package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductFormatedValidator {
    /***********************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le produit associe ne peut etre null
     * *le format utilise ne peut etre null
     * @param prodformDto
     * @return
     */
    public static List<String> validate(ProductFormatedDto prodformDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(prodformDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProductFormatedDto>> constraintViolations = validator.validate(prodformDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProductFormatedDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return  errors;
    }
}
