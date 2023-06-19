package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderCashAccountValidator {
    /**********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe au compte (pcaPointofsaleDto) ne peut etre null
     * *Le provider associe au compte (pcaProviderDto) ne peut etre null
     * *Le provider doit etre du meme point de vente que celui pour lequel le compte est cree
     * @param procashaccDto
     * @return
     */
    public static List<String> validate(ProviderCashAccountDto procashaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procashaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderCashAccountDto>> constraintViolations = validator.validate(procashaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderCashAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
