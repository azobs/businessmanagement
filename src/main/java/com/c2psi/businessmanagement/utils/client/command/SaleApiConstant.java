package com.c2psi.businessmanagement.utils.client.command;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SaleApiConstant {
    /**************************
     * End point de SaleApi
     */

    String SALE_ENDPOINT = APP_ROOT+"/sale";
    String CREATE_SALE_ENDPOINT = APP_ROOT+"/sale/create";
    String UPDATE_SALE_ENDPOINT = APP_ROOT+"/sale/update";
    String FIND_SALE_BY_ID_ENDPOINT = APP_ROOT+"/sale/id/{saleId}";
    String FIND_SALE_IN_COMMAND_FOR_ARTICLE_ENDPOINT = APP_ROOT+"/sale/command/{cmdId}/{artId}";
    String DELETE_SALE_BY_ID_ENDPOINT = APP_ROOT+"/sale/delete/id/{saleId}";
    String FIND_ALL_SALE_IN_COMMAND_ENDPOINT = APP_ROOT+"/sale/command/all/{cmdId}";
    String FIND_PAGE_SALE_IN_COMMAND_ENDPOINT = APP_ROOT+"/sale/command/page/{cmdId}/{pagenum}/{pagesize}";
    String FIND_ALL_SALE_OF_ARTICLE_BETWEEN_ENDPOINT = APP_ROOT+"/sale/article/between/all/{artId}/{from}/{to}";
    String FIND_PAGE_SALE_OF_ARTICLE_BETWEEN_ENDPOINT = APP_ROOT+"/sale/article/between/page/{artId}/{from}/{to}/{pagenum}/{pagesize}";

}
