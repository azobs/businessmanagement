package com.c2psi.businessmanagement.utils.client.command;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface BackInApiConstant {
    /**************************
     * End point de BackInApi
     */
    String BACKIN_ENDPOINT = APP_ROOT+"/backin";
    String CREATE_BACKIN_ENDPOINT = APP_ROOT+"/backin/create";
    String UPDATE_BACKIN_ENDPOINT = APP_ROOT+"/backin/update";
    String FIND_BACKIN_BY_ID_ENDPOINT = APP_ROOT+"/backin/id/{backinId}";
    String FIND_BACKIN_OF_COMMAND_ENDPOINT = APP_ROOT+"/backin/command/{cmdId}";
    String FIND_ALL_BACKIN_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/backin/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_BACKIN_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/backin/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String DELETE_BACKIN_BY_ID_ENDPOINT = APP_ROOT+"/backin/delete/id/{backinId}";
    String CREATE_BACKINDETAILS_ENDPOINT = APP_ROOT+"/backin/details/create";
    String UPDATE_BACKINDETAILS_ENDPOINT = APP_ROOT+"/backin/details/update";
    String FIND_BACKINDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/backin/details/id/{backInDetailsId}";
    String FIND_BACKINDETAILS_OF_ARTICLE_IN_BACKIN_ENDPOINT = APP_ROOT+"/backin/details/article/{artId}/{backinId}";
    String FIND_ALL_BACKINDETAILS_OF_BACKIN_ENDPOINT = APP_ROOT+"/backin/details/all/{backInId}";
    String FIND_PAGE_BACKINDETAILS_OF_BACKIN_ENDPOINT = APP_ROOT+"/backin/details/page/{backInId}/{pagenum}/{pagesize}";
    String DELETE_BACKINDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/backin/details/delete/id/{backInDetailsId}";
}
