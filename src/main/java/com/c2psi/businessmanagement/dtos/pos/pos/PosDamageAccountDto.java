package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.PosDamageAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class PosDamageAccountDto {
    @ApiModelProperty(value = "The Id of the PosDamageAccount in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The number of damage product cannot be null")
    @PositiveOrZero(message = "The number of damage product must be positive ou null")
    @ApiModelProperty(value = "The number of damage for an article", name = "pdaNumber", dataType = "String")
    BigDecimal pdaNumber;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each damageaccount must belonging to one pointofsale

    @NotNull(message = "The pointofsale owner of the account cannot be null")
    @ApiModelProperty(value = "The pointofsale owner of the damage account", name = "pdaPointofsaleId", dataType = "Long")
    Long pdaPointofsaleId;

    //Each PosDamageAccount is for 1 article but not all article must have an PosDamageAccount

    @NotNull(message = "The article associate with the account cannot be null")
    @ApiModelProperty(value = "The article concerned by the damage account", name = "pdaArticleDto", dataType = "ArticleDto")
    ArticleDto pdaArticleDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosDamageAccountDto fromEntity(PosDamageAccount pda){
        if(pda == null){
            return null;
        }
        return PosDamageAccountDto.builder()
                .id(pda.getId())
                .pdaNumber(pda.getPdaNumber())
                //.pdaPointofsaleDto(PointofsaleDto.fromEntity(pda.getPdaPointofsale()))
                .pdaPointofsaleId(pda.getPdaPointofsaleId())
                .pdaArticleDto(ArticleDto.fromEntity(pda.getPdaArticle()))
                .build();
    }
    public static PosDamageAccount toEntity(PosDamageAccountDto pdaDto){
        if(pdaDto == null){
            return null;
        }
        PosDamageAccount pda = new PosDamageAccount();
        pda.setId(pdaDto.getId());
        pda.setPdaNumber(pdaDto.getPdaNumber());
        //pda.setPdaPointofsale(PointofsaleDto.toEntity(pdaDto.getPdaPointofsaleDto()));
        pda.setPdaPointofsaleId(pdaDto.getPdaPointofsaleId());
        pda.setPdaArticle(ArticleDto.toEntity(pdaDto.getPdaArticleDto()));
        return pda;
    }
}
