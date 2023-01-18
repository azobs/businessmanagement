package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosCashAccountValidator {
    /************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *
     * @param poscashaccDto
     * @return
     */
    public static List<String> validate(PosCashAccountDto poscashaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(poscashaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(poscashaccDto.getPcaBalance()).isPresent()){
                errors.add("--La balance d'un compte ne peut etre null--");
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosCashAccountDto>> constraintViolations = validator.validate(poscashaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosCashAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
