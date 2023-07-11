package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface CapsuleArrivalApiConstant {
    String CAPSULEARRIVAL_ENDPOINT = APP_ROOT+"/capsuleArrival";
    String CREATE_CAPSULEARRIVAL_ENDPOINT = APP_ROOT+"/capsuleArrival/create";
    String UPDATE_CAPSULEARRIVAL_ENDPOINT = APP_ROOT+"/capsuleArrival/update";
    String DELETE_CAPSULEARRIVAL_BY_ID_ENDPOINT = APP_ROOT+"/capsuleArrival/delete/id/{capsaId}";
    String FIND_CAPSULEARRIVAL_BY_ID_ENDPOINT = APP_ROOT+"/capsuleArrival/id/{capsaId}";
    String FIND_CAPSULEARRIVAL_OF_ARTICLE_ENDPOINT = APP_ROOT+"/capsuleArrival/article/{artId}/{sicapsId}";
    String FIND_ALL_CAPSULEARRIVAL_IN_ENDPOINT = APP_ROOT+"/capsuleArrival/all/{sicapsId}";
    String FIND_PAGE_CAPSULEARRIVAL_IN_ENDPOINT = APP_ROOT+"/capsuleArrival/all/{sicapsId}/{pagenum}/{pagesize}";
    String FIND_ALL_CAPSULEARRIVAL_IN_SICAPS_BETWEEN_ENDPOINT = APP_ROOT+"/capsuleArrival/all/between/{sicapsId}/{from}/{to}";
    String FIND_PAGE_CAPSULEARRIVAL_IN_SICAPS_BETWEEN_ENDPOINT = APP_ROOT+"/capsuleArrival/page/between/{sicapsId}/{from}/{to}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_CAPSULEARRIVAL_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/capsuleArrival/all/between/{posId}/{from}/{to}";
    String FIND_PAGE_CAPSULEARRIVAL_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/capsuleArrival/page/between/{posId}/{from}/{to}/" +
            "{pagenum}/{pagesize}";
}
