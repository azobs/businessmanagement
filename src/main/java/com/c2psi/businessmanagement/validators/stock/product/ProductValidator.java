package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductValidator {
    /*************************************************************************************
     * *le parametre a valider ne peut etre null
     * *le nom du produit (prodName) ne peut etre ni null ni vide
     * *La categorie du produit ne peut etre null
     * @param prodDto
     * @return
     */
    public static List<String> validate(ProductDto prodDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(prodDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(prodDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProductDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
