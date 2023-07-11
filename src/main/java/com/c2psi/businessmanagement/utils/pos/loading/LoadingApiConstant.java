package com.c2psi.businessmanagement.utils.pos.loading;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface LoadingApiConstant {
    String LOADING_ENDPOINT = APP_ROOT+"/loading";
    String CREATE_LOADING_ENDPOINT = APP_ROOT+"/loading/create";
    String UPDATE_LOADING_ENDPOINT = APP_ROOT+"/loading/update";
    String FIND_LOADIND_BY_ID_ENDPOINT = APP_ROOT+"/loading/id/{loadingId}";
    String FIND_LOADING_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/loading/pos/code/{loadingCode}/{posId}";
    String FIND_ALL_LOADING_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/loading/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_LOADING_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/loading/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_LOADING_OF_USERBMMANAGER_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/loading/pos/userbm/manager/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_LOADING_OF_USERBMMANAGER_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/loading/pos/userbm/manager/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_LOADING_OF_USERBMSALER_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/loading/pos/userbm/saler/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_LOADING_OF_USERBMSALER_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/loading/pos/userbm/saler/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_LOADING_OF_USERBMMANAGER_AND_SALER_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/loading/pos/userbm/manager/saler/all/{posId}/{userbmId_m}/{userbmId_s}/" +
                    "{from}/{to}";
    String FIND_PAGE_LOADING_OF_USERBMMANAGER_AND_SALER_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/loading/pos/userbm/manager/saler/page/{posId}/{userbmId_m}/{userbmId_s}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";
    String DELETE_LOADING_BY_ID_ENDPOINT = APP_ROOT+"/loading/delete/id/{loadingId}";
    String CREATE_LOADINGDETAILS_ENDPOINT = APP_ROOT+"/loading/details/create";
    String UPDATE_LOADINGDETAILS_ENDPOINT = APP_ROOT+"/loading/details/update";
    String FIND_LOADINGDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/loading/details/id/{ldId}";
    String FIND_LOADINGDETAILS_OF_ARTICLE_IN_LOADING_ENDPOINT = APP_ROOT+"/loading/details/article/{artId}/{loadingId}";
    String FIND_ALL_LOADINGDETAILS_IN_LOADING_ENDPOINT = APP_ROOT+"/loading/details/all/{loadingId}";
    String FIND_PAGE_LOADINGDETAILS_IN_LOADING_ENDPOINT = APP_ROOT+"/loading/details/page/{loadingId}/{pagenum}/{pagesize}";
    String DELETE_LOADINGDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/loading/details/delete/id/{ldId}";
    String CREATE_PACKINGDETAILS_ENDPOINT = APP_ROOT+"/loading/packing/details/create";
    String UPDATE_PACKINGDETAILS_ENDPOINT = APP_ROOT+"/loading/packing/details/update";
    String FIND_PACKINGDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/loading/packing/details/id/{pdId}";
    String FIND_PACKINGDETAILS_OF_ARTICLE_IN_LOADING_ENDPOINT = APP_ROOT+"/loading/packing/details/packaging/" +
            "{packagingId}/{loadingId}";
    String FIND_ALL_PACKINGDETAILS_IN_LOADING_ENDPOINT = APP_ROOT+"/loading/packing/details/all/{loadingId}";
    String FIND_PAGE_PACKINGDETAILS_IN_LOADING_ENDPOINT = APP_ROOT+"/loading/packing/details/page/{loadingId}/" +
            "{pagenum}/{pagesize}";
    String DELETE_PACKINGDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/loading/packing/details/delete/id/{pdId}";

}
