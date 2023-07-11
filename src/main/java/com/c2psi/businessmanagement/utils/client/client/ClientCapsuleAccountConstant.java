package com.c2psi.businessmanagement.utils.client.client;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ClientCapsuleAccountConstant {
    /**************************
     * End point de ClientCapsuleAccountApi
     */
    String CLIENT_CAPSULE_ENDPOINT = APP_ROOT+"/clientcapsuleaccount";
    String CREATE_CLIENT_CAPSULE_ENDPOINT = APP_ROOT+"/clientcapsuleaccount/create";
    String FIND_CLIENT_CAPSULE_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/clientcapsuleaccount/id/{cltcapaccId}";
    String FIND_CLIENT_CAPSULE_ACCOUNT_OF_ARTICLE_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/client/article/{clientId}/{artId}";
    String FIND_ALL_CLIENT_CAPSULE_ACCOUNT_ENDPOINT = APP_ROOT+"/clientcapsuleaccount/all/{clientId}";
    String FIND_PAGE_CLIENT_CAPSULE_ACCOUNT_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/page/{clientId}/{pagenum}/{pagesize}";
    String CREATE_CLIENT_CAPSULE_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientcapsuleaccount/operation/create";
    String UPDATE_CLIENT_CAPSULE_ACCOUNT_OPERATION_ENDPOINT = APP_ROOT+"/clientcapsuleaccount/operation/update";
    String DELETE_CLIENT_CAPSULE_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/delete/{cltcapopId}";
    String FIND_ALL_CLIENT_CAPSULE_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/all/{cltcapaccId}";
    String FIND_PAGE_CLIENT_CAPSULE_ACCOUNT_OPERATION_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/page/{cltcapaccId}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_CAPSULE_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/type/all/{cltcapaccId}/{optype}";
    String FIND_PAGE_CLIENT_CAPSULE_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/type/page/{cltcapaccId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_CAPSULE_ACCOUNT_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/all/between/{cltcapaccId}/{from}/{to}";
    String FIND_PAGE_CLIENT_CAPSULE_ACCOUNT_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/page/between/{cltcapaccId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_CAPSULE_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/type/all/between/{cltcapaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_CLIENT_CAPSULE_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/clientcapsuleaccount/operation/type/page/between/{cltcapaccId}/{optype}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
}
