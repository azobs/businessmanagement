package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.PosCapsuleAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class PosCapsuleAccountDto {
    @ApiModelProperty(value = "The Id of the PosCapsuleAccount in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The number capsule cannot be null")
    @PositiveOrZero(message = "The number of capsule product must be positive ou null")
    @ApiModelProperty(value = "The number of cover for an article", name = "pcsaNumber", dataType = "BigDecimal")
    BigDecimal pcsaNumber;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each capsuleaccount must belonging to one pointofsale

    @NotNull(message = "The owner pointofsale of the account cannot be null")
    @ApiModelProperty(value = "The id of the pointofsale associate", name = "pcsaPointofsaleId", dataType = "Long")
    Long pcsaPointofsaleId;

    //Each PosCapsuleAccount is for 1 article but not all article must have an PosCapsuleAccount

    @NotNull(message = "The article that is concerned by the capsule account cannot be null")
    @ApiModelProperty(value = "The article associate", name = "pcsaArticleDto", dataType = "ArticleDto")
    ArticleDto pcsaArticleDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosCapsuleAccountDto fromEntity(PosCapsuleAccount pcsa){
        if(pcsa == null){
            return null;
        }
        return PosCapsuleAccountDto.builder()
                .id(pcsa.getId())
                .pcsaNumber(pcsa.getPcsaNumber())
                //.pcsaPointofsaleDto(PointofsaleDto.fromEntity(pcsa.getPcsaPointofsale()))
                .pcsaPointofsaleId(pcsa.getPcsaPointofsaleId())
                .pcsaArticleDto(ArticleDto.fromEntity(pcsa.getPcsaArticle()))
                .build();
    }
    public static PosCapsuleAccount toEntity(PosCapsuleAccountDto pcsaDto){
        if(pcsaDto == null){
            return null;
        }
        PosCapsuleAccount pcsa = new PosCapsuleAccount();
        pcsa.setId(pcsaDto.getId());
        pcsa.setPcsaNumber(pcsaDto.getPcsaNumber());
        //pcsa.setPcsaPointofsale(PointofsaleDto.toEntity(pcsaDto.getPcsaPointofsaleDto()));
        pcsa.setPcsaPointofsaleId(pcsaDto.getPcsaPointofsaleId());
        pcsa.setPcsaArticle(ArticleDto.toEntity(pcsaDto.getPcsaArticleDto()));
        return pcsa;
    }
}
