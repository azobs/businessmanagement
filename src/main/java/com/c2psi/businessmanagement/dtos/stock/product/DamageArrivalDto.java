package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.CashArrival;
import com.c2psi.businessmanagement.models.DamageArrival;
import com.c2psi.businessmanagement.models.SupplyInvoiceDamage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class DamageArrivalDto {
    Long id;
    @NotNull(message = "The delivery quantity cannot be null")
    @Positive(message = "The delivery quantity must be positive")
    Integer damaDeliveryquantity;
    @NotNull(message = "The quantity changed cannot be null")
    @Positive(message = "The quantity changed must be positive")
    Integer damaQuantityartchanged;
    @NotNull(message = "The article associated to the damage arrival cannot be null")
    ArticleDto damaArt;
    SupplyInvoiceDamageDto damaSidam;
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
                .damaArt(ArticleDto.fromEntity(damArrival.getDamaArt()))
                .damaDeliveryquantity(damArrival.getDamaDeliveryquantity())
                .damaQuantityartchanged(damArrival.getDamaQuantityartchanged())
                .damaSidam(SupplyInvoiceDamageDto.fromEntity(damArrival.getDamaSidam()))
                .build();
    }

    public static DamageArrival toEntity(DamageArrivalDto damArrivalDto) {
        if (damArrivalDto == null) {
            return null;
        }
        DamageArrival da = new DamageArrival();
        da.setId(damArrivalDto.getId());
        da.setDamaArt(ArticleDto.toEntity(damArrivalDto.getDamaArt()));
        da.setDamaDeliveryquantity(damArrivalDto.getDamaDeliveryquantity());
        da.setDamaQuantityartchanged(damArrivalDto.getDamaQuantityartchanged());
        da.setDamaSidam(SupplyInvoiceDamageDto.toEntity(damArrivalDto.getDamaSidam()));
        return da;
    }
}
