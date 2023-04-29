package com.c2psi.businessmanagement.validators.pos.pos;


import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosPackagingOperationValidator {
    /**************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nombre en mouvement ne peut etre negatif
     * *Le compte d'emballage (posPackagingAccountDto) ne peut etre null
     * *l'operation associe doit etre valide
     * @param pospackopDto
     * @return
     */
    public static List<String> validate(PosPackagingOperationDto pospackopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(pospackopDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{

            List<String> opt_errors = OperationValidator.validate(
                    pospackopDto.getPospoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosPackagingOperationDto>> constraintViolations = validator.validate(pospackopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosPackagingOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
