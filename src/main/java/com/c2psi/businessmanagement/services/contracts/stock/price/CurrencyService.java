package com.c2psi.businessmanagement.services.contracts.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CurrencyService {
    CurrencyDto saveCurrency(CurrencyDto currencyDto);
    CurrencyDto updateCurrency(CurrencyDto currencyDto);
    List<CurrencyDto> listofExistingCurrency();
    Page<CurrencyDto> pageofExistingCurrency(int pagenum, int pagesize);
    Boolean isCurrencyUnique(String currencyName, String currencyShortName);
    CurrencyDto findCurrencyByFullname(String currencyName, String currencyShortName);
    CurrencyDto findCurrencyById(Long currencyId);
    Boolean deleteCurrencyById(Long id);
    Boolean deleteCurrencyByFullname(String currencyName, String currencyShortName);
}
