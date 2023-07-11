package com.c2psi.businessmanagement.utils.pos.pos;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface PosDamageAccountApiConstant {
    String POS_DAMAGE_ACCOUNT_ENDPOINT = APP_ROOT+"/posdamageaccount";
    String CREATE_POS_DAMAGE_ACCOUNT_ENDPOINT = APP_ROOT+"/pdamacc/create";
    String FIND_POS_DAMAGE_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/pdamacc/id/{pdamaccId}";
    String FIND_POS_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pdamacc/art/pos/{artId}/{posId}";
    String FIND_ALL_DAMAGE_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pdamacc/all/pos/{posId}";
    String FIND_PAGE_DAMAGE_ACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/pdamacc/page/pos/{posId}/{pagenum}/{pagesize}";
    String FIND_ALL_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pdamacc/all/art/{artId}";
    String FIND_PAGE_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/pdamacc/all/art/{artId}/{pagenum}/{pagesize}";
    String DELETE_POS_DAMAGE_ACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/pdamacc/delete/id/{pdamaccId}";
    String CREATE_POS_DAMAGE_OPERATION_ENDPOINT = APP_ROOT+"/pdamacc/operation/create";
    String UPDATE_POS_DAMAGE_OPERATION_ENDPOINT = APP_ROOT+"/pdamacc/operation/update";
    String DELETE_POS_DAMAGE_OPERATION_BY_ID_ENDPOINT = APP_ROOT+"/pdamacc/operation/delete/id/{pdamopId}";
    String FIND_ALL_POS_DAMAGE_OPERATION_ENDPOINT = APP_ROOT+"/pdamacc/operation/all/{pdamopId}";
    String FIND_ALL_POS_DAMAGE_OPERATION_OF_TYPE_ENDPOINT = APP_ROOT+"/pdamacc/operation/type/{pdamopId}/{optype}";
    String FIND_PAGE_POS_DAMAGE_OPERATION_ENDPOINT = APP_ROOT+"/pdamacc/operation/page/{pdamopId}/{pagenum}/{pagesize}";
    String FIND_PAGE_POS_DAMAGE_OPERATION_OF_TYPE_ENDPOINT =
            APP_ROOT+"/pdamacc/operation/page/type/{pdamopId}/{optype}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_DAMAGE_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/pdamacc/operation/all/between/{pdamopId}/{from}/{to}";
    String FIND_PAGE_POS_DAMAGE_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/pdamacc/operation/page/between/{pdamopId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_POS_DAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/pdamacc/operation/type/between/{pdamopId}/{optype}/{from}/{to}";
    String FIND_PAGE_POS_DAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/pdamacc/operation/type/between/{pdamopId}/{optype}/{from}/{to}/{pagenum}/{pagesize}";
}
