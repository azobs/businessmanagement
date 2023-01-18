package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderDamageAccountValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe au compte (pdaPosDto) ne peut etre null
     * *Le provider associe au compte (pdaProviderDto) ne peut etre null
     * *L'article associe au compte (pdaArticleDto) ne peut etre null
     * *L'article et le provider doivent etre du meme point de vente que celui du compte
     * @param prodamaccDto
     * @return
     */
    public static List<String> validate(ProviderDamageAccountDto prodamaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(prodamaccDto).isPresent()){
            errors.add("--Le parametre a valider ProviderDamageAccountDto ne peut etre null: "+errors);
        }
        else{

            if(!Optional.ofNullable(prodamaccDto.getPdaArticleDto()).isPresent()){
                errors.add("--L'article associe au compte ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(prodamaccDto.getPdaProviderDto()).isPresent()){
                errors.add("--Le provider associe au compte ne peut etre null: "+errors);
            }
            /*if(isnullable == 0){
                Long idPos = prodamaccDto.getPdaPosDto().getId();
                Long idPosArt = prodamaccDto.getPdaArticleDto().getArtPosDto().getId();
                Long idPosPro = prodamaccDto.getPdaProviderDto().getProviderPosDto().getId();
                if(!idPos.equals(idPosArt)){
                    errors.add("--Le point de vente du compte doit etre le meme que celui de " +
                            "l'article lie au compte: "+errors);
                }
                if(!idPos.equals(idPosPro)){
                    errors.add("--Le point de vente du compte doit etre le meme que celui du " +
                            " provider lie au compte: "+errors);
                }
                if(!idPosArt.equals(idPosPro)){
                    errors.add("--L'article et le provider doivent etre du même point de vente: "+errors);
                }
            }*/

        }
        return errors;
    }
}
