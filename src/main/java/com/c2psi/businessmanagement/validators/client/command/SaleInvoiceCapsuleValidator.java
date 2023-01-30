package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class SaleInvoiceCapsuleValidator {
    /*********************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Les validations sur les attributs sont faites par les annotations de spring validation
     * Ici ne figure aue les validations aui ne pourront pas etre faites par les annotations
     * @param sicapsDto
     * @return
     */
   public static List<String> validate(SaleInvoiceCapsuleDto sicapsDto){
       List<String> errors = new ArrayList<>();
       if(!Optional.ofNullable(sicapsDto).isPresent()){
           errors.add("--Le paramètre à valider ne peut etre null--");
       }
       else{
           /*
           if(Optional.ofNullable(sicapsDto.getSaleicapsCode()).isPresent()){
               if(!StringUtils.hasLength(sicapsDto.getSaleicapsCode())){
                   errors.add("--Le code de la facture de vente capsule ne peut etre vide--");
               }
           }*/
           if(Optional.ofNullable(sicapsDto.getSaleicapsDeliveryDate()).isPresent() &&
                   Optional.ofNullable(sicapsDto.getSaleicapsInvoicingDate()).isPresent()) {
               if (sicapsDto.getSaleicapsDeliveryDate().isBefore(
                       sicapsDto.getSaleicapsInvoicingDate())) {
                   errors.add("--La date de livraison ne saurait être antérieure à la date de facturation--");
               }
           }

           ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
           Validator validator = factory.getValidator();

           Set<ConstraintViolation<SaleInvoiceCapsuleDto>> constraintViolations = validator.validate(sicapsDto);

           if (constraintViolations.size() > 0 ) {
               for (ConstraintViolation<SaleInvoiceCapsuleDto> contraintes : constraintViolations) {
                   errors.add(contraintes.getMessage());
               }
           }

       }
       return errors;
   }
}
