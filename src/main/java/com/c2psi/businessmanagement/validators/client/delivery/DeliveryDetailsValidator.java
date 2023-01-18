package com.c2psi.businessmanagement.validators.client.delivery;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryDetailsValidator {
    /****************************************************************************************
     * *Le parametre a valider ne doit pas etre null
     * *Le nombre d'emballage utilise par type d'emballage (ddNumberofpackageused) ne peut
     * etre negatif
     * *Le nombre d'emballage retourne par type d'emballage (ddNumberofpackagereturn) ne peut
     * etre negatif
     * *le type d'emballage (ddPackagingDto) associe ne peut etre null
     * *La livraison associe (ddDeliveryDto) associe ne peut etre null
     * @param deliveryDetailsDto
     * @return
     */
    public static List<String> validate(DeliveryDetailsDto deliveryDetailsDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(deliveryDetailsDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null: "+errors);
        }
        else{
            if(deliveryDetailsDto.getDdNumberofpackagereturn().intValue()<0){
                errors.add("--Le nombre d'emballage retourne pour un type d'emballage ne peut " +
                        "etre negatif: "+errors);
            }
            if(deliveryDetailsDto.getDdNumberofpackageused().intValue()<0){
                errors.add("--Le nombre d'emballage utilise pour un type d'emballage ne peut " +
                        "etre negatif: "+errors);
            }
            if(!Optional.ofNullable(deliveryDetailsDto.getDdDeliveryDto()).isPresent()){
                errors.add("--La livraison associe a ce details de livraison ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(deliveryDetailsDto.getDdPackagingDto()).isPresent()){
                errors.add("--Le type d'emballage lie a ce details de livraison ne peut " +
                        "etre null: "+errors);
            }
        }
        return errors;
    }
}
