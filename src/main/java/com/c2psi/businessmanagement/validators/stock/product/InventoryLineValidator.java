package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InventoryLineValidator {
    /*********************************************************************************
     * *le parametre a valider ne peut etre null
     * *La quantite reelle en stock () ne peut etre negative
     * *la quantite logique en stock () ne peut etre negative
     * *L'inventaire auquel appartient la ligne d'inventaire ne peut etre null
     * *L'article associe a la ligne d'inventaire ne peut etre null
     * @param invlineDto
     * @return
     */
    public static List<String> validate(InventoryLineDto invlineDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(invlineDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<InventoryLineDto>> constraintViolations = validator.validate(invlineDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<InventoryLineDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
