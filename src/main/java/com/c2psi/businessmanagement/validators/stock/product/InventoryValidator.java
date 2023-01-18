package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class InventoryValidator {
    /*************************************************************************************
     * *le parametre a valider ne peut etre null
     * *la date de l'inventaire ne peut etre ulterieure a la date courante
     * *le code l'inventaire ne peut etre ni null ni vide
     * *le point de vente pour lequel l'inventaire est enregistre ne peut etre null
     * @param invDto
     * @return
     */
    public static List<String> validate(InventoryDto invDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(invDto).isPresent()){
            errors.add("--le parametre InventoryDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(invDto.getInvPosDto()).isPresent()){
                errors.add("--Le point de vente de l'inventaire ne peut etre null: "+errors);
            }
            Instant dateCourante = new Date().toInstant();
            if (dateCourante.isBefore(invDto.getInvDate())) {
                errors.add("--La date de l'inventaire ne saurait etre ultérieure a " +
                        "la date courante: "+errors);
            }
            if(!StringUtils.hasLength(invDto.getInvCode())){
                errors.add("--Le nom du format ne peut etre vide: "+errors);
            }
        }
        return errors;
    }
}
