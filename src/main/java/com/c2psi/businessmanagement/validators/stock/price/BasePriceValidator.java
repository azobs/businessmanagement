package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasePriceValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le prix d'achat moyen (bpPurchaseprice) ne peut etre negatif
     * *Le prix de vente en gros (bpWholesaleprice) ne peut etre negatif
     * *le prix de vente en semi gros (bpSemiwholesaleprice) ne peut etre negatif
     * *Le prix de vente en details (bpDetailprice) ne peut etre negatif
     * *Le montant des precomptes (bpPrecompte) ne peut etre negatif
     * *Le montant des ristournes (bpRistourne) ne peut etre negatif
     * *La devise ou monnaie (bpCurrencyDto) associe au prix ne peut etre null
     * *L'article associe (bp_artDto) ne peut etre null
     * *L'article (bp_artDto) et la devise (bpCurrencyDto) doivent appartienir au
     * meme point de vente
     * @param bpDto
     * @return
     */
    public static List<String> validate(BasePriceDto bpDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(bpDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null: "+errors);
        }
        else{
            if(bpDto.getBpPurchaseprice().doubleValue()<0){
                errors.add("--Le prix d'achat de base ne peut etre negatif: "+errors);
            }
            if(bpDto.getBpDetailprice().doubleValue()<0){
                errors.add("--Le prix de detail de base ne peut etre negatif: "+errors);
            }
            if(bpDto.getBpPrecompte().doubleValue()<0){
                errors.add("--La precompte de base ne peut etre negatif: "+errors);
            }
            if(bpDto.getBpRistourne().doubleValue()<0){
                errors.add("--La ristourne de base ne peut etre negatif: "+errors);
            }
            if(bpDto.getBpSemiwholesaleprice().doubleValue()<0){
                errors.add("--Le prix de semi gros de base ne peut etre negatif: "+errors);
            }
            if(bpDto.getBpWholesaleprice().doubleValue()<0){
                errors.add("--Le prix de gros de base ne peut etre negatif: "+errors);
            }

            if(!Optional.ofNullable(bpDto.getBpCurrencyDto()).isPresent()){
                errors.add("--La devise ou monnaie associe au prix de base ne peut etre null: "+errors);
            }

        }
        return errors;
    }
}
