package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ProductFormatedApiConstant {
    String PRODUCTFORMATED_ENDPOINT = APP_ROOT+"/productformated";
    String CREATE_PRODUCTFORMATED_ENDPOINT = APP_ROOT+"/productformated/create";
    String UPDATE_PRODUCTFORMATED_ENDPOINT = APP_ROOT+"/productformated/update";
    String FIND_PRODUCTFORMATED_BY_ID_ENDPOINT = APP_ROOT+"/productformated/id/{pfId}";
    String FIND_PRODUCTFORMATED_BY_PRODUCT_AND_FORMAT_ENDPOINT = APP_ROOT+"/productformated/pf/{productId}/{formatId}";
    String FIND_ALL_PRODUCTFORMATED_IN_POS_ENDPOINT = APP_ROOT+"/productformated/all/{posId}";
    String FIND_PAGE_PRODUCTFORMATED_IN_POS_ENDPOINT = APP_ROOT+"/productformated/page/{posId}";
    String DELETE_PRODUCTFORMATED_BY_ID_ENDPOINT = APP_ROOT+"/productformated/delete/id/{pfId}";
}
