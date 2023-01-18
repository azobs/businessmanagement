package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderDamageOperationValidator {
    /******************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nombre en mouvement (prodoNumberinmvt) ne peut etre negatif
     * *Le compte damage provider (proDamageAccountDto) qui sera affecte au compte
     * ne peut etre null
     * *L'operation (prodoOperationDto) doit etre valide
     * @param prodamopDto
     * @return
     */
    public static List<String> validate(ProviderDamageOperationDto prodamopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(prodamopDto).isPresent()){
            errors.add("--le parametre ProviderDamageOperationDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(prodamopDto.getProdoProDamageAccountDto()).isPresent()){
                errors.add("--Le compte damage associe ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(prodamopDto.getProdoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null: "+errors);
            }
            if(prodamopDto.getProdoNumberinmvt()<0){
                errors.add("--Le nombre en mouvement ne peut etre negatif dans l'operation: "+errors);
            }
            List<String> opt_errors = OperationValidator.validate(
                    prodamopDto.getProdoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }
        }
        return errors;
    }
}
