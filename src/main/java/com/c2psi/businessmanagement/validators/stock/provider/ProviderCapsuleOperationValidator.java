package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderCapsuleOperationValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le nombre de capsule en mouvement (procsoNumberinmvt) ne peut etre negatif
     * *Le compte qui sera affecte par l'operation (proCapsuleAccountDto) ne peut
     * etre null
     * *L'operation associe doit etre valide
     * @param procapsopDto
     * @return
     */
    public static List<String> validate(ProviderCapsuleOperationDto procapsopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procapsopDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderCapsuleOperationDto>> constraintViolations = validator.validate(procapsopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderCapsuleOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
