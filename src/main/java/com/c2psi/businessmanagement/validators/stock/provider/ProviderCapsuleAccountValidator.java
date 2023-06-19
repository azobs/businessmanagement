package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProviderCapsuleAccountValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *L'article lie au compte (pcsaArticleDto) ne peut etre null
     * *Le provider lie au compte (pcsaProviderDto) ne peut etre null
     * *L'article et le provider lie au compte doivent tous appartenir au point de vente
     * *le compte doit etre lie au meme point de vente que celui de l'article et du provider
     * @param procapsaccDto
     * @return
     */
    public static List<String> validate(ProviderCapsuleAccountDto procapsaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procapsaccDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderCapsuleAccountDto>> constraintViolations = validator.validate(procapsaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderCapsuleAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
