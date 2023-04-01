package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CategoryValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le nom de la categorie (catName) ne peut etre ni vide ni null
     * *Le code de la categorie (catCode) ne peut etre ni null ni vide
     * *le shortname de la categorie (catShortname) ne peut etre ni null ni vide et doit etre plus court
     * que le nom de la categorie
     * *Une categorie ne peut etre sa propre categorie parent
     * *le point de vente de la categorie ne peut etre null
     * @param catDto
     * @return
     */
    public static List<String> validate(CategoryDto catDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(catDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CategoryDto>> constraintViolations = validator.validate(catDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CategoryDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            if(!Optional.ofNullable(catDto.getCatDescription()).isPresent()){
                if(!StringUtils.hasLength(catDto.getCatDescription())){
                    errors.add("--La description de la categorie ne peut etre vide--");
                }
            }
        }
        return errors;
    }
}
