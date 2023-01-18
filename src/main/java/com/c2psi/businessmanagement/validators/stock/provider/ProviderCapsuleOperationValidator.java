package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderCapsuleOperationValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le nombre de capsule en mouvement (procsoNumberinmvt) ne peut etre negatif
     * *Le compte qui sera affecte par l'operation (proCapsuleAccountDto) ne peut
     * etre null
     * *L'operation associe doit etre valide
     * @param procapsopDto
     * @return
     */
    public static List<String> validate(ProviderCapsuleOperationDto procapsopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procapsopDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(procapsopDto.getProcsoProCapsuleAccountDto()).isPresent()){
                errors.add("--Le compte qui sera affecte par l'operation ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(procapsopDto.getProcsoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null: "+errors);
            }
            if(procapsopDto.getProcsoNumberinmvt()<0){
                errors.add("--Le nombre de capsule en mouvement dans l'operation ne peut " +
                        "etre null: "+errors);
            }
            List<String> opt_errors = OperationValidator.validate(
                    procapsopDto.getProscoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }
        }
        return errors;
    }
}
