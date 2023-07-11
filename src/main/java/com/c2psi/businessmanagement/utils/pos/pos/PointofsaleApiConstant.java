package com.c2psi.businessmanagement.utils.pos.pos;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface PointofsaleApiConstant {
    String POS_ENDPOINT = APP_ROOT+"/pos";
    String GET_POS_TURNOVER_ENDPOINT = APP_ROOT+"/pos/turnover/{posId}/{startDate}/{endDate}";
    String FIND_ALL_EMPLOYE_OF_POS = APP_ROOT+"/pos/employes/{posId}";
    String FIND_ALL_PROVIDER_OF_POS = APP_ROOT+"/pos/providers/{posId}";
    String GET_POS_TOTALCASH_ENDPOINT = APP_ROOT+"/pos/totalcash/{posId}";
    String GET_POS_NUMBER_OF_DAMAGE_ENDPOINT = APP_ROOT+"/pos/damage/{posId}";
    String GET_POS_NUMBER_OF_DAMAGE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pos/damage/{posId}/{artId}";
    String GET_POS_NUMBER_OF_CAPSULE_ENDPOINT = APP_ROOT+"/pos/cover/{posId}";
    String GET_POS_NUMBER_OF_CAPSULE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pos/cover/{posId}/{artId}";
    String GET_POS_NUMBER_OF_PACKAGING_ENDPOINT = APP_ROOT+"/pos/packaging/{posId}";
    String GET_POS_NUMBER_OF_PACKAGING_OF_PROVIDER_ENDPOINT = APP_ROOT+"/pos/packaging/{posId}/{providerId}";
    String CREATE_POS = APP_ROOT+"/pos/create";
    String UPDATE_POS = APP_ROOT+"/pos/update";
    String FIND_POS_BY_ID = APP_ROOT+"/pos/{posId}";
    String DELETE_POS_BY_ID = APP_ROOT+"/pos/delete/id/{posId}";
    String DELETE_POS_IN_ENTERPRISE_BY_NAME = APP_ROOT+"/pos/delete/name/{posName}/{entId}";
    String LIST_OF_CONVERTIBLE_CURRENCY = APP_ROOT+"/pos/currencies/{posId}";
    String GET_POS_DEFAULT_CURRENCY = APP_ROOT+"/pos/currency/{posId}";
    String CREATE_POS_CASH_OPERATION = APP_ROOT+"/pos/cash/operation/create";
    String DELETE_POS_CASH_ACCOUNT = APP_ROOT+"/pos/cash/delete/id/{pcaId}";
    String FIND_POS_CASH_ACCOUNT_BY_ID = APP_ROOT+"/pos/cash/id/{pcaId}";
    String UPDATE_POS_CASH_OPERATION =APP_ROOT+"/pos/cash/operation/update";
    String DELETE_POS_CASH_OPERATION_BY_ID = APP_ROOT+"/pos/cash/operation/delete/{pcopId}";
    String FIND_POS_CASH_OPERATION_BY_ID = APP_ROOT+"/pos/cash/operation/id/{pcopId}";
    String FIND_ALL_POS_CASH_OPERATION =
            APP_ROOT+"/pos/cash/operation/all/{pcopId}";
    String FIND_PAGE_POS_CASH_OPERATION =
            APP_ROOT+"/pos/cash/operation/all/{pcaId}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_CASH_OPERATION_BETWEEN =
            APP_ROOT+"/pos/cash/operation/all/between/{pcaId}/{from}/{to}";
    String FIND_PAGE_POS_CASH_OPERATION_BETWEEN =
            APP_ROOT+"/pos/cash/operation/page/between/{pcaId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_CASH_OPERATION_OF_TYPE_BETWEEN =
            APP_ROOT+"/pos/cash/operation/type/all/between/{pcaId}/{opType}/{from}/{to}";
    String FIND_PAGE_POS_CASH_OPERATION_OF_TYPE_BETWEEN =
            APP_ROOT+"/pos/cash/operation/type/page/between/{pcaId}/{opType}/{from}/{to}/{pagenum}/{pagesize}";

}
