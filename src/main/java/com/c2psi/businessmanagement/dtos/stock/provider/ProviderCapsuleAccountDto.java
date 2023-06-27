package com.c2psi.businessmanagement.dtos.stock.provider;


import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ProviderCapsuleAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderCapsuleAccountDto {
    @ApiModelProperty(value = "The Id of the ProviderCapsuleAccountDto in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The number of capsule in the account cannot be null")
    @ApiModelProperty(value = "The number of cover in the account", name = "pcsaNumber", dataType = "BigDecimal")
    BigDecimal pcsaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providercapsuleaccount must belonging to one pointofsale

    //PointofsaleDto pcsaPointofsaleDto;

    //Each providercapsuleaccount must belonging to one provider
    @NotNull(message = "The provider associated cannot be null")
    @ApiModelProperty(value = "The provider that own the account", name = "pcsaProviderDto", dataType = "ProviderDto")
    ProviderDto pcsaProviderDto;

    //Each ProviderCapsuleAccount is for 1 article but not all article must have an ProviderCapsuleAccount

    @NotNull(message = "The article associated cannot be null")
    @ApiModelProperty(value = "The article for which the account is created", name = "pcsaArticleDto", dataType = "ArticleDto")
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
