package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CapsuleArrivalValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *La quantite livre (capsaDeliveryquantity) ne peut etre negative
     * *la quantite de capsule change (capsaQuantitycapschanged) ne peut etre negative
     * *L'article associe (capsaArtDto) ne peut etre null
     * *la facture associe a l'arrivage (capsaSicapsDto) ne peut etre null
     * @param capsarrDto
     * @return
     */
    public static List<String> validate(CapsuleArrivalDto capsarrDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(capsarrDto).isPresent()){
            errors.add("--Le parametre CapsuleArrivalDto a valider ne peut etre null: "+errors);
        }
        else{
            if(capsarrDto.getCapsaDeliveryquantity()<0){
                errors.add("--la quantite livre ne peut etre negative: "+errors);
            }
            if(capsarrDto.getCapsaQuantitycapschanged()<0){
                errors.add("--la quantite de capsule changé ne peut etre negative: "+errors);
            }
            if(!Optional.ofNullable(capsarrDto.getCapsaArtDto()).isPresent()){
                errors.add("--L'article concerné par l'arrivage par capsule ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(capsarrDto.getCapsaSicapsDto()).isPresent()){
                errors.add("--La facture sur laquelle figure cet arrivage ne peut etre null: "+errors);
            }
        }
        return errors;
    }
}
