package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SaleInvoiceDamageValidator {
    public static List<String> validate(SaleInvoiceDamageDto sidamDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(sidamDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            if(Optional.ofNullable(sidamDto.getSaleidamDeliveryDate()).isPresent() &&
                    Optional.ofNullable(sidamDto.getSaleidamInvoicingDate()).isPresent()) {
                if (sidamDto.getSaleidamDeliveryDate().isBefore(
                        sidamDto.getSaleidamInvoicingDate())) {
                    errors.add("--La date de livraison ne saurait être antérieure à la date de facturation--");
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SaleInvoiceDamageDto>> constraintViolations = validator.validate(sidamDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<SaleInvoiceDamageDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
