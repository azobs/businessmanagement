package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyConversionValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le facteur de conversion ne peut etre negatif
     * *les devises associees ne peuvent etre null
     * *les devises associes doivent appartenir au meme point de vente
     * @param curconvDto
     * @return
     */
    public static List<String> validate(CurrencyConversionDto curconvDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(curconvDto).isPresent()){
            errors.add("--Le parametre CurrencyConversionDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(curconvDto.getCurrencySourceDto()).isPresent()){
                errors.add("--La devise source associe ne saurait etre null: "+errors);
            }
        }
        return errors;
    }
}
