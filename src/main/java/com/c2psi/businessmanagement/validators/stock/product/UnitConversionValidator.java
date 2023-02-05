package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UnitConversionValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le facteur de conversion (conversionFactor) ne peut etre negatif
     * *L'unite source (unitSourceDto) et l'unite destination (unitDestinationDto)
     * ne peuvent etre null
     * @param unitConversionDto
     * @return
     */
    public static List<String> validate(UnitConversionDto unitConversionDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(unitConversionDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<UnitConversionDto>> constraintViolations = validator.validate(unitConversionDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<UnitConversionDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
