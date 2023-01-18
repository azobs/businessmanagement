package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            errors.add("--Le parametre a valider ArticleDto ne peut etre null: "+errors);
        }
        else{
            if(!StringUtils.hasLength(artDto.getArtCode())){
                errors.add("--Le code de l'article ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(artDto.getArtName())){
                errors.add("--Le nom de l'article ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(artDto.getArtShortname())){
                errors.add("--Le shortname de l'article ne peut etre vide: "+errors);
            }
            if(artDto.getArtName().length()<artDto.getArtShortname().length()){
                errors.add("--Le shortname de l'article ne peut etre plus long que son nom: "+errors);
            }
            if(artDto.getArtThreshold().intValue()<0){
                errors.add("--La quantite seuil de l'article en stock ne peut etre negative: "+errors);
            }
            if(artDto.getArtLowLimitSemiWholesale().intValue()<0){
                errors.add("--La quantite limite de vente en semi gros de l'article" +
                        " ne peut etre negative: "+errors);
            }
            if(artDto.getArtLowLimitWholesale().intValue()<0){
                errors.add("--La quantite limite de vente en gros de l'article" +
                        " ne peut etre negative: "+errors);
            }
            if(artDto.getArtQuantityinstock().doubleValue()<0){
                errors.add("--La quantite en stock de l'article" +
                        " ne peut etre negative: "+errors);
            }
            if(!Optional.ofNullable(artDto.getArtPfDto()).isPresent()){
                errors.add("--Le produit formate auquel est issu l'article ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(artDto.getArtUnitDto()).isPresent()){
                errors.add("--L'unite de mesure de l'article ne saurait etre null: "+errors);
            }
            if(!Optional.ofNullable(artDto.getArtPosDto()).isPresent()){
                errors.add("--Le point de vente auquel appartient l'article ne peut etre null: "+errors);
            }
        }
        return errors;
    }
}
