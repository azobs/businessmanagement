package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CurrencyConversionValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le facteur de conversion ne peut etre negatif
     * *les devises associees ne peuvent etre null
     * *les devises associes doivent appartenir au meme point de vente
     * @param curconvDto
     * @return
     */
    public static List<String> validate(CurrencyConversionDto curconvDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(curconvDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CurrencyConversionDto>> constraintViolations = validator.validate(curconvDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CurrencyConversionDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
