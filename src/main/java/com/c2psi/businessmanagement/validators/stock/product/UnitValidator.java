package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UnitValidator {
    /*********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom de l'unite (unitName) et son abbreviation (unitAbbreviation)
     * ne sauront etre ni vide ni null
     * *La taille de l'abbreviation ne peut etre superieur a celle du nom
     * *Le point de vente de l'unite (unitPosDto) ne saurait etre null
     * @param unitDto
     * @return
     */
    public static List<String> validate(UnitDto unitDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(unitDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<UnitDto>> constraintViolations = validator.validate(unitDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<UnitDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
