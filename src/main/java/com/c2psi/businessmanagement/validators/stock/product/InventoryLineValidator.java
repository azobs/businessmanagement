package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryLineValidator {
    /*********************************************************************************
     * *le parametre a valider ne peut etre null
     * *La quantite reelle en stock () ne peut etre negative
     * *la quantite logique en stock () ne peut etre negative
     * *L'inventaire auquel appartient la ligne d'inventaire ne peut etre null
     * *L'article associe a la ligne d'inventaire ne peut etre null
     * @param invlineDto
     * @return
     */
    public static List<String> validate(InventoryLineDto invlineDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(invlineDto).isPresent()){
            errors.add("--Le parametre InventoryLineDto ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(invlineDto.getInvlineInvDto()).isPresent()){
                errors.add("--L'inventaire associe a la ligne ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(invlineDto.getInvlineArtDto()).isPresent()){
                errors.add("--L'article associe a la ligne d'inventaire ne peut etre null: "+errors);
            }
            if(invlineDto.getInvlineLogicqteinstock().doubleValue()<0){
                errors.add("--la quantite logique en stock de l'article ne peut etre negative: "+errors);
            }
            if(invlineDto.getInvlineRealqteinstock().doubleValue()<0){
                errors.add("--la quantite reelle en stock de l'article ne peut etre negative: "+errors);
            }
        }
        return errors;
    }
}
