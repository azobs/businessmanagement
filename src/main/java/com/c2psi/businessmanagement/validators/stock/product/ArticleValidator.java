package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ArticleValidator {
    /**********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le code de l'article (artCode) ne peut etre ni null ni vide
     * *Le nom de l'article (artName) ne peut etre ni null ni vide
     * *le shortname de l'article (artShortname) ne peut etre ni null ni vide et doit etre plus court
     * que le nom de l'article
     * *La quantite seuil en stock de l'article (artThreshold) ne doit pas etre negative
     * *La limite de vente en gros de l'article (artLowLimitWholesale) ne doit pas etre negative
     * *La limite de vente en semi gros de l'article (artLowLimitSemiWholesale) ne doit pas etre negative
     * *La quantite en stock de l'article (artQuantityinstock) ne doit pas etre negative
     * *Le produit formate auquel est issu l'article (artPfDto) ne doit pas etre null
     * *L'unite de mesure de l'article (artUnitDto) ne peut etre null
     * *Le point de vente auquel appartient l'article (artPosDto) ne peut etre null
     * @param artDto
     * @return
     */
    public static List<String> validate(ArticleDto artDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(artDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ArticleDto>> constraintViolations = validator.validate(artDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ArticleDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            if(Optional.ofNullable(artDto.getArtLowLimitSemiWholesale()).isPresent() &&
                    Optional.ofNullable(artDto.getArtLowLimitWholesale()).isPresent()){
                if(artDto.getArtLowLimitSemiWholesale().compareTo(artDto.getArtLowLimitWholesale())>0){
                    errors.add("--La limite de vente en semi gros ne saurait etre supperieur a la limite de vente en gros--");
                }
            }

            if(Optional.ofNullable(artDto.getArtDescription()).isPresent()) {
                if (!StringUtils.hasLength(artDto.getArtDescription())) {
                    errors.add("--La description de l'article ne doit pas etre vide--");
                }
            }

        }
        return errors;
    }
}
