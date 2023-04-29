package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosCapsuleAccountValidator {
    /**************************************************************************************
     * *Le parametre a valider ne doit pas etre null
     * *Le point de vente associe au compte ne doit pas etre null
     * *L'article associe au compte capsule ne doit pas etre null
     * *L'article doit etre un article du point de vente
     * @param poscapsaccDto
     * @return
     */
    public static List<String> validate(PosCapsuleAccountDto poscapsaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(poscapsaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosCapsuleAccountDto>> constraintViolations = validator.validate(poscapsaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosCapsuleAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
