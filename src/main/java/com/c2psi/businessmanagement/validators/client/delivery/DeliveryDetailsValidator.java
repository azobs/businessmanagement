package com.c2psi.businessmanagement.validators.client.delivery;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DeliveryDetailsValidator {
    /****************************************************************************************
     * *Le parametre a valider ne doit pas etre null
     * *Le nombre d'emballage utilise par type d'emballage (ddNumberofpackageused) ne peut
     * etre negatif
     * *Le nombre d'emballage retourne par type d'emballage (ddNumberofpackagereturn) ne peut
     * etre negatif
     * *le type d'emballage (ddPackagingDto) associe ne peut etre null
     * *La livraison associe (ddDeliveryDto) associe ne peut etre null
     * @param deliveryDetailsDto
     * @return
     */
    public static List<String> validate(DeliveryDetailsDto deliveryDetailsDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(deliveryDetailsDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DeliveryDetailsDto>> constraintViolations = validator.validate(deliveryDetailsDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<DeliveryDetailsDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
