package com.c2psi.businessmanagement.utils.stock.provider;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ProviderDamageAccountApiConstant {
    String PROVIDERDAMAGEACCOUNT_ENDPOINT = APP_ROOT+"/prodamageaccount";
    String CREATE_PROVIDERDAMAGEACCOUNT_ENDPOINT = APP_ROOT+"/prodamageaccount/create";
    String FIND_PROVIDERDAMAGEACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/prodamageaccount/id/{prodamaccId}";
    String FIND_PROVIDERDAMAGEACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/prodamageaccount/provider/article/{providerId}/" +
            "{artId}";
    String FIND_ALL_PROVIDERDAMAGEACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/prodamageaccount/all/{providerId}";
    String FIND_PAGE_PROVIDERDAMAGEACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/prodamageaccount/page/{providerId}/" +
            "{pagenum}/{pagesize}";
    String CREATE_PROVIDERDAMAGE_OPERATION_ENDPOINT = APP_ROOT+"/prodamageaccount/operation/create";
    String UPDATE_PROVIDERDAMAGE_OPERATION_ENDPOINT = APP_ROOT+"/prodamageaccount/operation/update";
    String DELETE_PROVIDERDAMAGE_OPERATION_BY_ID_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/delete/{prodamopId}";
    String FIND_ALL_PROVIDERDAMAGE_OPERATION_IN_POS_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/all/{prodamaccId}";
    String FIND_PAGE_PROVIDERDAMAGE_OPERATION_IN_POS_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/page/{prodamaccId}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERDAMAGE_OPERATION_OF_TYPE_IN_POS_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/type/all/{prodamaccId}/{optype}";
    String FIND_PAGE_PROVIDERDAMAGE_OPERATION_OF_TYPE_IN_POS_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/type/page/{prodamaccId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERDAMAGE_OPERATION_BETWEEN_ENDPOINT = APP_ROOT+"/prodamageaccount/operation/all/between/" +
            "{prodamaccId}/{from}/{to}";
    String FIND_PAGE_PROVIDERDAMAGE_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/page/between/{prodamaccId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERDAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/prodamageaccount/operation/type/all/between/{prodamaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_PROVIDERDAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT = APP_ROOT+"/prodamageaccount/operation/type/" +
            "page/between/{prodamaccId}/{optype}/{from}/{to}/{pagenum}/{pagesize}";
}
