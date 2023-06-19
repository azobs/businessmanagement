package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class InventoryValidator {
    /*************************************************************************************
     * *le parametre a valider ne peut etre null
     * *la date de l'inventaire ne peut etre ulterieure a la date courante
     * *le code l'inventaire ne peut etre ni null ni vide
     * *le point de vente pour lequel l'inventaire est enregistre ne peut etre null
     * @param invDto
     * @return
     */
    public static List<String> validate(InventoryDto invDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(invDto).isPresent()){
            errors.add("--le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<InventoryDto>> constraintViolations = validator.validate(invDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<InventoryDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
