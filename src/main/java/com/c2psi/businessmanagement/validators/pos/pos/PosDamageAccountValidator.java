package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosDamageAccountValidator {
    /**************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe ne peut etre null
     * *L'article associe ne peut etre null
     * *L'article doit etre un article du point de vente
     * @param posdamaccDto
     * @return
     */
    public static List<String> validate(PosDamageAccountDto posdamaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(posdamaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            //int isnullable=0;
            if(!Optional.ofNullable(posdamaccDto.getPdaArticleDto()).isPresent()){
                errors.add("--L'article associe au compte avarie ne peut etre null--");
                //isnullable=1;
            }
            if(!Optional.ofNullable(posdamaccDto.getPdaPointofsaleDto()).isPresent()){
                errors.add("--Le point de vente associe au compte ne peut etre null--");
                //isnullable=2;
            }

            if(!Optional.ofNullable(posdamaccDto.getPdaNumber()).isPresent()){
                errors.add("--Le nombre d'article avarié ne peut etre null--");
                //isnullable=2;
            }
            else{
                if(posdamaccDto.getPdaNumber()<0){
                    errors.add("--Le nombre d'article avarié ne peut être negatif--");
                }
            }

            /*if(isnullable == 0){

                int isArticleofPos = 0;
                List<ArticleDto> articleDtoList = posdamaccDto.getPdaPointofsaleDto().getArticleDtoList();
                for (ArticleDto articleDto : articleDtoList) {
                    if (articleDto.getId().equals(posdamaccDto.getPdaArticleDto().getId())) {
                        isArticleofPos = 1;
                    }
                }
                if (isArticleofPos == 0) {
                    errors.add("--L'article spécifié n'est pas un article du point de vente: "+errors);
                }
            }*/

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosDamageAccountDto>> constraintViolations = validator.validate(posdamaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosDamageAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
