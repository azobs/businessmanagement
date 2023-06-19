package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FormatValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom du format (formatName) ne peut etre ni null ni vide
     * *la capacite du format (formatCapacity) ne peut etre negative
     * *Le point de vente du format (formatPosDto) ne peut etre null
     * @param formatDto
     * @return
     */
    public static List<String> validate(FormatDto formatDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(formatDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<FormatDto>> constraintViolations = validator.validate(formatDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<FormatDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
