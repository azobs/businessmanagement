package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderPackagingOperationValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le compte packaging associe ne peut etre null
     * *Le nombre de packaging en mouvement ne peut etre negatif
     * *L'operation doit etre valide
     * @param propackopDto
     * @return
     */
    public static List<String> validate(ProviderPackagingOperationDto propackopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(propackopDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderPackagingOperationDto>> constraintViolations = validator.validate(propackopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderPackagingOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
