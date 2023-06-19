package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.DiversCashAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiversCashAccountValidator {
    public static List<String> validate(DiversCashAccountDto dcaDto){
        List<String> errors = new ArrayList<>();
        if(dcaDto == null){
            errors.add("--Le compte cash a valider ne saurait être null--");
        }
        else {
            if(dcaDto.getDcaBalance() == null){
                errors.add("--La balance d'un compte ne peut etre null--");
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DiversCashAccountDto>> constraintViolations = validator.validate(dcaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<DiversCashAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
