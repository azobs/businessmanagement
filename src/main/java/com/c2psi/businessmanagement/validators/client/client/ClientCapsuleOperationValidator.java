package com.c2psi.businessmanagement.validators.client.client;


import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ClientCapsuleOperationValidator {
    /******************************************************************************************
     * Les conditions de validité d'une operation sur un compte client capsule
     * --Le paramètre operation a valider (ccapsopDto) ne doit pas etre null
     * --Le nombre en mouvement doit toujours etre positif ou null
     * --Le compte capsule associe ne doit pas etre null
     * --La date de l'operation doit etre antérieur ou egal a la date d'enregistrement
     * --L'objet de l'operation doit être précise
     * --Le type de l'opération est obligatoire. Il doit toujours etre précisé parmi ceux
     * admis par l'application
     * @param ccapsopDto
     * @return
     */
    public static List<String> validate(ClientCapsuleOperationDto ccapsopDto){
        List<String> errors = new ArrayList<>();
        if(ccapsopDto == null){
            errors.add("--le parametre à valider ne peut etre null--");
        }
        else {

            List<String> opt_errors = OperationValidator.validate(
                    ccapsopDto.getCltcsoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientCapsuleOperationDto>> constraintViolations = validator.validate(ccapsopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientCapsuleOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
