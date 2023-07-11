package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface CategoryApiConstant {
    String CATEGORY_ENDPOINT = APP_ROOT+"/category";
    String CREATE_CATEGORY_ENDPOINT = APP_ROOT+"/category/create";
    String UPDATE_CATEGORY_ENDPOINT = APP_ROOT+"/category/update";
    String FIND_CATEGORY_BY_ID_ENDPOINT = APP_ROOT+"/category/{catId}";
    String FIND_ASCENDANT_CATEGORY_OF_ENDPOINT = APP_ROOT+"/category/parents/{catId}";
    String FIND_CATEGORY_PARENT_OF_ENDPOINT = APP_ROOT+"/category/parent/{catId}";
    String FIND_CHILD_CATEGORY_OF_ENDPOINT = APP_ROOT+"/category/children/{catId}";
    String FIND_ALL_CATEGORY_IN_POS_ENDPOINT = APP_ROOT+"/category/all/pos/{posId}";
    String FIND_PAGE_CATEGORY_IN_POS_ENDPOINT = APP_ROOT+"/category/page/pos/{posId}/{pagenum}/{pagesize}";
    String FIND_PAGE_CATEGORY_CONTANING_ENDPOINT = APP_ROOT+"/category/page/pos/catname/{posId}/{sample}/" +
            "{pagenum}/{pagesize}";
    String DELETE_CATEGORY_BY_ID_ENDPOINT = APP_ROOT+"/cat/delete/id/{catId}";
    String DELETE_CATEGORY_BY_CODE_ENDPOINT = APP_ROOT+"/pos/delete/code/{catCode}/{posId}";
}
