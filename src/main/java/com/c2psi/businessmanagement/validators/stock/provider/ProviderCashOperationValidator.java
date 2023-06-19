package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderCashOperationValidator {
    /************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le montant en mouvement dans l'operation ne peut etre null
     * *Le compte cash associe ne peut etre null
     * *L'operation doit etre valide
     * @param procashopDto
     * @return
     */
    public static List<String> validate(ProviderCashOperationDto procashopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procashopDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderCashOperationDto>> constraintViolations = validator.validate(procashopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderCashOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
