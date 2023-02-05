package com.c2psi.businessmanagement.dtos.stock.price;

import com.c2psi.businessmanagement.models.CurrencyConversion;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class CurrencyConversionDto {
    Long id;
    @NotNull(message = "The conversion factor cannot be null")
    @Positive(message = "The converion factor must be positive")
    BigDecimal conversionFactor;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unitconversion for 1 unit source

    @NotNull(message = "The currency source of the rule cannot be null")
    CurrencyDto currencySourceDto;
    //Many unitconversion for 1 unit destination

    @NotNull(message = "The currency destination of the rule cannot be null")
    CurrencyDto currencyDestinationDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static CurrencyConversionDto fromEntity(CurrencyConversion cc){
        if(cc == null){
            return null;
        }
        return CurrencyConversionDto.builder()
                .id(cc.getId())
                .conversionFactor(cc.getConversionFactor())
                .currencySourceDto(CurrencyDto.fromEntity(cc.getCurrencySource()))
                .currencyDestinationDto(CurrencyDto.fromEntity(cc.getCurrencyDestination()))
                .build();
    }
    public static  CurrencyConversion toEntity(CurrencyConversionDto ccDto){
        if(ccDto == null){
            return null;
        }
        CurrencyConversion cc = new CurrencyConversion();
        cc.setId(ccDto.getId());
        cc.setConversionFactor(ccDto.getConversionFactor());
        cc.setCurrencySource(CurrencyDto.toEntity(ccDto.getCurrencySourceDto()));
        cc.setCurrencyDestination(CurrencyDto.toEntity(ccDto.getCurrencyDestinationDto()));
        return cc;
    }
}
