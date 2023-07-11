package com.c2psi.businessmanagement.utils.pos.pos;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface PosCapsuleAccountApiConstant {
    String POS_CAPSULE_ACCOUNT_ENDPOINT = APP_ROOT+"/poscapsuleaccount";
    String CREATE_POS_CAPSULE_ACCOUNT_ENDPOINT = APP_ROOT+"/pcapacc/create";
    String FIND_POS_CAPSULE_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/pcapacc/id/{pcapaccId}";
    String FIND_POS_CAPSULE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pcapacc/art/pos/{artId}/{posId}";
    String FIND_ALL_CAPSULE_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pcapacc/all/pos/{posId}";
    String FIND_PAGE_CAPSULE_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pcapacc/page/pos/{posId}/{pagenum}/{pagesize}";
    String FIND_ALL_CAPSULE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pcapacc/all/art/{artId}";
    String FIND_PAGE_CAPSULE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pcapacc/all/art/{artId}/{pagenum}/{pagesize}";
    String DELETE_POS_CAPSULE_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/pcapacc/delete/id/{pcapaccId}";
    String CREATE_POS_CAPSULE_OPERATION_ENDPOINT = APP_ROOT+"/pcapacc/operation/create";
    String UPDATE_POS_CAPSULE_OPERATION_ENDPOINT = APP_ROOT+"/pcapacc/operation/create";
    String DELETE_POS_CAPSULE_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/pcapacc/operation/delete/id/{pcapopId}";
    String FIND_ALL_POS_CAPSULE_OPERATION_ENDPOINT = APP_ROOT+"/pcapacc/operation/all/{pcapopId}";
    String FIND_ALL_POS_CAPSULE_OPERATION_OF_TYPE_ENDPOINT = APP_ROOT+"/pcapacc/operation/type/{pcapopId}/{optype}";
    String FIND_PAGE_POS_CAPSULE_OPERATION_ENDPOINT = APP_ROOT+"/pcapacc/operation/page/{pcapopId}/{pagenum}/{pagesize}";
    String FIND_PAGE_POS_CAPSULE_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/pcapacc/operation/page/type/{pcapopId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_CAPSULE_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/pcapacc/operation/all/between/{pcapopId}/{from}/{to}";
    String FIND_PAGE_POS_CAPSULE_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/pcapacc/operation/page/between/{pcapopId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_CAPSULE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/pcapacc/operation/type/between/{pcapopId}/{optype}/{from}/{to}";
    String FIND_PAGE_POS_CAPSULE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/pcapacc/operation/type/between/{pcapopId}/{optype}/{from}/{to}/{pagenum}/{pagesize}";
}
