package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface CashArrivalApiConstant {
    String CASHARRIVAL_ENDPOINT = APP_ROOT+"/cashArrival";
    String CREATE_CASHARRIVAL_ENDPOINT = APP_ROOT+"/cashArrival/create";
    String UPDATE_CASHARRIVAL_ENDPOINT = APP_ROOT+"/cashArrival/update";
    String DELETE_CASHARRIVAL_ENDPOINT = APP_ROOT+"/cashArrival/delete/id/{cashaId}";
    String FIND_CASHARRIVAL_BY_ID_ENDPOINT = APP_ROOT+"/cashArrival/id/{cashaId}";
    String FIND_CASHARRIVAL_OF_ARTICLE_IN_ENDPOINT = APP_ROOT+"/cashArrival/article/{artId}/{sicashId}";
    String FIND_ALL_CASHARRIVAL_IN_ENDPOINT = APP_ROOT+"/cashArrival/all/{sicashId}";
    String FIND_PAGE_CASHARRIVAL_IN_ENDPOINT = APP_ROOT+"/cashArrival/all/{sicashId}/{pagenum}/{pagesize}";
    String FIND_ALL_CASHARRIVAL_OF_TYPE_ENDPOINT = APP_ROOT+"/cashArrival/all/type/{arrivalType}/{sicashId}";
    String FIND_PAGE_CASHARRIVAL_OF_TYPE_ENDPOINT = APP_ROOT+"/cashArrival/page/type/{arrivalType}/{sicashId}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_CASHARRIVAL_IN_SICASH_BETWEEN_ENDPOINT = APP_ROOT+"/cashArrival/all/between/{sicashId}/{from}/{to}";
    String FIND_PAGE_CASHARRIVAL_IN_SICASH_BETWEEN_ENDPOINT = APP_ROOT+"/cashArrival/page/between/{sicashId}/{from}/{to}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_CASHARRIVAL_OF_TYPE_BETWEEN_ENDPOINT = APP_ROOT+"/cashArrival/all/type/between/{arrivalType}/" +
            "{sicashId}/{from}/{to}";
    String FIND_PAGE_CASHARRIVAL_OF_TYPE_BETWEEN_ENDPOINT = APP_ROOT+"/cashArrival/page/type/between/{arrivalType}/" +
            "{sicashId}/{from}/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_CASHARRIVAL_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/cashArrival/all/between/{posId}/{from}/{to}";
    String FIND_PAGE_CASHARRIVAL_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/cashArrival/page/between/{posId}/{from}/{to}/" +
            "{pagenum}/{pagesize}";
}
