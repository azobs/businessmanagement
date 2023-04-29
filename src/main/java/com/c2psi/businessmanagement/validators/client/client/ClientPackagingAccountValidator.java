package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientPackagingAccountValidator {
    /***************************************************************************************
     *  *Le parametre compte packaging client (cpaDto) ne doit pas etre null
     *  *Le compte doit être lié à un packaging
     *  *Le compte doit être lié à un client
     *  *Le compte doit être lié à un point de vente
     *  *Le packaging doit être un packaging du point de vente auquel le compte est lié
     *  *Le client doit être un client du point de vente auquel le compte est lié
     * @param cpaDto
     * @return
     */
    public static List<String> validate(ClientPackagingAccountDto cpaDto){
        List<String> errors = new ArrayList<>();
        if(cpaDto == null){
            errors.add("--Le compte packaging client ne peut être null--");
        }
        else{

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientPackagingAccountDto>> constraintViolations = validator.validate(cpaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientPackagingAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
