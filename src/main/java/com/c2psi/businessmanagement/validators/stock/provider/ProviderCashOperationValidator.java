package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderCashOperationValidator {
    /************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le montant en mouvement dans l'operation ne peut etre null
     * *Le compte cash associe ne peut etre null
     * *L'operation doit etre valide
     * @param procashopDto
     * @return
     */
    public static List<String> validate(ProviderCashOperationDto procashopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procashopDto).isPresent()){
            errors.add("--Le parametre a valider ProviderCashOperationDto ne peut etre null: "+errors);
        }
        else{
            if(procashopDto.getPcoAmountinmvt().doubleValue()<0){
                errors.add("--Le montant en mouvement ne peut etre negatif: "+errors);
            }
            if(!Optional.ofNullable(procashopDto.getPcoProCashAccountDto()).isPresent()){
                errors.add("--Le compte qui subira l'operation ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(procashopDto.getPcoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null: "+errors);
            }
            List<String> opt_errors = OperationValidator.validate(
                    procashopDto.getPcoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }
        }
        return errors;
    }
}
