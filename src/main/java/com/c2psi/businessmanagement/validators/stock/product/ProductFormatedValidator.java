package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductFormatedValidator {
    /***********************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le produit associe ne peut etre null
     * *le format utilise ne peut etre null
     * @param prodformDto
     * @return
     */
    public static List<String> validate(ProductFormatedDto prodformDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(prodformDto).isPresent()){
            errors.add("--Le parametre ProductFormatedDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(prodformDto.getPfFormatDto()).isPresent()){
                errors.add("--Le format utilise ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(prodformDto.getPfProductDto()).isPresent()){
                errors.add("--Le produit associe ne peut etre null: "+errors);
            }
        }
        return  errors;
    }
}
