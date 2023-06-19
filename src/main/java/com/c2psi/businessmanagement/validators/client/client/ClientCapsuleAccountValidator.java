package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientCapsuleAccountValidator {
    /******************************************************************************
     * Les conditions de validité d'un compte capsule client sont:
     * --le parametre compte capsule client (ccapsaDto) ne doit pas etre null
     * --Le compte doit être lié à un article
     * --Le compte doit être lié à un client
     * --Le compte doit être lié à un point de vente
     * --L'article doit être un article du point de vente auquel le compte est lié
     * --Le client doit être un client du point de vente auquel le compte est lié
     * @param ccapsaDto
     * @return
     */
    public static List<String> validate(ClientCapsuleAccountDto ccapsaDto){
        List<String> errors = new ArrayList<>();
        if(ccapsaDto == null){
            errors.add("--Le compte capsule client ne peut être null--");
        }
        else{

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientCapsuleAccountDto>> constraintViolations = validator.validate(ccapsaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientCapsuleAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
