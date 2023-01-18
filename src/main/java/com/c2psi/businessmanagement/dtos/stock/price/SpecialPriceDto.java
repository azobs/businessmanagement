package com.c2psi.businessmanagement.dtos.stock.price;

import com.c2psi.businessmanagement.models.SpecialPrice;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class SpecialPriceDto {
    Long id;
    @PositiveOrZero
    BigDecimal spWholesaleprice;
    @PositiveOrZero
    BigDecimal spDetailprice;
    @PositiveOrZero
    BigDecimal spSemiwholesaleprice;
    @PositiveOrZero
    BigDecimal spPrecompte;
    @PositiveOrZero
    BigDecimal spRistourne;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many specialprice is based on 1 baseprice

    @NotNull
    BasePriceDto spBasepriceDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SpecialPriceDto fromEntity(SpecialPrice sp){
        if(sp == null){
            return null;
        }
        return SpecialPriceDto.builder()
                .id(sp.getId())
                .spWholesaleprice(sp.getSpWholesaleprice())
                .spDetailprice(sp.getSpDetailprice())
                .spSemiwholesaleprice(sp.getSpSemiwholesaleprice())
                .spPrecompte(sp.getSpPrecompte())
                .spRistourne(sp.getSpRistourne())
                .spBasepriceDto(BasePriceDto.fromEntity(sp.getSpBaseprice()))
                .build();
    }
    public static SpecialPrice toEntity(SpecialPriceDto spDto){
        if(spDto == null){
            return null;
        }
        SpecialPrice sp =  new SpecialPrice();
        sp.setId(spDto.getId());
        sp.setSpWholesaleprice(spDto.getSpWholesaleprice());
        sp.setSpDetailprice(spDto.getSpDetailprice());
        sp.setSpSemiwholesaleprice(spDto.getSpSemiwholesaleprice());
        sp.setSpPrecompte(spDto.getSpPrecompte());
        sp.setSpRistourne(spDto.getSpRistourne());
        sp.setSpBaseprice(BasePriceDto.toEntity(spDto.getSpBasepriceDto()));
        return sp;
    }
}
