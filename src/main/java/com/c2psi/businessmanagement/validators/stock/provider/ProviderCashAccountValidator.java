package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderCashAccountValidator {
    /**********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe au compte (pcaPointofsaleDto) ne peut etre null
     * *Le provider associe au compte (pcaProviderDto) ne peut etre null
     * *Le provider doit etre du meme point de vente que celui pour lequel le compte est cree
     * @param procashaccDto
     * @return
     */
    public static List<String> validate(ProviderCashAccountDto procashaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procashaccDto).isPresent()){
            errors.add("--Le parametre ProviderCashAccountDto a valider ne peut etre null: "+errors);
        }
        else{

            /*if(isnullable == 0){
                Long idPosProvider = procashaccDto.getPcaProviderDto()
                        .getProviderPosDto().getId();
                Long idPos_account = procashaccDto.getPcaPointofsaleDto().getId();
                if(!idPosProvider.equals(idPos_account)){
                    errors.add("--Le provider doit appartenir au meme point de vente " +
                            "que le compte: "+errors);
                }
            }*/
        }
        return errors;
    }
}
