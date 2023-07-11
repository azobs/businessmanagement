package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ArticleApiConstant {
    String ARTICLE_ENDPOINT = APP_ROOT+"/article";
    String FIND_ALL_ARTICLE_OF_POS_ENDPOINT = APP_ROOT+"/article/all/pos/{posId}";
    String FIND_ALL_ARTICLE_OF_POS_ORDERBY_CREATIONDATE_ENDPOINT = APP_ROOT+"/article/all/pos/creationDate/{posId}";
    String FIND_PAGE_ARTICLE_OF_POS_ENDPOINT = APP_ROOT+"/article/page/pos/{posId}/{pagenum}/{pagesize}";
    String FIND_PAGE_ARTICLE_OF_POS_ORDERBY_CREATIONDATE_ENDPOINT = APP_ROOT+"/article/page/pos/creationDate/{posId}/" +
            "{pagenum}/{pagesize}";
    String FIND_ALL_ARTICLE_OF_CATEGORY_ENDPOINT = APP_ROOT+"/article/all/cat/{catId}";
    String FIND_PAGE_ARTICLE_OF_CATEGORY_ENDPOINT = APP_ROOT+"/article/page/cat/{catId}/{pagenum}/{pagesize}";
    String CREATE_ARTICLE_ENDPOINT_ENDPOINT = APP_ROOT+"/article/create";
    String UPDATE_ARTICLE_ENDPOINT_ENDPOINT = APP_ROOT+"/article/update";
    String FIX_QUANTITY_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/update/stock";
    String UPDATE_UNIT_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/update/unit";
    String UPDATE_BASEPRICE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/update/bprice";
    String FIND_ARTICLE_BY_ID_ENDPOINT = APP_ROOT+"/article/id/{artId}";
    String FIND_ARTICLE_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/article/code/{artCode}/{posId}";
    String FIND_ALL_ARTICLE_OF_PROVIDER_ENDPOINT = APP_ROOT+"/article/all/pos/provider/{posId}/{providerId}";
    String FIND_PAGE_ARTICLE_OF_PROVIDER_ENDPOINT = APP_ROOT+"/article/page/pos/provider/{posId}/{providerId}/" +
            "{pagenum}/{pagesize}";
    String FIND_PAGE_ARTICLE_CONTANING_ENDPOINT = APP_ROOT+"/article/name/page/{posId}/{sample}/{pagenum}/{pagesize}";
    String ADD_QUANTITY_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/update/add";
    String REDUCE_QUANTITY_OF_ARTICLE_ENDPOINT = APP_ROOT+"/article/update/reduce";
    String ADD_DAMAGE_ARTICLE_ENDPOINT = APP_ROOT+"/article/damage/update/add";
    String REDUCE_DAMAGE_ARTICLE_ENDPOINT = APP_ROOT+"/article/damage/update/reduce";
    String GET_COMMON_EFFECTIVE_PRICE_TO_APPLIED_ENDPOINT = APP_ROOT+"/article/price/{artId}/{quantity}";
    String DELETE_ARTICLE_BY_ID_ENDPOINT = APP_ROOT+"/article/delete/id/{artId}";
}
