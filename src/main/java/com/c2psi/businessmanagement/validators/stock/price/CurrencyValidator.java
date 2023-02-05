package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CurrencyValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom de la devise (currencyName) ne peut etre ni null ni vide
     * *Le shortname de la devise ne peut etre ni null ni vide
     * *Le point de vente associe a la devise ne peut etre null
     * @param curDto
     * @return
     */
    public static List<String> validate(CurrencyDto curDto){
        List<String> errors  = new ArrayList<>();
        if(!Optional.ofNullable(curDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CurrencyDto>> constraintViolations = validator.validate(curDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CurrencyDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
