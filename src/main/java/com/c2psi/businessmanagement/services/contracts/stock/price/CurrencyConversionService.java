package com.c2psi.businessmanagement.services.contracts.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyConversionService {
    CurrencyConversionDto saveCurrencyConversion(CurrencyConversionDto ccDto);
    CurrencyConversionDto updateCurrencyConversion(CurrencyConversionDto ccDto);
    /*****************************************************************************
     * Helps to convert the amount from the source currency to the destination
     * currency
     * @param amountToConvert
     * @param currencySourceId
     * @param currencyDestinationId
     * @return
     */
    BigDecimal convertTo (BigDecimal amountToConvert, Long currencySourceId,
                          Long currencyDestinationId);

    Boolean isCurrencyConversionUnique(Long currencySourceId, Long currencyDestinationId);

    CurrencyConversionDto findCurrencyConversionById(Long curconvId);

    CurrencyConversionDto findConversionRuleBetween(Long currencySourceId,
                                                    Long currencyDestinationId);

    List<CurrencyDto> listofConvertibleCurrencyWith(Long currencySourceId);
    Boolean isCurrencyConversionDeleteable(Long curconvId);
    Boolean deleteCurrencyConversionById(Long id);
    Boolean deleteCurrencyConversionByCurrencyLink(Long currencySourceId, Long currencyDestinationId);
}
