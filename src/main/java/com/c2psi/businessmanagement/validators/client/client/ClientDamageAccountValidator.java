package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientDamageAccountValidator {
    /******************************************************************************
     *  *Le parametre cdaDto ne doit pas etre null
     *  *Le compte doit être lié à un article
     *  *Le compte doit être lié à un client
     *  *Le compte doit être lié à un point de vente
     *  *L'article doit être un article du point de vente auquel le compte est lié
     *  *Le client doit être un client du point de vente auquel le compte est lié
     * @param cdaDto
     * @return
     */
    public static List<String> validate(ClientDamageAccountDto cdaDto){
        List<String> errors = new ArrayList<>();
        if(cdaDto == null){
            errors.add("--Le compte damage (avarie) ne peut être null--");
        }
        else{

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientDamageAccountDto>> constraintViolations = validator.validate(cdaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientDamageAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
