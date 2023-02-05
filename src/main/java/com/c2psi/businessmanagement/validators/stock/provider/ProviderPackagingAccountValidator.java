package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderPackagingAccountValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe () ne peut etre null
     * *Le provider associe () ne peut etre null
     * *Le packaging associe () ne peut etre null
     * *Le packaging, le provider et le compte meme doivent etre du meme point de vente
     *
     * @param propackaccDto
     * @return
     */
    public static List<String> validate(ProviderPackagingAccountDto propackaccDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(propackaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderPackagingAccountDto>> constraintViolations = validator.validate(propackaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderPackagingAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
