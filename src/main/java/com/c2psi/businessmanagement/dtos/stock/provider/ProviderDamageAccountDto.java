package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ProviderDamageAccount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderDamageAccountDto {
    Long id;
    Integer pdaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providerdamageaccount must belonging to one pointofsale

    //PointofsaleDto pdaPosDto;

    //Each providerdamageaccount must belonging to one provider

    ProviderDto pdaProviderDto;

    //Each ProviderdamageAccount is for 1 article but not all article must have an ProviderdamageAccount

    ArticleDto pdaArticleDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderDamageAccountDto fromEntity(ProviderDamageAccount pda){
        if(pda == null){
            return null;
        }
        return ProviderDamageAccountDto.builder()
                .id(pda.getId())
                .pdaNumber(pda.getPdaNumber())
                .pdaArticleDto(ArticleDto.fromEntity(pda.getPdaArticle()))
                .pdaProviderDto(ProviderDto.fromEntity(pda.getPdaProvider()))
                .build();
    }
    public static ProviderDamageAccount toEntity(ProviderDamageAccountDto pdaDto){
        if(pdaDto == null){
            return null;
        }
        ProviderDamageAccount pda = new ProviderDamageAccount();
        pda.setId(pdaDto.getId());
        pda.setPdaNumber(pdaDto.getPdaNumber());
        pda.setPdaArticle(ArticleDto.toEntity(pdaDto.getPdaArticleDto()));
        pda.setPdaProvider(ProviderDto.toEntity(pdaDto.getPdaProviderDto()));
        return pda;
    }
}
