package com.c2psi.businessmanagement.utils.stock.provider;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ProviderCapsuleAccountApiConstant {
    String PROVIDERCAPSULEACCOUNT_ENDPOINT = APP_ROOT+"/procapsuleaccount";
    String CREATE_PROVIDERCAPSULEACCOUNT_ENDPOINT = APP_ROOT+"/procapsuleaccount/create";
    String FIND_PROVIDERCAPSULEACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/procapsuleaccount/id/{procapaccId}";
    String FIND_PROVIDERCAPSULEACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/procapsuleaccount/provider/article/" +
            "{providerId}/{artId}";
    String FIND_ALL_PROVIDERCAPSULEACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/procapsuleaccount/all/{providerId}";
    String FIND_PAGE_PROVIDERCAPSULEACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/procapsuleaccount/page/{providerId}/" +
            "{pagenum}/{pagesize}";
    String CREATE_PROVIDERCAPSULE_OPERATION_ENDPOINT = APP_ROOT+"/procapsuleaccount/operation/create";
    String UPDATE_PROVIDERCAPSULE_OPERATION_ENDPOINT = APP_ROOT+"/procapsuleaccount/operation/update";
    String DELETE_PROVIDERCAPSULE_OPERATION_BY_ID_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/delete/{procapopId}";
    String FIND_ALL_PROVIDERCAPSULE_OPERATION_IN_POS_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/all/{procapaccId}";
    String FIND_PAGE_PROVIDERCAPSULE_OPERATION_IN_POS_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/page/{procapaccId}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERCAPSULE_OPERATION_OF_TYPE_IN_POS_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/type/all/{procapaccId}/{optype}";
    String FIND_PAGE_PROVIDERCAPSULE_OPERATION_OF_TYPE_IN_POS_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/type/page/{procapaccId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERCAPSULE_OPERATION_BETWEEN_ENDPOINT = APP_ROOT+"/procapsuleaccount/operation/all/between/" +
            "{procapaccId}/{from}/{to}";
    String FIND_PAGE_PROVIDERCAPSULE_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/page/between/{procapaccId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERCAPSULE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/procapsuleaccount/operation/type/all/between/{procapaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_PROVIDERCAPSULE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT = APP_ROOT+"/procapsuleaccount/operation/type/" +
            "page/between/{procapaccId}/{optype}/{from}/{to}/{pagenum}/{pagesize}";
}
