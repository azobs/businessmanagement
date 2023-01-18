package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PackagingValidator {
    /*************************************************************************************
     * *le parametre a valider ne peut etre null
     * *le label du packaging () ne peut etre ni vide ni null
     * *la couleur du packaging () ne peut etre ni null ni vide
     * *le prix du packaging () ne peut etre negative
     * *le provider du packaging ne peut etre null
     * @param packagingDto
     * @return
     */
    public static List<String> validate(PackagingDto packagingDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(packagingDto).isPresent()){
            errors.add("--Le parametre PackagingDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(packagingDto.getPackProviderDto()).isPresent()){
                errors.add("--Le fournisseur officiel du packaging ne peut etre null: "+errors);
            }
            if(!StringUtils.hasLength(packagingDto.getPackLabel())){
                errors.add("--Le label du packaging ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(packagingDto.getPackFirstcolor())){
                errors.add("--La couleur principale du packaging ne peut etre vide: "+errors);
            }
            if(packagingDto.getPackPrice().doubleValue()<0){
                errors.add("--le prix du packaging ne peut etre negatif: "+errors);
            }
        }
        return errors;
    }
}
