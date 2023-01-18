package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            errors.add("--Le parametre a valider ne saurait etre null: "+errors);
        }
        else{
            if(!StringUtils.hasLength(supplyinvcashDto.getSicashCode())){
                errors.add("--Le code de la facture cash ne peut etre vide: "+errors);
            }
            Instant dateCourante = new Date().toInstant();
            if (dateCourante.isBefore(supplyinvcashDto.getSicashInvoicingDate())) {
                errors.add("--La date de facturation ne saurait etre ultérieure a " +
                        "la date courante: "+errors);
            }
            if (supplyinvcashDto.getSicashDeliveryDate().isBefore(
                    supplyinvcashDto.getSicashInvoicingDate())) {
                errors.add("--La date de facturation ne saurait etre ultérieure a " +
                        "la date de livraison: "+errors);
            }
            if(!Optional.ofNullable(supplyinvcashDto.getSicashUserbmDto()).isPresent()){
                errors.add("--L'utilisateur qui enregistre la facture ne peut etre null: "+errors);
            }
            if(supplyinvcashDto.getSicashAmountexpected().doubleValue()<0){
                errors.add("--Le montant à verser ne peut etre negatif sur la facture: "+errors);
            }
            if(supplyinvcashDto.getSicashAmountpaid().doubleValue()<0){
                errors.add("--Le montant verse ne peut etre negatif sur la facture: "+errors);
            }
        }
        return errors;
    }
}
