package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderDamageAccountValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe au compte (pdaPosDto) ne peut etre null
     * *Le provider associe au compte (pdaProviderDto) ne peut etre null
     * *L'article associe au compte (pdaArticleDto) ne peut etre null
     * *L'article et le provider doivent etre du meme point de vente que celui du compte
     * @param prodamaccDto
     * @return
     */
    public static List<String> validate(ProviderDamageAccountDto prodamaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(prodamaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderDamageAccountDto>> constraintViolations = validator.validate(prodamaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderDamageAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
