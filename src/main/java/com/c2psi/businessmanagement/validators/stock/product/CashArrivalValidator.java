package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            errors.add("--Le parametre CashArrivalDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(casharrDto.getCashaSicashDto()).isPresent()){
                errors.add("--La facture associe a cet arrivage ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(casharrDto.getCashaArtDto()).isPresent()){
                errors.add("--L'article associe a cet arrivage ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(casharrDto.getCashaArrivaltype()).isPresent()){
                errors.add("--Le type d'arrivage ne peut etre null: "+errors);
            }
            else{
                if(!casharrDto.getCashaArrivaltype().equals(CashArrivalType.Divers) &&
                    !casharrDto.getCashaArrivaltype().equals(CashArrivalType.Standard)){
                    errors.add("--Type d'arrivage non reconnu par le systeme: "+errors);
                }
            }
            if(casharrDto.getCashaDeliveryquantity()<0){
                errors.add("--La quantite livree ne saurait etre negative: "+errors);
            }
            if(casharrDto.getCashaUnitprice().doubleValue()<0){
                errors.add("--le prix unitaire de l'article livre ne peut etre negatif: "+errors);
            }
        }
        return errors;
    }
}
