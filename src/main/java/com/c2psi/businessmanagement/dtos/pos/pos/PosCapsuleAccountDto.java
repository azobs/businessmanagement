package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.PosCapsuleAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class PosCapsuleAccountDto {
    Long id;
    @NotNull(message = "The number capsule cannot be null")
    @PositiveOrZero(message = "The number of capsule product must be positive ou null")
    BigDecimal pcsaNumber;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each capsuleaccount must belonging to one pointofsale

    @NotNull(message = "The owner pointofsale of the account cannot be null")
    PointofsaleDto pcsaPointofsaleDto;

    //Each PosCapsuleAccount is for 1 article but not all article must have an PosCapsuleAccount

    @NotNull(message = "The article that is concerned by the capsule account cannot be null")
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
                .pcsaPointofsaleDto(PointofsaleDto.fromEntity(pcsa.getPcsaPointofsale()))
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
        pcsa.setPcsaPointofsale(PointofsaleDto.toEntity(pcsaDto.getPcsaPointofsaleDto()));
        pcsa.setPcsaArticle(ArticleDto.toEntity(pcsaDto.getPcsaArticleDto()));
        return pcsa;
    }
}
