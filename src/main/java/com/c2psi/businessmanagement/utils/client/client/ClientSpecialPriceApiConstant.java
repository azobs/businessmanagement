package com.c2psi.businessmanagement.utils.client.client;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ClientSpecialPriceApiConstant {
    /*****************************************
     * End point de ClientSpecialpriceApi
     */
    String CLIENT_SPECIAL_PRICE_ENDPOINT = APP_ROOT+"/clientspecialprice";
    String CREATE_CLIENT_SPECIAL_PRICE = APP_ROOT + "/clientspecialprice/create";
    String UPDATE_CLIENT_SPECIAL_PRICE = APP_ROOT + "/clientspecialprice/update";
    String FIND_CLIENT_SPECIAL_PRICE_BY_ID = APP_ROOT + "/clientspecialprice/id/{cltspepriceId}";
    String FIND_CLIENT_SPECIAL_PRICE_OF_ARTICLE = APP_ROOT + "/clientspecialprice/of/{articleId}/{clientId}";
    String FIND_ALL_SPECIAL_PRICE_OF_CLIENT = APP_ROOT + "/clientspecialprice/client/all/{clientId}";
    String FIND_PAGE_SPECIAL_PRICE_OF_CLIENT = APP_ROOT + "/clientspecialprice/client/page/{clientId}/{pagenum}/{pagesize}";
    String FIND_ALL_CLIENT_WITH_SPECIAL_PRICE = APP_ROOT + "/clientspecialprice/client/article/all/{articleId}";
    String FIND_PAGE_CLIENT_WITH_SPECIAL_PRICE =
            APP_ROOT + "/clientspecialprice/client/article/page/{articleId}/{pagenum}/{pagesize}";
    String GET_EFFECTIVE_SPECIAL_PRICE_TO_APPLIED =
            APP_ROOT + "/clientspecialprice/client/article/price/{clientId}/{articleId}/{qteCommand}";
    String GET_COMMON_EFFECTIVE_SPECIAL_PRICE_TO_APPLIED =
            APP_ROOT + "/clientspecialprice/article/price/{articleId}/{qteCommand}";
    String DELETE_CLIENT_SPECIAL_PRICE_BY_ID = APP_ROOT + "/clientspecialprice/delete/{cltspepriceId}";
}
