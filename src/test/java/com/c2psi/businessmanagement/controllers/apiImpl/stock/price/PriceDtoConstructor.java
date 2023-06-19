package com.c2psi.businessmanagement.controllers.apiImpl.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyConversionService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;

import java.math.BigDecimal;

public class PriceDtoConstructor {
    public static CurrencyDto getCurrencyDtoToSave(String prefix, int num){
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName(prefix+"_"+num)
                .currencyShortname(prefix)
                .build();
        return currencyDto;
    }

    public static CurrencyDto getCurrencyDtoSaved(String prefix, int num, CurrencyService currencyService){
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName(prefix+"_"+num)
                .currencyShortname(prefix)
                .build();

        return currencyService.saveCurrency(currencyDto);
    }

    public static CurrencyConversionDto getCurrencyConversionDtoToSave(BigDecimal convFactor, CurrencyDto from,
                                                                       CurrencyDto to){
        CurrencyConversionDto currconvDtoToSave = CurrencyConversionDto.builder()
                .currencySourceDto(from)
                .currencyDestinationDto(to)
                .conversionFactor(convFactor)
                .build();
        return currconvDtoToSave;
    }

    public static CurrencyConversionDto getCurrencyConversionDtoSaved(BigDecimal convFactor, CurrencyDto from,
                                                                      CurrencyDto to, CurrencyConversionService currconvService){
        CurrencyConversionDto currconvDtoToSave = CurrencyConversionDto.builder()
                .currencySourceDto(from)
                .currencyDestinationDto(to)
                .conversionFactor(convFactor)
                .build();
        CurrencyConversionDto currconvDtoSaved = currconvService.saveCurrencyConversion(currconvDtoToSave);
        return currconvDtoSaved;
    }
}
