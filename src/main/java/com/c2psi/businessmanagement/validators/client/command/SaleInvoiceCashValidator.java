package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class SaleInvoiceCashValidator {
    /***********************************************************************************
     * *Les annotations font le gros du boulot dans la validation dans les dto
     * @param sicDto
     * @return
     */
    public static List<String> validate(SaleInvoiceCashDto sicDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(sicDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{

            if(Optional.ofNullable(sicDto.getSaleicashDeliveryDate()).isPresent() &&
                    Optional.ofNullable(sicDto.getSaleicashInvoicingDate()).isPresent()) {
                if (sicDto.getSaleicashDeliveryDate().isBefore(
                        sicDto.getSaleicashInvoicingDate())) {
                    errors.add("--La date de livraison ne saurait être antérieure à la date de facturation--");
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SaleInvoiceCashDto>> constraintViolations = validator.validate(sicDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<SaleInvoiceCashDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
