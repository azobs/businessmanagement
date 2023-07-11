package com.c2psi.businessmanagement.utils.client.client;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ClientApiConstant {
    /**************************
     * End point de ClientApi
     */
    String CLIENT_ENDPOINT = APP_ROOT+"/client";
    String CREATE_CLIENT_ENDPOINT = APP_ROOT+"/client/create";
    String UPDATE_CLIENT_ENDPOINT = APP_ROOT+"/client/update";
    String FIND_CLIENT_BY_ID_ENDPOINT = APP_ROOT+"/client/id/{clientId}";
    String FIND_CLIENT_BY_NAME_IN_POS_ENDPOINT = APP_ROOT+"/client/name/{clientName}/{clientOthername}/{posId}";
    String FIND_CLIENT_BY_CNI_IN_POS_ENDPOINT = APP_ROOT+"/client/cni/{clientCni}/{posId}";
    String FIND_CLIENT_BY_EMAIL_IN_POS_ENDPOINT = APP_ROOT+"/client/email/{clientEmail}";
    String FIND_ALL_CLIENT_IN_POS_ENDPOINT = APP_ROOT+"/client/all/{posId}";
    String FIND_PAGE_CLIENT_IN_POS_ENDPOINT = APP_ROOT+"/client/page/{posId}/{pagenum}/{pagesize}";
    String DELETE_CLIENT_BY_ID_ENDPOINT = APP_ROOT+"/client/delete/id/{clientId}";
    String CREATE_CLIENT_CASH_OPERATION_ENDPOINT = APP_ROOT+"/client/cash/operation/create";
    String CREATE_CLIENT_CASH_ACCOUNT_ENDPOINT = APP_ROOT+"/client/cash/create";
    String FIND_CLIENT_CASH_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/client/cash/id/{ccaId}";
    String DELETE_CLIENT_CASH_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/client/cash/delete/{ccaId}";
    String UPDATE_CLIENT_CASH_OPERATION_ENDPOINT = APP_ROOT+"/client/cash/operation/update";
    String DELETE_CLIENT_CASH_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/client/cash/operation/delete/{ccaopId}";
    String FIND_CLIENT_CASH_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/client/cash/operation/id/{ccaopId}";
    String FIND_ALL_CLIENT_CASH_OPERATION_ENDPOINT = APP_ROOT+"/client/cash/operation/all/{ccaId}";
    String FIND_PAGE_CLIENT_CASH_OPERATION_ENDPOINT = APP_ROOT+"/client/cash/operation/page/{ccaId}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_CASH_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/client/cash/operation/all/between/{ccaId}/{from}/{to}";
    String FIND_PAGE_CLIENT_CASH_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/client/cash/operation/page/between/{ccaId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_CASH_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/client/cash/operation/type/all/between/{ccaId}/{opType}/{from}/{to}";
    String FIND_PAGE_CLIENT_CASH_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/client/cash/operation/type/page/between/{ccaId}/{opType}/{from}/{to}/{pagenum}/{pagesize}";

}
