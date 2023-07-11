package com.c2psi.businessmanagement.utils.stock.price;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface BasePriceApiConstant {
    String BASEPRICE_ENDPOINT = APP_ROOT+"/article/bprice";
    String CREATE_BASEPRICE_ENDPOINT = APP_ROOT+"/article/bprice/create";
    String UPDATE_BASEPRICE_ENDPOINT = APP_ROOT+"/article/bprice/update";
    String FIND_BASEPRICE_BY_ID_ENDPOINT = APP_ROOT+"/article/bprice/id/{bpId}";
    String DELETE_BASEPRICE_BY_ID_ENDPOINT = APP_ROOT+"/article/bprice/delete/id/{bpId}";

}
