package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface PackagingApiConstant {
    String PACKAGING_ENDPOINT = APP_ROOT+"/packaging";
    String CREATE_PACKAGING_ENDPOINT = APP_ROOT+"/packaging/create";
    String UPDATE_PACKAGING_ENDPOINT = APP_ROOT+"/packaging/update";
    String FIND_PACKAGING_BY_ID_ENDPOINT = APP_ROOT+"/packaging/id/{packagingId}";
    String FIND_PACKAGING_BY_ATTRIBUTES_ENDPOINT = APP_ROOT+"/packaging/attributes/{packLabel}/{packFirstColor}/" +
            "{providerId}/{posId}";
    String FIND_ALL_PACKAGING_OF_POS_ENDPOINT = APP_ROOT+"/packaging/all/{posId}";
    String FIND_PAGE_PACKAGING_OF_POS_ENDPOINT = APP_ROOT+"/packaging/page/{posId}";
    String FIND_ALL_PACKAGING_OF_PROVIDER_ENDPOINT = APP_ROOT+"/packaging/provider/all/{providerId}/{posId}";
    String FIND_PAGE_PACKAGING_OF_PROVIDER_ENDPOINT = APP_ROOT+"/packaging/provider/page/{providerId}/{posId}/" +
            "{pagenum}/{pagesize}";
    String CONVERT_CASH_TO_PACKAGING_ENDPOINT = APP_ROOT+"/packaging/consigne/{packagingId}/{amountToConvert}";
    String CONVERT_PACKAGING_TO_CASH_ENDPOINT = APP_ROOT+"/packaging/deconsigne/{packagingId}/{numberToConvert}";
    String DELETE_PACKAGING_BY_ID_ENDPOINT = APP_ROOT+"/format/delete/id/{packagingId}";
}
