package com.c2psi.businessmanagement.utils.client.client;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ClientDamageAccountConstant {
    /***************************************
     * End point de ClientDamageAccountApi
     */

    String CLIENT_DAMAGE_ENDPOINT = APP_ROOT+"/clientdamageaccount";
    String CREATE_CLIENT_DAMAGE_ENDPOINT = APP_ROOT+"/clientdamageaccount/create";
    String FIND_CLIENT_DAMAGE_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/clientdamageaccount/id/{cltdamaccId}";
    String FIND_CLIENT_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/clientdamageaccount/client/article/{clientId}/{artId}";
    String FIND_ALL_CLIENT_DAMAGE_ACCOUNT_ENDPOINT = APP_ROOT+"/clientdamageaccount/all/{clientId}";
    String FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_ENDPOINT = APP_ROOT+"/clientdamageaccount/page/{clientId}/{pagenum}/{pagesize}";
    String CREATE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientdamageaccount/operation/create";
    String UPDATE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientdamageaccount/operation/update";
    String DELETE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientdamageaccount/operation/delete/{cltdamopId}";
    String FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientdamageaccount/operation/all/{cltdamaccId}";
    String FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/page/{cltdamaccId}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/type/all/{cltdamaccId}/{optype}";
    String FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/type/page/{cltdamaccId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/all/between/{cltdamaccId}/{from}/{to}";
    String FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/page/between/{cltdamaccId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/type/all/between/{cltdamaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientdamageaccount/operation/type/page/between/{cltdamaccId}/{optype}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
}
