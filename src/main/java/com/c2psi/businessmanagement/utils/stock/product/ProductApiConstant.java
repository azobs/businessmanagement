package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ProductApiConstant {
    String PRODUCT_ENDPOINT = APP_ROOT+"/product";
    String CREATE_PRODUCT_ENDPOINT = APP_ROOT+"/product/create";
    String UPDATE_PRODUCT_ENDPOINT = APP_ROOT+"/product/update";
    String FIND_PRODUCT_BY_ID_ENDPOINT = APP_ROOT+"/product/id/{prodId}";
    String FIND_PRODUCT_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/product/code/{prodCode}/{posId}";
    String FIND_ALL_PRODUCT_IN_POS = APP_ROOT+"/product/all/pos/{posId}";
    String FIND_ALL_PRODUCT_OF_CATEGORY = APP_ROOT+"/product/all/cat/{catId}";
    String FIND_PAGE_PRODUCT_IN_POS = APP_ROOT+"/product/page/pos/{posId}/{pagenum}/{pagesize}";
    String FIND_PAGE_PRODUCT_OF_CATEGORY = APP_ROOT+"/product/page/cat/{catId}/{pagenum}/{pagesize}";
    String FIND_PAGE_PRODUCT_CONTAINING = APP_ROOT+"/product/page/pos/{posId}/{sample}/{pagenum}/{pagesize}";
    String DELETE_PRODUCT_BY_ID = APP_ROOT+"/product/delete/id/{prodId}";
}
