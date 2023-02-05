package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.DamageArrivalDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DamageArrivalValidator {
    public static List<String> validate(DamageArrivalDto damarrDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(damarrDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DamageArrivalDto>> constraintViolations = validator.validate(damarrDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<DamageArrivalDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
