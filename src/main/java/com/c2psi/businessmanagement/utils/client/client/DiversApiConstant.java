package com.c2psi.businessmanagement.utils.client.client;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface DiversApiConstant {
    /**************************
     * End point de DiversApi
     */
    String DIVERS_ENDPOINT = APP_ROOT + "/divers";
    String CREATE_DIVERS_ENDPOINT =  APP_ROOT + "/divers/create";
    String UPDATE_DIVERS_ENDPOINT = APP_ROOT + "/divers/update";
    String FIND_DIVERS_BY_ID_ENDPOINT = APP_ROOT + "/divers/id/{diversId}";
    String FIND_DIVERS_BY_EMAIL_ENDPOINT = APP_ROOT + "/divers/email/{diversEmail}";
    String FIND_ALL_DIVERS_OF_POS_ENDPOINT = APP_ROOT + "/divers/all/{posId}";
    String FIND_PAGE_DIVERS_OF_POS_ENDPOINT = APP_ROOT + "/divers/page/{posId}/{pagenum}/{pagesize}";
    String FIND_ALL_DIVERS_BY_NAME_OF_POS_ENDPOINT = APP_ROOT + "/divers/name/all/{posId}";
    String FIND_PAGE_DIVERS_BY_NAME_OF_POS_ENDPOINT = APP_ROOT + "/divers/name/page/{posId}/{pagenum}/{pagesize}";
    String DELETE_DIVERS_BY_ID_ENDPOINT = APP_ROOT+"/divers/delete/id/{diversId}";
    String CREATE_DIVERS_CASH_OPERATION_ENDPOINT = APP_ROOT + "/divers/cash/operation/create";
    String CREATE_DIVERS_CASH_ACCOUNT_ENDPOINT = APP_ROOT + "/divers/cash/create";
    String DELETE_DIVERS_CASH_ACCOUNT_ENDPOINT = APP_ROOT + "/divers/cash/delete/id/{diversId}";
    String FIND_DIVERS_CASH_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/divers/cash/id/{dcaId}";
    String FIND_DIVERS_CASH_ACCOUNT_OF_POS_ENDPOINT = APP_ROOT+"/divers/cash/pos/{posId}";
    String UPDATE_DIVERS_CASH_OPERATION_ENDPOINT = APP_ROOT+"/divers/cash/operation/update";
    String DELETE_DIVERS_CASH_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/divers/cash/operation/delete/{dcaopId}";
    String FIND_DIVERS_CASH_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/divers/cash/operation/id/{dcaopId}";
    String FIND_ALL_DIVERS_CASH_ENDPOINT = APP_ROOT+"/divers/cash/operation/all/{dcaId}";
    String FIND_PAGE_DIVERS_CASH_ENDPOINT = APP_ROOT+"/divers/cash/operation/page/{dcaId}/{pagenum}/{pagesize}";
    String FIND_ALL_DIVERS_CASH_OPERATION_ENDPOINT = APP_ROOT+"/divers/cash/operation/all/{dcaId}";
    String FIND_PAGE_DIVERS_CASH_OPERATION_ENDPOINT = APP_ROOT+"/divers/cash/operation/page/{dcaId}/{pagenum}/{pagesize}";
    String FIND_ALL_DIVERS_CASH_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/divers/cash/operation/type/all/between/{ccaId}/{opType}/{from}/{to}";
    String FIND_PAGE_DIVERS_CASH_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/divers/cash/operation/type/all/between/{ccaId}/{opType}/{from}/{to}/{pagenum}/{pagesize}";
}
