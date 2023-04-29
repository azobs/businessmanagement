package com.c2psi.businessmanagement.dtos.stock.provider;


import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ProviderCapsuleAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderCapsuleAccountDto {
    Long id;
    @NotNull(message = "The number of capsule in the account cannot be null")
    BigDecimal pcsaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providercapsuleaccount must belonging to one pointofsale

    //PointofsaleDto pcsaPointofsaleDto;

    //Each providercapsuleaccount must belonging to one provider
    @NotNull(message = "The provider associated cannot be null")
    ProviderDto pcsaProviderDto;

    //Each ProviderCapsuleAccount is for 1 article but not all article must have an ProviderCapsuleAccount

    @NotNull(message = "The article associated cannot be null")
    ArticleDto pcsaArticleDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderCapsuleAccountDto fromEntity(ProviderCapsuleAccount pca){
        if(pca == null){
            return null;
        }
        return ProviderCapsuleAccountDto.builder()
                .id(pca.getId())
                .pcsaNumber(pca.getPcsaNumber())
                .pcsaArticleDto(ArticleDto.fromEntity(pca.getPcsaArticle()))
                .pcsaProviderDto(ProviderDto.fromEntity(pca.getPcsaProvider()))
                .build();
    }
    public static ProviderCapsuleAccount toEntity(ProviderCapsuleAccountDto pcaDto){
        if(pcaDto == null){
            return null;
        }
        ProviderCapsuleAccount pca = new ProviderCapsuleAccount();
        pca.setId(pcaDto.getId());
        pca.setPcsaArticle(ArticleDto.toEntity(pcaDto.getPcsaArticleDto()));
        pca.setPcsaNumber(pcaDto.getPcsaNumber());
        pca.setPcsaProvider(ProviderDto.toEntity(pcaDto.getPcsaProviderDto()));
        return pca;
    }
}
