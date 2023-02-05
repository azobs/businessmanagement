package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.InventoryLine;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class InventoryLineDto {
    Long id;
    String invlineComment;
    @NotNull(message = "The inventory real quantity in stock for an article cannot be null")
    @PositiveOrZero(message = "The inventory real quantity in stock for an article must be positive or zero")
    BigDecimal invlineRealqteinstock;
    @NotNull(message = "The inventory logic quantity in stock for an article cannot be null")
    @PositiveOrZero(message = "The inventory logic quantity in stock for an article must be positive or zero")
    BigDecimal invlineLogicqteinstock;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The associated inventory for the inventory line cannot be null")
    InventoryDto invlineInvDto;
    @NotNull(message = "The associated article for the inventory line cannot be null")
    ArticleDto invlineArtDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static InventoryLineDto fromEntity(InventoryLine invLine){
        if(invLine == null){
            return null;
        }
        return InventoryLineDto.builder()
                .id(invLine.getId())
                .invlineComment(invLine.getInvlineComment())
                .invlineLogicqteinstock(invLine.getInvlineLogicqteinstock())
                .invlineRealqteinstock(invLine.getInvlineRealqteinstock())
                .invlineArtDto(ArticleDto.fromEntity(invLine.getInvlineArt()))
                .invlineInvDto(InventoryDto.fromEntity(invLine.getInvlineInv()))
                .build();
    }

    public static InventoryLine toEntity(InventoryLineDto invLineDto){
        if(invLineDto == null){
            return null;
        }
        InventoryLine invLine = new InventoryLine();
        invLine.setId(invLineDto.getId());
        invLine.setInvlineComment(invLineDto.getInvlineComment());
        invLine.setInvlineLogicqteinstock(invLineDto.getInvlineLogicqteinstock());
        invLine.setInvlineRealqteinstock(invLineDto.getInvlineRealqteinstock());
        invLine.setInvlineArt(ArticleDto.toEntity(invLineDto.getInvlineArtDto()));
        invLine.setInvlineInv(InventoryDto.toEntity(invLineDto.getInvlineInvDto()));
        return invLine;
    }
}
