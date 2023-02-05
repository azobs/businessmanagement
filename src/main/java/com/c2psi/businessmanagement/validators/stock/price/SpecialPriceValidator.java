package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SpecialPriceValidator {
    /*********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *
     * @param spDto
     * @return
     */
    public static List<String> validate(SpecialPriceDto spDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(spDto).isPresent()){
            errors.add("--Le parametre  a valider ne peut etre null--");
        }
        else {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SpecialPriceDto>> constraintViolations = validator.validate(spDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<SpecialPriceDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
