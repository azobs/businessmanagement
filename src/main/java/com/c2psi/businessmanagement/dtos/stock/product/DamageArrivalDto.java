package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.CashArrival;
import com.c2psi.businessmanagement.models.DamageArrival;
import com.c2psi.businessmanagement.models.SupplyInvoiceDamage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class DamageArrivalDto {
    Long id;
    @PositiveOrZero
    Integer damaDeliveryquantity;
    @PositiveOrZero
    Integer damaQuantityartchanged;
    @NotNull
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
