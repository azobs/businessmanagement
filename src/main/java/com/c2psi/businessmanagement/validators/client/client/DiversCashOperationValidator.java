package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiversCashOperationValidator {
    public static List<String> validate(DiversCashOperationDto dcopDto){
        List<String> errors = new ArrayList<>();
        if(dcopDto == null){
            errors.add("--le parametre à valider ne peut etre null--");
        }
        else {

            List<String> opt_errors = OperationValidator.validate(
                    dcopDto.getDcoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DiversCashOperationDto>> constraintViolations = validator.validate(dcopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<DiversCashOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
