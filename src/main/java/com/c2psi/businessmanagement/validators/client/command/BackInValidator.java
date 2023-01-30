package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import com.c2psi.businessmanagement.dtos.client.command.BackInDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BackInValidator {
    public static List<String> validate(BackInDto backInDto){
        List<String> errors = new ArrayList<>();
        Optional<BackInDto> optbiDto = Optional.ofNullable(backInDto);
        if(!optbiDto.isPresent()){
            errors.add("--Le  parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<BackInDto>> constraintViolations = validator.validate(backInDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<BackInDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
