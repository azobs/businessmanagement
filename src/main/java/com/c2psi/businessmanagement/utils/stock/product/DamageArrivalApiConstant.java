package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface DamageArrivalApiConstant {
    String DAMAGEARRIVAL_ENDPOINT = APP_ROOT+"/damageArrival";
    String CREATE_DAMAGEARRIVAL_ENDPOINT = APP_ROOT+"/damageArrival/create";
    String UPDATE_DAMAGEARRIVAL_ENDPOINT = APP_ROOT+"/damageArrival/update";
    String DELETE_DAMAGEARRIVAL_BY_ID_ENDPOINT = APP_ROOT+"/damageArrival/delete/id/{damaId}";
    String FIND_DAMAGEARRIVAL_BY_ID_ENDPOINT = APP_ROOT+"/damageArrival/id/{damaId}";
    String FIND_DAMAGEARRIVAL_OF_ARTICLE_ENDPOINT = APP_ROOT+"/damageArrival/article/{artId}/{sidamId}";
    String FIND_ALL_DAMAGEERRIVAL_IN_SIDAM_ENDPOINT = APP_ROOT+"/damageArrival/all/{sidamId}";
    String FIND_PAGE_DAMAGEERRIVAL_IN_SIDAM_ENDPOINT = APP_ROOT+"/damageArrival/all/{sidamId}/{pagenum}/{pagesize}";
    String FIND_ALL_DAMAGEERRIVAL_IN_SIDAM_BETWEEN_ENDPOINT = APP_ROOT+"/damageArrival/all/between/{sidamId}/{from}/{to}";
    String FIND_PAGE_DAMAGEERRIVAL_IN_SIDAM_BETWEEN_ENDPOINT = APP_ROOT+"/damageArrival/page/between/{sidamId}/{from}/{to}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_DAMAGEERRIVAL_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/damageArrival/all/between/{posId}/{from}/{to}";
    String FIND_PAGE_DAMAGEERRIVAL_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/damageArrival/page/between/{posId}/{from}/{to}/" +
            "{pagenum}/{pagesize}";
}
