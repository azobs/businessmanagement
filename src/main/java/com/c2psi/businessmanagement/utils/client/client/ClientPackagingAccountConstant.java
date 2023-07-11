package com.c2psi.businessmanagement.utils.client.client;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ClientPackagingAccountConstant {
    /*****************************************
     * End point de ClientPackagingAccountApi
     */

    String CLIENT_PACKAGING_ENDPOINT = APP_ROOT+"/clientpackagingaccount";
    String CREATE_CLIENT_PACKAGING_ENDPOINT = APP_ROOT+"/clientpackagingaccount/create";
    String FIND_CLIENT_PACKAGING_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/clientpackagingaccount/id/{cltpackaccId}";
    String FIND_CLIENT_PACKAGING_ACCOUNT_OF_PACKAGING_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/client/packaging/{clientId}/{packagingId}";
    String FIND_ALL_CLIENT_PACKAGING_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/clientpackagingaccount/all/{posId}";
    String FIND_PAGE_CLIENT_PACKAGING_ACCOUNT_IN_POS_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/page/{posId}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_PACKAGING_ACCOUNT_OF_CLIENT_ENDPOINT = APP_ROOT+"/clientpackagingaccount/all/{clientId}";
    String FIND_PAGE_CLIENT_PACKAGING_ACCOUNT_OF_CLIENT_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/page/{clientId}/{pagenum}/{pagesize}";
    String CREATE_CLIENT_PACKAGING_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientpackagingaccount/operation/create";
    String DELETE_CLIENT_PACKAGING_ACCOUNT_BY_ID = APP_ROOT+"/clientpackagingaccount/delete/{cltpackaccId}";
    String UPDATE_CLIENT_PACKAGING_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientpackagingaccount/operation/update";
    String DELETE_CLIENT_PACKAGING_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/delete/{cltpackopId}";
    String FIND_ALL_CLIENT_PACKAGING_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/all/{cltpackaccId}";
    String FIND_PAGE_CLIENT_PACKAGING_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/page/{cltpackaccId}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_PACKAGING_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/type/all/{cltpackaccId}/{optype}";
    String FIND_PAGE_CLIENT_PACKAGING_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/type/page/{cltpackaccId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_PACKAGING_ACCOUNT_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/all/between/{cltpackaccId}/{from}/{to}";
    String FIND_PAGE_CLIENT_PACKAGING_ACCOUNT_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/page/between/{cltpackaccId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_PACKAGING_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/type/all/between/{cltpackaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_CLIENT_PACKAGING_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientpackagingaccount/operation/type/page/between/{cltpackaccId}/{optype}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
}
