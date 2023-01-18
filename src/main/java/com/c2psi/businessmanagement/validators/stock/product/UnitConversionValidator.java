package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitConversionValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le facteur de conversion (conversionFactor) ne peut etre negatif
     * *L'unite source (unitSourceDto) et l'unite destination (unitDestinationDto)
     * ne peuvent etre null
     * @param unitConversionDto
     * @return
     */
    public static List<String> validate(UnitConversionDto unitConversionDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(unitConversionDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(unitConversionDto.getUnitSourceDto()).isPresent()){
                errors.add("--L'unite source associe a la regle ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(unitConversionDto.getUnitDestinationDto()).isPresent()){
                errors.add("--L'unite destination associe a la regle ne peut etre null: "+errors);
            }
            if(unitConversionDto.getConversionFactor()<0){
                errors.add("--Le facteur de conversion dans la regle ne peut etre negatif: "+errors);
            }
        }
        return errors;
    }
}
