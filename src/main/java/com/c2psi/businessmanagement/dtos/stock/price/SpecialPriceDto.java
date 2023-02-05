package com.c2psi.businessmanagement.dtos.stock.price;

import com.c2psi.businessmanagement.models.SpecialPrice;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class SpecialPriceDto {
    Long id;
    @NotNull(message = "The special whole price cannot be null")
    @Positive(message = "The special whole price must be positive")
    BigDecimal spWholesaleprice;
    @NotNull(message = "The special detail price cannot be null")
    @Positive(message = "The special detail price must be positive")
    BigDecimal spDetailprice;
    @NotNull(message = "The special semi whole price cannot be null")
    @Positive(message = "The special semi whole price must be positive")
    BigDecimal spSemiwholesaleprice;
    @NotNull(message = "The special precompte cannot be null")
    @PositiveOrZero(message = "The special precompte must be positive or zero")
    BigDecimal spPrecompte;
    @NotNull(message = "The special ristourne cannot be null")
    @PositiveOrZero(message = "The special ristourne must be positive or zero")
    BigDecimal spRistourne;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many specialprice is based on 1 baseprice

    @NotNull(message = "The base price associated cannot be null")
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
