package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.DamageArrival;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class DamageArrivalDto {
    Long id;
    @NotNull(message = "The delivery quantity cannot be null")
    @Positive(message = "The delivery quantity must be positive")
    BigDecimal damaDeliveryquantity;
    @NotNull(message = "The quantity changed cannot be null")
    @Positive(message = "The quantity changed must be positive")
    BigDecimal damaQuantityartchanged;

    @NotNull(message = "The entry date cannot be null")
    @PastOrPresent(message = "The entry date cannot be in the future")
    Instant damaArrivalEntryDate;

    @NotNull(message = "The article associated to the damage arrival cannot be null")
    ArticleDto damaArtDto;
    SupplyInvoiceDamageDto damaSidamDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static DamageArrivalDto fromEntity(DamageArrival damArrival) {
        if (damArrival == null) {
            return null;
        }
        return DamageArrivalDto.builder()
                .id(damArrival.getId())
                .damaArtDto(ArticleDto.fromEntity(damArrival.getDamaArt()))
                .damaDeliveryquantity(damArrival.getDamaDeliveryquantity())
                .damaQuantityartchanged(damArrival.getDamaQuantityartchanged())
                .damaSidamDto(SupplyInvoiceDamageDto.fromEntity(damArrival.getDamaSidam()))
                .damaArrivalEntryDate(damArrival.getDamaArrivalEntryDate())
                .build();
    }

    public static DamageArrival toEntity(DamageArrivalDto damArrivalDto) {
        if (damArrivalDto == null) {
            return null;
        }
        DamageArrival da = new DamageArrival();
        da.setId(damArrivalDto.getId());
        da.setDamaArt(ArticleDto.toEntity(damArrivalDto.getDamaArtDto()));
        da.setDamaDeliveryquantity(damArrivalDto.getDamaDeliveryquantity());
        da.setDamaQuantityartchanged(damArrivalDto.getDamaQuantityartchanged());
        da.setDamaSidam(SupplyInvoiceDamageDto.toEntity(damArrivalDto.getDamaSidamDto()));
        da.setDamaArrivalEntryDate(damArrivalDto.getDamaArrivalEntryDate());
        return da;
    }
}
