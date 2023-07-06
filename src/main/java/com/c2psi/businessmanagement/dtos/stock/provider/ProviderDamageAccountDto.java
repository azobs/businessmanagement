package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ProviderDamageAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderDamageAccountDto {
    @ApiModelProperty(value = "The Id of the ProviderDamageAccountDto in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The number of damage product cannot be null")
    @ApiModelProperty(value = "The number of Damage article in the DB", name = "pdaNumber", dataType = "String")
    BigDecimal pdaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providerdamageaccount must belonging to one pointofsale

    //PointofsaleDto pdaPosDto;

    //Each providerdamageaccount must belonging to one provider
    @NotNull(message = "The provider associated with the account cannot be null")
    @ApiModelProperty(value = "The provider link with the DB", name = "pdaProviderDto", dataType = "ProviderDto")
    ProviderDto pdaProviderDto;

    //Each ProviderdamageAccount is for 1 article but not all article must have an ProviderdamageAccount
    @NotNull(message = "The article associated with the account cannot be null")
    @ApiModelProperty(value = "The article link with the DB", name = "pdaArticleDto", dataType = "ArticleDto")
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
