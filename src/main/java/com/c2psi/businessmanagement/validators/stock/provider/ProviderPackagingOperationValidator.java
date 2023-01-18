package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderPackagingOperationValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le compte packaging associe ne peut etre null
     * *Le nombre de packaging en mouvement ne peut etre negatif
     * *L'operation doit etre valide
     * @param propackopDto
     * @return
     */
    public static List<String> validate(ProviderPackagingOperationDto propackopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(propackopDto).isPresent()){
            errors.add("--Le parametre a valider ProviderPackagingOperationDto ne peut etre: "+errors);
        }
        else{
            if(!Optional.ofNullable(propackopDto.getPropoProPackagingAccountDto()).isPresent()){
                errors.add("--Le compte qui doit subir l'operation ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(propackopDto.getPropoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null: "+errors);
            }
            if(propackopDto.getPropoNumberinmvt()<0){
                errors.add("--Le nombre de package en mouvement ne peut etre negatif: "+errors);
            }
            List<String> opt_errors = OperationValidator.validate(
                    propackopDto.getPropoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }
        }
        return errors;
    }
}
