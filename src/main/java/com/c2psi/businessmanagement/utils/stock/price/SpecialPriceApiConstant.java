package com.c2psi.businessmanagement.utils.stock.price;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SpecialPriceApiConstant {
    String SPECIALPRICE_ENDPOINT = APP_ROOT+"/article/bprice/special";
    String CREATE_SPECIALPRICE_ENDPOINT = APP_ROOT+"/article/bprice/special/create";
    String UPDATE_SPECIALPRICE_ENDPOINT = APP_ROOT+"/article/bprice/special/update";
    String FIND_SPECIALPRICE_BY_ID_ENDPOINT = APP_ROOT+"/article/bprice/special/id/{spId}";
    String FIND_ALL_SPECIALPRICE_OF_BASEPRICE_ENDPOINT = APP_ROOT+"/article/bprice/special/all/{bpId}";
    String FIND_PAGE_SPECIALPRICE_OF_BASEPRICE_ENDPOINT = APP_ROOT+"/article/bprice/special/page/{bpId}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_SPECIALPRICE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/bprice/special/article/all/{artId}";
    String FIND_PAGE_SPECIALPRICE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/bprice/special/article/page/{artId}/" +
            "{pagenum}/{pagesize}";
    String DELETE_SPECIALPRICE_BY_ID_ENDPOINT = APP_ROOT+"/article/bprice/special/delete/id/{spId}";
}
