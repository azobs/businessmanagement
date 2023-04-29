package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientCashAccountValidator {
    /****************************************************************************************
     * Les conditions de validité d'une operation sur un compte client cash
     *      *le parametre compte cash client (ccaDto) ne doit pas etre null
     *      *Le compte doit être lié à un client
     *      *Le compte doit être lié à un point de vente
     *      *Le client doit être un client du point de vente auquel le compte est lié
     * @param ccaDto
     * @return
     */
    public static List<String> validate(ClientCashAccountDto ccaDto){
        List<String> errors = new ArrayList<>();
        if(ccaDto == null){
            errors.add("--Le compte cash a valider ne saurait être null--");
        }
        else {
            if(ccaDto.getCcaBalance() == null){
                errors.add("--La balance d'un compte ne peut etre null--");
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientCashAccountDto>> constraintViolations = validator.validate(ccaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientCashAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
