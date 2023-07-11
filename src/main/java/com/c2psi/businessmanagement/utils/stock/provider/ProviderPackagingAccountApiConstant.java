package com.c2psi.businessmanagement.utils.stock.provider;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface ProviderPackagingAccountApiConstant {
    String PROVIDERPACKAGINGACCOUNT_ENDPOINT = APP_ROOT+"/propackagingaccount";
    String CREATE_PROVIDERPACKAGINGACCOUNT_ENDPOINT = APP_ROOT+"/propackagingaccount/create";
    String FIND_PROVIDERPACKAGINGACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/propackagingaccount/id/{propackaccId}";
    String FIND_PROVIDERPACKAGINGACCOUNT_OF_PROVIDER_ENDPOINT = APP_ROOT+"/propackagingaccount/packaging/provider/" +
            "{packagingId}/{providerId}";
    String FIND_ALL_PROVIDERPACKAGINGACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/propackagingaccount/all/{providerId}";
    String FIND_PAGE_PROVIDERPACKAGINGACCOUNT_IN_POS_ENDPOINT = APP_ROOT+"/propackagingaccount/page/{providerId}/" +
            "{pagenum}/{pagesize}";
    String DELETE_PROVIDERPACKAGINGACCOUNT_BY_ID_ENDPOINT = APP_ROOT+"/propackagingaccount/delete/{propackId}";
    String CREATE_PROVIDERPACKAGING_OPERATION_ENDPOINT = APP_ROOT+"/propackagingaccount/operation/create";
    String UPDATE_PROVIDERPACKAGING_OPERATION_ENDPOINT = APP_ROOT+"/propackagingaccount/operation/update";
    String DELETE_PROVIDERPACKAGING_OPERATION_BY_ID_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/delete/{propackopId}";
    String FIND_ALL_PROVIDERPACKAGING_OPERATION_IN_POS_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/all/{propackaccId}";
    String FIND_PAGE_PROVIDERPACKAGING_OPERATION_IN_POS_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/page/{propackaccId}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERPACKAGING_OPERATION_OF_TYPE_IN_POS_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/type/all/{propackaccId}/{opType}";
    String FIND_PAGE_PROVIDERPACKAGING_OPERATION_OF_TYPE_IN_POS_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/type/page/{propackaccId}/{opType}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERPACKAGING_OPERATION_BETWEEN_ENDPOINT = APP_ROOT+"/propackagingaccount/operation/all/" +
            "between/{propackaccId}/{from}/{to}";
    String FIND_PAGE_PROVIDERPACKAGING_OPERATION_BETWEEN_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/all/between/{propackaccId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_PROVIDERPACKAGING_OPERATION_OF_TYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/propackagingaccount/operation/type/all/between/{propackaccId}/{optype}/{from}/{to}";
    String FIND_PAGE_PROVIDERPACKAGING_OPERATION_OF_TYPE_BETWEEN_ENDPOINT = APP_ROOT+"/propackagingaccount/operation/" +
            "type/page/between/{propackaccId}/{optype}/{from}/{to}/{pagenum}/{pagesize}";
}
