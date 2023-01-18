package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FormatValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom du format (formatName) ne peut etre ni null ni vide
     * *la capacite du format (formatCapacity) ne peut etre negative
     * *Le point de vente du format (formatPosDto) ne peut etre null
     * @param formatDto
     * @return
     */
    public static List<String> validate(FormatDto formatDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(formatDto).isPresent()){
            errors.add("--Le parametre FormatDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(formatDto.getFormatPosDto()).isPresent()){
                errors.add("--Le point de vente associe au format ne peut etre null: "+errors);
            }
            if(formatDto.getFormatCapacity().doubleValue()<0){
                errors.add("--La capacite du format ne saurait etre negative: "+errors);
            }
            if(!StringUtils.hasLength(formatDto.getFormatName())){
                errors.add("--Le nom du format ne peut etre vide: "+errors);
            }
        }
        return errors;
    }
}
