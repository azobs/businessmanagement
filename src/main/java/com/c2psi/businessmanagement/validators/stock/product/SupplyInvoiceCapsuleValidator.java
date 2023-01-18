package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            errors.add("--Le parametre a valider SupplyInvoiceCapsuleDto ne " +
                    "saurait etre null: "+errors);
        }
        else{
            Instant dateCourante = new Date().toInstant();
            if (dateCourante.isBefore(supplyinvcapsDto.getSicapsInvoicingDate())) {
                errors.add("--La date de facturation ne saurait etre ultérieure a " +
                        "la date courante: "+errors);
            }
            if (supplyinvcapsDto.getSicapsDeliveryDate().isBefore(
                    supplyinvcapsDto.getSicapsInvoicingDate())) {
                errors.add("--La date de facturation ne saurait etre ultérieure a " +
                        "la date de livraison: "+errors);
            }
            if(!StringUtils.hasLength(supplyinvcapsDto.getSicapsCode())){
                errors.add("--Le code de la facture capsule ne peut etre vide: "+errors);
            }
            if(supplyinvcapsDto.getSicapsTotalcolis()<0){
                errors.add("--Le nombre total de colis ne saurait etre negatif: "+errors);
            }
            if(!Optional.ofNullable(supplyinvcapsDto.getSicapsUserbmDto()).isPresent()){
                errors.add("--L'utilisateur qui enregistre la facture ne peut etre null: "+errors);
            }
        }
        return errors;
    }
}
