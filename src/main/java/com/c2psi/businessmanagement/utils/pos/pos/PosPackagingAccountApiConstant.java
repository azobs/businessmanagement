package com.c2psi.businessmanagement.utils.pos.pos;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface PosPackagingAccountApiConstant {
    String POS_PACKAGING_ACCOUNT_ENDPOINT = APP_ROOT+"/pospackagingaccount";
    String CREATE_POS_PACKAGING_ACCOUNT_ENDPOINT = APP_ROOT+"/pospackagingaccount/create";
    String FIND_POS_PACKAGING_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/pospackagingaccount/id/{pospackaccId}";
    String FIND_POS_PACKAGING_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pospackagingaccount/packaging/{packagingId}/{posId}";
    String FIND_ALL_PACKAGING_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pospackagingaccount/all/{posId}";
    String FIND_PAGE_PACKAGING_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pospackagingaccount/page/{posId}/{pagenum}/{pagesize}";
    String DELETE_POS_PACKAGING_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/pospackagingaccount/delete/{pospackId}";
    String CREATE_POS_PACKAGING_OPERATION_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/create";
    String UPDATE_POS_PACKAGING_OPERATION_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/update";
    String DELETE_POS_PACKAGING_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/delete/{pospackopId}";
    String FIND_ALL_POS_PACKAGING_OPERATION_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/all/{pospackaccId}";
    String FIND_PAGE_POS_PACKAGING_OPERATION_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/all/{pospackaccId}";
    String FIND_ALL_POS_PACKAGING_OPERATION_OF_TYPE_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/type/all/" +
            "{pospackaccId}/{opType}";
    String FIND_PAGE_POS_PACKAGING_OPERATION_OF_TYPE_ENDPOINT = APP_ROOT+"/pospackagingaccount/operation/type/page/" +
            "{pospackaccId}/{opType}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_PACKAGING_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/pospackagingaccount/operation/all/between/{pospackaccId}/{from}/{to}";
    String FIND_PAGE_POS_PACKAGING_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/pospackagingaccount/operation/all/between/{pospackaccId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_POS_PACKAGING_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/pospackagingaccount/operation/type/all/between/{pospackaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_POS_PACKAGING_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/pospackagingaccount/operation/type/page/between/{pospackaccId}/{optype}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";



}
