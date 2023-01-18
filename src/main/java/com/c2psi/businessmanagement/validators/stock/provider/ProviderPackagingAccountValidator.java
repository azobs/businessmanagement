package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderPackagingAccountValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe () ne peut etre null
     * *Le provider associe () ne peut etre null
     * *Le packaging associe () ne peut etre null
     * *Le packaging, le provider et le compte meme doivent etre du meme point de vente
     *
     * @param propackaccDto
     * @return
     */
    public static List<String> validate(ProviderPackagingAccountDto propackaccDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(propackaccDto).isPresent()){
            errors.add("--Le parametre ProviderPackagingAccountDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(propackaccDto.getPpaProviderDto()).isPresent()){
                errors.add("--Le provider associe au compte ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(propackaccDto.getPpaPackagingDto()).isPresent()){
                errors.add("Le packaging associe au compte ne peut etre null: "+errors);
            }
            /*if(isnullable == 0){
                Long idPos_acc = propackaccDto.getPpaPosDto().getId();
                Long idPosPro = propackaccDto.getPpaProviderDto()
                        .getProviderPosDto().getId();
                Long idPosPack = propackaccDto.getPpaPackagingDto()
                        .getPackagingPosDto().getId();
                if(!idPos_acc.equals(idPosPro)){
                    errors.add("--Le provider doit etre du meme point de vente que " +
                            "celui du compte cree: "+errors);
                }
                if(!idPos_acc.equals(idPosPack)){
                    errors.add("--Le point de vente du packaging doit etre le meme " +
                            "que celui du compte: "+errors);
                }
                if(!idPosPack.equals(idPosPro)){
                    errors.add("--Le point de vente du packaging et celui du provider " +
                            "doivent etre les meme: "+errors);
                }
            }*/

        }
        return errors;
    }
}
