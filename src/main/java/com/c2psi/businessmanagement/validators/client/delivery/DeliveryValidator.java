package com.c2psi.businessmanagement.validators.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DeliveryValidator {
    /********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le code de la livraison (deliveryCode) ne peut etre ni null ni vide
     * *L'etat de la livraison (deliveryState) ne doit pas etre null et doit une
     * valeur reconnu par le systeme
     * *L'utilisateur en charge de la livraison (deliveryUserbmDto) ne doit pas etre null
     * @param deliveryDto
     * @return
     */
    public static List<String> validate(DeliveryDto deliveryDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(deliveryDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{



            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DeliveryDto>> constraintViolations = validator.validate(deliveryDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<DeliveryDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
