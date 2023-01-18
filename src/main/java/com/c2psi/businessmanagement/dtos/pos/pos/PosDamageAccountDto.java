package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.PosDamageAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class PosDamageAccountDto {
    Long id;
    @NotNull(message = "The number of damage product cannot be null")
    @Positive(message = "The number of damage product must be positive")
    Integer pdaNumber;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each damageaccount must belonging to one pointofsale

    @NotNull(message = "The pointofsale owner of the account cannot be null")
    PointofsaleDto pdaPointofsaleDto;

    //Each PosDamageAccount is for 1 article but not all article must have an PosDamageAccount

    @NotNull(message = "The article associate with the account cannot be null")
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
                .pdaPointofsaleDto(PointofsaleDto.fromEntity(pda.getPdaPointofsale()))
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
        pda.setPdaPointofsale(PointofsaleDto.toEntity(pdaDto.getPdaPointofsaleDto()));
        pda.setPdaArticle(ArticleDto.toEntity(pdaDto.getPdaArticleDto()));
        return pda;
    }
}
