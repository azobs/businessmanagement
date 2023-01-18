package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SaleInvoiceCapsuleValidator {
    /**********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le code de la facture de vente (saleicapsCode) ne peut etre null ou vide
     * *Le nombre de capsule à changer (saleicapsNumbertochange) doit etre positif
     * ou nul
     * *Le nombre de capsule changé (saleicapsNumberchanged) doit etre positif et
     * inférieur ou egale au nombre de capsule a changer (saleicapsNumbertochange)
     * *La date de facturation (saleicapsInvoicingDate) ne peut etre ulterieure à la
     * date de courante
     * *La date de facturation (saleicapsInvoicingDate) doit toujours etre antérieure
     * à la date de livraison (saleicapsDeliveryDate)
     * *Le point de vente asssocie a la facture ne saurait etre null
     * @param sicapsDto
     * @return
     */
   public static List<String> validate(SaleInvoiceCapsuleDto sicapsDto){
       List<String> errors = new ArrayList<>();
       if(!Optional.ofNullable(sicapsDto).isPresent()){
           errors.add("--Le paramètre à valider ne peut etre null: "+errors);
       }
       else{
           if(Optional.ofNullable(sicapsDto.getSaleicapsCode()).isPresent()){
               if(!StringUtils.hasLength(sicapsDto.getSaleicapsCode())){
                   errors.add("--Le code de la facture de vente capsule ne peut etre vide: "+errors);
               }
           }
           else{
               errors.add("--Le code la facture de vente capsule ne peut etre null: "+errors);
           }
           if(sicapsDto.getSaleicapsNumbertochange().intValue()<=0){
               errors.add("--Le nombre de capsule a changer ne saurait etre negatif ou null: "+errors);
           }
           if(sicapsDto.getSaleicapsNumberchanged().intValue()<=0){
               errors.add("--Le nombre de capsule changé ne saurait etre negatif ou null: "+errors);
           }
           if(sicapsDto.getSaleicapsNumberchanged().intValue()>
                   sicapsDto.getSaleicapsNumberchanged().intValue()){
               errors.add("--Le nombre de capsule changé ne peut etre supérieur au nombre " +
                       "de capsule à changer: "+errors);
           }
           if(sicapsDto.getSaleicapsInvoicingDate().isAfter(new Date().toInstant())){
               errors.add("--La date de facturation ne peut etre ultérieure à la date courante: "+errors);
           }
           if(sicapsDto.getSaleicapsDeliveryDate().isAfter(new Date().toInstant())){
               errors.add("--La date de livraison ne peut etre ultérieure à la date courante car " +
                       "c'est après avoir livré que ce champ prend une valeur: "+errors);
           }
           if(sicapsDto.getSaleicapsDeliveryDate().isBefore(
                   sicapsDto.getSaleicapsInvoicingDate())){
               errors.add("--La date de livraison ne saurait être antérieure à la date de " +
                       "facturation: "+errors);
           }
           if(!Optional.ofNullable(sicapsDto.getSaleicapsPosDto()).isPresent()){
               errors.add("--Le point de vente associe a la facture de vente capsule " +
                       "ne doit pas etre null: "+errors);
           }
       }
       return errors;
   }
}
