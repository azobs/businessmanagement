package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosCapsuleOperationValidator {
    /*********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *L'operation associe doit etre valide
     * *Le compte capsule associe ne doit pas etre null
     * @param poscapsopDto
     * @return
     */
    public static List<String> validate(PosCapsuleOperationDto poscapsopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(poscapsopDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosCapsuleOperationDto>> constraintViolations = validator.validate(poscapsopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosCapsuleOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
