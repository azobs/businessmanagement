package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CashArrivalValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *La quantite livre (cashaDeliveryquantity) ne peut etre negative
     * *Le prix unitaire de livraison (cashaUnitprice) ne peut etre negatif
     * *Le type d'arrivage (cashaArrivaltype) ne peut etre null et doit etre reconnu
     * par le systeme
     * *L'article associe a la livraison (cashaArtDto) ne peut etre null
     * *La facture associe a la livraison (cashaSicashDto) ne peut etre null
     * @param casharrDto
     * @return
     */
    public static List<String> validate(CashArrivalDto casharrDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(casharrDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CashArrivalDto>> constraintViolations = validator.validate(casharrDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CashArrivalDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*******************************************************************************************
             * Lorsque casharrivaltype est = standard alors SupplyInvoiceCashDto ne doit pas etre null *
             *******************************************************************************************/
            if(Optional.ofNullable(casharrDto.getCashaArrivaltype()).isPresent()) {
                if (casharrDto.getCashaArrivaltype().equals(CashArrivalType.Standard)) {
                    if (!Optional.ofNullable(casharrDto.getCashaSicashDto()).isPresent()) {
                        errors.add("--La facture associe a l'arrivage ne peut etre null--");
                    }
                }
            }
        }
        return errors;
    }
}
