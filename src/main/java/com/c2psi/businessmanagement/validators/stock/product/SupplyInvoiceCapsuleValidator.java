package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.*;

public class SupplyInvoiceCapsuleValidator {
    /**********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le code de la facture (sicapsCode) ne peut etre null
     * *La date de livraison (sicapsDeliveryDate) ne peut etre anterieur a la
     * date de facturation (sicapsInvoicingDate)
     * *Le nombre total de colis (sicapsTotalcolis) ne peut etre negatif ou null
     * *L'utilisateur qui enregistre la facture (sicapsUserbmDto) ne peut etre null
     * @param supplyinvcapsDto
     * @return
     */
    public static List<String> validate(SupplyInvoiceCapsuleDto supplyinvcapsDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(supplyinvcapsDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SupplyInvoiceCapsuleDto>> constraintViolations = validator.validate(supplyinvcapsDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<SupplyInvoiceCapsuleDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*********************************************************************************************
             * Il faut se rassurer que la date de livraison n'est pas anterieur a la date de facturation *
             *********************************************************************************************/
            if(Optional.ofNullable(supplyinvcapsDto.getSicapsInvoicingDate()).isPresent() &&
                    Optional.ofNullable(supplyinvcapsDto.getSicapsDeliveryDate()).isPresent()){
                if(supplyinvcapsDto.getSicapsInvoicingDate().compareTo(supplyinvcapsDto.getSicapsDeliveryDate())>0){
                    errors.add("--La date de facturation ne saurait etre ulterieure a la date de livraison--");
                }
            }

        }
        return errors;
    }
}
