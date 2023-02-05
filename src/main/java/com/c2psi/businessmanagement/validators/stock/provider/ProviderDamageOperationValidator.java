package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderDamageOperationValidator {
    /******************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nombre en mouvement (prodoNumberinmvt) ne peut etre negatif
     * *Le compte damage provider (proDamageAccountDto) qui sera affecte au compte
     * ne peut etre null
     * *L'operation (prodoOperationDto) doit etre valide
     * @param prodamopDto
     * @return
     */
    public static List<String> validate(ProviderDamageOperationDto prodamopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(prodamopDto).isPresent()){
            errors.add("--le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderDamageOperationDto>> constraintViolations = validator.validate(prodamopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderDamageOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
