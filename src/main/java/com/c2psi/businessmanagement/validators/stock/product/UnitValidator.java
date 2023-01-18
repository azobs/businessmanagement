package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitValidator {
    /*********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom de l'unite (unitName) et son abbreviation (unitAbbreviation)
     * ne sauront etre ni vide ni null
     * *La taille de l'abbreviation ne peut etre superieur a celle du nom
     * *Le point de vente de l'unite (unitPosDto) ne saurait etre null
     * @param unitDto
     * @return
     */
    public static List<String> validate(UnitDto unitDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(unitDto).isPresent()){
            errors.add("--Le parametre unitDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!StringUtils.hasLength(unitDto.getUnitName())){
                errors.add("--Le nom de l'unite ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(unitDto.getUnitAbbreviation())){
                errors.add("--L'abbreviation de l'unite ne peut etre vide: "+errors);
            }
            if(unitDto.getUnitAbbreviation().length() >= unitDto.getUnitName().length()){
                errors.add("--L'abbreviation de l'unite ne peut etre plus long que son nom: "+errors);
            }
            if(!Optional.ofNullable(unitDto.getUnitPosDto()).isPresent()){
                errors.add("--Le point de vente associe a l'unite ne peut etre null: "+errors);
            }
        }
        return errors;
    }
}
