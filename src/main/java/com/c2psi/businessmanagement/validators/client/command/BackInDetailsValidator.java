package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BackInDetailsValidator {
    public static List<String> validate(BackInDetailsDto backInDetailsDto){
        List<String> errors = new ArrayList<>();
        Optional<BackInDetailsDto> optbiDto = Optional.ofNullable(backInDetailsDto);
        if(!optbiDto.isPresent()){
            errors.add("--Le  parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<BackInDetailsDto>> constraintViolations = validator.validate(backInDetailsDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<BackInDetailsDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
