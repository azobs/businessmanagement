package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom de la devise (currencyName) ne peut etre ni null ni vide
     * *Le shortname de la devise ne peut etre ni null ni vide
     * *Le point de vente associe a la devise ne peut etre null
     * @param curDto
     * @return
     */
    public static List<String> validate(CurrencyDto curDto){
        List<String> errors  = new ArrayList<>();
        if(!Optional.ofNullable(curDto).isPresent()){
            errors.add("--Le parametre CurrencyDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!StringUtils.hasLength(curDto.getCurrencyName())){
                errors.add("--Le nom de la devise ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(curDto.getCurrencyShortname())){
                errors.add("--Le shortname de la devise ne peut etre vide: "+errors);
            }

        }
        return errors;
    }
}
