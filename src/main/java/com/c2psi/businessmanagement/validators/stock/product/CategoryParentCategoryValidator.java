package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryParentCategoryDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CategoryParentCategoryValidator {
    public static List<String> validate(CategoryParentCategoryDto catPatCatDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(catPatCatDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CategoryParentCategoryDto>> constraintViolations = validator.validate(catPatCatDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CategoryParentCategoryDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
