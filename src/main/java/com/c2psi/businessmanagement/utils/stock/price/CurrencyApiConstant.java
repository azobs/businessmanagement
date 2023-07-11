package com.c2psi.businessmanagement.utils.stock.price;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface CurrencyApiConstant {
    String CURRENCY_ENDPOINT = APP_ROOT+"/currency";
    String CREATE_CURRENCY_ENDPOINT = APP_ROOT+"/currency/create";
    String UPDATE_CURRENCY_ENDPOINT = APP_ROOT+"/currency/update";
    String FIND_CURRENCY_BY_ID_ENDPOINT = APP_ROOT+"/currency/{currencyId}";
    String FIND_CURRENCY_BY_FULLNAME_ENDPOINT = APP_ROOT+"/currency/fullname/{currencyName}/{currencyShortname}";
    String FIND_ALL_CURRENCY_ENDPOINT = APP_ROOT+"/currency/all";
    String FIND_PAGE_CURRENCY_ENDPOINT = APP_ROOT+"/currency/page/{pagenum}/{pagesize}";
    String DELETE_CURRENCY_BY_ID_ENDPOINT = APP_ROOT+"/currency/delete/id/{currencyId}";
    String CREATE_CURRENCYCONVERSION_ENDPOINT = APP_ROOT+"/currencyconversion/create";
    String UPDATE_CURRENCYCONVERSION_ENDPOINT = APP_ROOT+"/currencyconversion/update";
    String CONVERTTO_CURRENCY_ENDPOINT = APP_ROOT+"/currencyconversion/convertTo/{amount}/{from}/{to}";
    String FIND_CURRENCYCONVERSION_BY_ID_ENDPOINT = APP_ROOT+"/currencyconversion/id/{currconvId}";
    String FIND_CURRENCY_CONVERSIONRULE_BETWEEN_ENDPOINT = APP_ROOT+"/currencyconversion/link/{from}/{to}";
    String FIND_ALL_CONVERTIBLE_CURRENCY_BETWEEN_ENDPOINT = APP_ROOT+"/currencyconversion/rules/{from}";
    String DELETE_CURRENCYCONVERSION_BY_ID_ENDPOINT = APP_ROOT+"/currencyconversion/delete/id/{currconvId}";
    String DELETE_CURRENCYCONVERSION_BY_CURRENCYLINK_ENDPOINT = APP_ROOT+"/currencyconversion/delete/link/{from}/{to}";
}
