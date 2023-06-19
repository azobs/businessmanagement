package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosDamageOperationValidator {
    /************************************************************************************
     * *Le parametre a valider ne doit pas etre null
     * *Le nombre en mouvement ne doit pas etre negatif
     * *Le compte avarie associe ne doit pas etre null
     * *L'operation associe doit etre valide
     * @param posdamopDto
     * @return
     */
    public static List<String> validate(PosDamageOperationDto posdamopDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(posdamopDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{

            if(!Optional.ofNullable(posdamopDto.getPosdoPosDamageAccountDto()).isPresent()){
                errors.add("--Le compte affecte par l'operation ne peut etre null--");
            }

            if(!Optional.ofNullable(posdamopDto.getPosdoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null--");
            }

            List<String> opt_errors = OperationValidator.validate(
                    posdamopDto.getPosdoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosDamageOperationDto>> constraintViolations = validator.validate(posdamopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosDamageOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
