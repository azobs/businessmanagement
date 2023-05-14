package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.CapsuleArrival;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class CapsuleArrivalDto {
    Long id;
    @NotNull(message = "The delivery quantity cannot be null")
    @Positive(message = "The delivery quantity of capsule changed must be positive")
    BigDecimal capsaDeliveryquantity;
    @NotNull(message = "The quantity capsule changed cannot be null")
    @Positive(message = "The quantity capsule changed must be positive")
    BigDecimal capsaQuantitycapschanged;
    @NotNull(message = "The entry date cannot be null")
    @PastOrPresent(message = "The entry date cannot be in the future")
    Instant capsaArrivalEntryDate;


    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The article associated with the capsule arrival cannot be null")
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
                .capsaArrivalEntryDate(capsuleArrival.getCapsaArrivalEntryDate())
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
        capsa.setCapsaArrivalEntryDate(capsuleArrivalDto.getCapsaArrivalEntryDate());
        return capsa;
    }
}
