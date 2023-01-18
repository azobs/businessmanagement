package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialPriceValidator {
    /*********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *
     * @param spDto
     * @return
     */
    public static List<String> validate(SpecialPriceDto spDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(spDto).isPresent()){
            errors.add("--Le parametre SpecialPriceDto a valider ne peut etre null: "+errors);
        }
        else {
            if (spDto.getSpDetailprice().doubleValue() < 0) {
                errors.add("--Le prix de detail special ne peut etre negatif: "+errors);
            }
            if (spDto.getSpPrecompte().doubleValue() < 0) {
                errors.add("--La precompte special ne peut etre negatif: "+errors);
            }
            if (spDto.getSpRistourne().doubleValue() < 0) {
                errors.add("--La ristourne special ne peut etre negatif: "+errors);
            }
            if (spDto.getSpSemiwholesaleprice().doubleValue() < 0) {
                errors.add("--Le prix de semi gros special ne peut etre negatif: "+errors);
            }
            if (spDto.getSpWholesaleprice().doubleValue() < 0) {
                errors.add("--Le prix de gros special ne peut etre negatif: "+errors);
            }
            if (!Optional.ofNullable(spDto.getSpBasepriceDto()).isPresent()) {
                errors.add("--Le prix de base associe au prix special ne peut etre null: "+errors);
            }
        }
        return errors;
    }
}
