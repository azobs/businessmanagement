package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderCapsuleAccountValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *L'article lie au compte (pcsaArticleDto) ne peut etre null
     * *Le provider lie au compte (pcsaProviderDto) ne peut etre null
     * *L'article et le provider lie au compte doivent tous appartenir au point de vente
     * *le compte doit etre lie au meme point de vente que celui de l'article et du provider
     * @param procapsaccDto
     * @return
     */
    public static List<String> validate(ProviderCapsuleAccountDto procapsaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(procapsaccDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(procapsaccDto.getPcsaArticleDto()).isPresent()){
                errors.add("--L'article lie au compte ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(procapsaccDto.getPcsaProviderDto()).isPresent()){
                errors.add("--Le provider lie au compte ne peut etre null: "+errors);
            }

            /*else{
                if(!procapsaccDto.getPcsaPointofsaleDto().getId().equals(
                        procapsaccDto.getPcsaArticleDto().getArtPosDto().getId())){
                    errors.add("--L'article lie au compte doit appartenir au meme point " +
                            "de vente que le compte lui meme: "+errors);
                }
                if(!procapsaccDto.getPcsaPointofsaleDto().getId().equals(
                        procapsaccDto.getPcsaProviderDto().getProviderPosDto().getId())){
                    errors.add("--Le provider lie au compte doit appartenir au meme point " +
                            "de vente que le compte lui meme: "+errors);
                }
                if(!procapsaccDto.getPcsaArticleDto().getArtPosDto().getId().equals(
                        procapsaccDto.getPcsaProviderDto().getProviderPosDto().getId())){
                    errors.add("--Le provider et l'article lie au compte doivent appartenir " +
                            "au même point de vente: "+errors);
                }
            }*/
        }
        return errors;
    }
}
