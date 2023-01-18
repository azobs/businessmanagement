package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductValidator {
    /*************************************************************************************
     * *le parametre a valider ne peut etre null
     * *le nom du produit (prodName) ne peut etre ni null ni vide
     * *La categorie du produit ne peut etre null
     * @param prodDto
     * @return
     */
    public static List<String> validate(ProductDto prodDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(prodDto).isPresent()){
            errors.add("--Le parametre ProductDto a valider ne saurait etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(prodDto.getProdCatDto()).isPresent()){
                errors.add("--La categorie du produit ne peut etre null: "+errors);
            }
            if(!StringUtils.hasLength(prodDto.getProdName())){
                errors.add("--Le nom du produit ne peut etre vide: "+errors);
            }
        }
        return errors;
    }
}
