package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class ClientCashOperationValidator {
    /***************************************************************************************
     * --Le paramètre operation a valider (ccopDto) ne doit pas etre null
     *      * --Le montant en mouvement doit toujours etre positif ou null
     *      * --Le compte cash associe ne doit pas etre null
     *      * --L'operation associe doit etre valide
     * @param ccopDto
     * @return
     */
    public static List<String> validate(ClientCashOperationDto ccopDto){
        List<String> errors = new ArrayList<>();
        if(ccopDto == null){
            errors.add("--le parametre à valider ne peut etre null--");
        }
        else {

            List<String> opt_errors = OperationValidator.validate(
                    ccopDto.getCcoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientCashOperationDto>> constraintViolations = validator.validate(ccopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientCashOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
