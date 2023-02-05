package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.*;

public class SupplyInvoiceCashValidator {
    /***********************************************************************************
     * *Le parametre a valider ne saurait etre null
     * *Le code de la facture () ne peut etre ni vide ni null
     * *La date de livraison (sicashDeliveryDate) ne peut etre anterieur a la
     * date de facturation (sicashInvoicingDate)
     * *L'utilisateur qui enregistre la facture (sicashUserbmDto) ne peut etre null
     * *Le montant verse (sicashAmountpaid) et le montant a verser
     * (sicashAmountexpected) ne saurait etre negatif
     * @param supplyinvcashDto
     * @return
     */
    public static List<String> validate(SupplyInvoiceCashDto supplyinvcashDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(supplyinvcashDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SupplyInvoiceCashDto>> constraintViolations = validator.validate(supplyinvcashDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<SupplyInvoiceCashDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*********************************************************************************************
             * Il faut se rassurer que la date de livraison n'est pas anterieur a la date de facturation *
             *********************************************************************************************/
            if(Optional.ofNullable(supplyinvcashDto.getSicashInvoicingDate()).isPresent() &&
                    Optional.ofNullable(supplyinvcashDto.getSicashDeliveryDate()).isPresent()){
                if(supplyinvcashDto.getSicashInvoicingDate().compareTo(supplyinvcashDto.getSicashDeliveryDate())>0){
                    errors.add("--La date de facturation ne saurait etre ulterieure a la date de livraison--");
                }
            }
        }
        return errors;
    }
}
