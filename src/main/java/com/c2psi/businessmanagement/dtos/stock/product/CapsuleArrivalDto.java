package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.CapsuleArrival;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class CapsuleArrivalDto {
    Long id;
    @PositiveOrZero
    Integer capsaDeliveryquantity;
    @PositiveOrZero
    Integer capsaQuantitycapschanged;
    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull
    ArticleDto capsaArtDto;

    SupplyInvoiceCapsuleDto capsaSicapsDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static CapsuleArrivalDto fromEntity(CapsuleArrival capsuleArrival){
        if(capsuleArrival == null){
            return null;
        }
        return CapsuleArrivalDto.builder()
                .id(capsuleArrival.getId())
                .capsaDeliveryquantity(capsuleArrival.getCapsaDeliveryquantity())
                .capsaQuantitycapschanged(capsuleArrival.getCapsaQuantitycapschanged())
                .capsaArtDto(ArticleDto.fromEntity(capsuleArrival.getCapsaArt()))
                .capsaSicapsDto(SupplyInvoiceCapsuleDto.fromEntity(capsuleArrival.getCapsaSicaps()))
                .build();
    }
    public static CapsuleArrival toEntity(CapsuleArrivalDto capsuleArrivalDto){
        if(capsuleArrivalDto == null){
            return null;
        }
        CapsuleArrival capsa = new CapsuleArrival();
        capsa.setId(capsuleArrivalDto.getId());
        capsa.setCapsaDeliveryquantity(capsuleArrivalDto.getCapsaDeliveryquantity());
        capsa.setCapsaQuantitycapschanged(capsuleArrivalDto.getCapsaQuantitycapschanged());
        capsa.setCapsaArt(ArticleDto.toEntity(capsuleArrivalDto.getCapsaArtDto()));
        capsa.setCapsaSicaps(SupplyInvoiceCapsuleDto.toEntity(capsuleArrivalDto.getCapsaSicapsDto()));
        return capsa;
    }
}
