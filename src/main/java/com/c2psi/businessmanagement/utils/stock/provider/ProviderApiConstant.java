package com.c2psi.businessmanagement.utils.stock.provider;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ProviderApiConstant {
    String PROVIDER_ENDPOINT = APP_ROOT+"/provider";
    String CREATE_PROVIDER_ENDPOINT = APP_ROOT+"/provider/create";
    String UPDATE_PROVIDER_ENDPOINT = APP_ROOT+"/provider/update";
    String FIND_PROVIDER_BY_ID_ENDPOINT = APP_ROOT+"/provider/id/{providerId}";
    String FIND_PROVIDER_BY_NAME_ENDPOINT = APP_ROOT+"/provider/name/{providerName}/{posId}";
    String FIND_ALL_PROVIDER_OF_POS_ENDPOINT = APP_ROOT+"/provider/all/{posId}";
    String FIND_PAGE_PROVIDER_OF_POS_ENDPOINT = APP_ROOT+"/provider/page/{posId}/{pagenum}/{pagesize}";
    String DELETE_PROVIDER_BY_ID_ENDPOINT = APP_ROOT+"/provider/delete/id/{providerId}";
    String CREATE_PROVIDER_CASH_OPERATION_ENDPOINT = APP_ROOT+"/provider/cash/operation/create";
    String CREATE_PROVIDER_CASH_ACCOUNT_ENDPOINT = APP_ROOT+"/provider/cash/create";
    String FIND_PROVIDER_CASH_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/provider/cash/id/{pcaId}";
    String DELETE_PROVIDER_CASH_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/provider/cash/delete/{pcaId}";
    String UPDATE_PROVIDER_CASH_OPERATION_ENDPOINT = APP_ROOT+"/provider/cash/operation/update";
    String DELETE_PROVIDER_CASH_OPERATION_ENDPOINT = APP_ROOT+"/provider/cash/operation/delete/{procopId}";
    String FIND_PROVIDER_CASH_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/provider/cash/operation/id/{procopId}";
    String FIND_ALL_PROVIDER_CASH_OPERATION_ENDPOINT = APP_ROOT+"/pos/cash/operation/all/{procaId}";
    String FIND_PAGE_PROVIDER_CASH_OPERATION_ENDPOINT = APP_ROOT+"/provider/cash/operation/all/{procaId}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDER_CASH_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/provider/cash/operation/all/between/{procaId}/{from}/{to}";
    String FIND_PAGE_PROVIDER_CASH_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/provider/cash/operation/page/between/{procaId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDER_CASH_OPERATION_OFTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/provider/cash/operation/type/all/between/{procaId}/{opType}/{from}/{to}";
    String FIND_PAGE_PROVIDER_CASH_OPERATION_OFTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/provider/cash/operation/type/page/between/{procaId}/{opType}/{from}/{to}/{pagenum}/{pagesize}";

}
