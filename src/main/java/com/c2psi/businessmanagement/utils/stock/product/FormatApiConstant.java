package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface FormatApiConstant {
    String FORMAT_ENDPOINT = APP_ROOT+"/format";
    String CREATE_FORMAT_ENDPOINT = APP_ROOT+"/format/create";
    String UPDATE_FORMAT_ENDPOINT = APP_ROOT+"/format/update";
    String FIND_FORMAT_BY_ID_ENDPOINT = APP_ROOT+"/format/{formatId}";
    String FIND_FORMAT_BY_FULLCHARACTERISTICS_ENDPOINT = APP_ROOT+"/format/pos/{posId}/{formatName}/{formatCapacity}";
    String FIND_ALL_FORMAT_IN_POS_ENDPOINT = APP_ROOT+"/format/all/pos/{posId}";
    String FIND_PAGE_FORMAT_IN_POS_ENDPOINT = APP_ROOT+"/format/page/pos/{posId}/{pagenum}/{pagesize}";
    String DELETE_FORMAT_BY_ID_ENDPOINT = APP_ROOT+"/format/delete/id/{formatId}";

}
