package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;

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
