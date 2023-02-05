package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SupplyInvoiceDamageValidator {
    public static List<String> validate(SupplyInvoiceDamageDto supplyinvDamDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(supplyinvDamDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SupplyInvoiceDamageDto>> constraintViolations = validator.validate(supplyinvDamDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<SupplyInvoiceDamageDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*********************************************************************************************
             * Il faut se rassurer que la date de livraison n'est pas anterieur a la date de facturation *
             *********************************************************************************************/
            if(Optional.ofNullable(supplyinvDamDto.getSidamInvoicingDate()).isPresent() &&
                    Optional.ofNullable(supplyinvDamDto.getSidamDeliveryDate()).isPresent()){
                if(supplyinvDamDto.getSidamInvoicingDate().compareTo(supplyinvDamDto.getSidamDeliveryDate())>0){
                    errors.add("--La date de facturation ne saurait etre ulterieure a la date de livraison--");
                }
            }
        }
        return errors;
    }
}
