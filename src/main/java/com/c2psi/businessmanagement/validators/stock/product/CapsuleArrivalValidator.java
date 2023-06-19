package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CapsuleArrivalValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *La quantite livre (capsaDeliveryquantity) ne peut etre negative
     * *la quantite de capsule change (capsaQuantitycapschanged) ne peut etre negative
     * *L'article associe (capsaArtDto) ne peut etre null
     * *la facture associe a l'arrivage (capsaSicapsDto) ne peut etre null
     * @param capsarrDto
     * @return
     */
    public static List<String> validate(CapsuleArrivalDto capsarrDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(capsarrDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CapsuleArrivalDto>> constraintViolations = validator.validate(capsarrDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CapsuleArrivalDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
