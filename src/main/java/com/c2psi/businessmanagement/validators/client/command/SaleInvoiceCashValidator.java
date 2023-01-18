package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SaleInvoiceCashValidator {
    /***********************************************************************************
     * *Le parametre à valider ne peut etre null
     * *Le code de la facture de vente (saleicashCode) ne peut etre null ou vide
     * *Le montant à payer (saleicashAmountexpected) ne doit pas etre negatif
     * *le montant verse (saleicashAmountpaid) ne doit pas etre negatif
     * *le montant verse (saleicashAmountreimbourse) ne doit pas etre negatif
     * *La date de facturation (saleicashInvoicingDate) ne peut etre ulterieure à la
     *   date de courante
     *  *La date de facturation (saleicashInvoicingDate) doit toujours etre antérieure
     *  à la date de livraison (saleicashDeliveryDate)
     *  *Le point de vente associe a la facture de vente cash ne saurait etre null
     * @param sicDto
     * @return
     */
    public static List<String> validate(SaleInvoiceCashDto sicDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(sicDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null: "+errors);
        }
        else{
            if(Optional.ofNullable(sicDto.getSaleicashCode()).isPresent()){
                if(!StringUtils.hasLength(sicDto.getSaleicashCode())){
                    errors.add("--Le code de la facture de vente cash ne peut etre vide: "+errors);
                }
            }
           else{
                errors.add("--Le code la facture de vente cash ne peut etre null: "+errors);
            }

           if(sicDto.getSaleicashAmountexpected().doubleValue()<0){
               errors.add("--Le montant attendu pour une facture de vente cash ne saurait etre negatif: "+errors);
           }
            if(sicDto.getSaleicashAmountpaid().doubleValue()<0){
                errors.add("--Le montant paye pour une facture de vente cash ne saurait etre negatif: "+errors);
            }
            if(sicDto.getSaleicashAmountreimbourse().doubleValue()<0){
                errors.add("--Le montant remboursé pour une facture ne saurait etre negatif: "+errors);
            }
            if(sicDto.getSaleicashInvoicingDate().isAfter(new Date().toInstant())){
                errors.add("--La date de facturation ne peut etre ultérieure à la date courante: "+errors);
            }
            if(sicDto.getSaleicashDeliveryDate().isAfter(new Date().toInstant())){
                errors.add("--La date de livraison ne peut etre ultérieure à la date courante car " +
                        "c'est après avoir livré que ce champ prend une valeur: "+errors);
            }
            if(sicDto.getSaleicashDeliveryDate().isBefore(
                    sicDto.getSaleicashInvoicingDate())){
                errors.add("--La date de livraison ne saurait être antérieure à la date de " +
                        "facturation: "+errors);
            }
            if(!Optional.ofNullable(sicDto.getSaleicashPosDto()).isPresent()){
                errors.add("--Le point de vente associe a la facture de vente cash " +
                        "ne doit pas etre null: "+errors);
            }
        }
        return errors;
    }
}
