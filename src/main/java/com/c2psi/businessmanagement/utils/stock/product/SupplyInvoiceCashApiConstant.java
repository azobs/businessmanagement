package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SupplyInvoiceCashApiConstant {
    String SUPPLYINVOICECASH_ENDPOINT = APP_ROOT+"/supplyInvoiceCash";
    String CREATE_SUPPLYINVOICECASH_ENDPOINT = APP_ROOT+"/supplyInvoiceCash/create";
    String UPDATE_SUPPLYINVOICECASH_ENDPOINT = APP_ROOT+"/supplyInvoiceCash/update";
    String DELETE_SUPPLYINVOICECASH_BY_ID_ENDPOINT = APP_ROOT+"/supplyInvoiceCash/delete/id/{sicashId}";
    String FIND_SUPPLYINVOICECASH_BY_ID_ENDPOINT = APP_ROOT+"/supplyInvoiceCash/id/{sicashId}";
    String FIND_SUPPLYINVOICECASH_BY_CODE_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/code/pos/{sicashCode}/{posId}";
    String FIND_ALL_SUPPLYINVOICECASH_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECASH_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICECASH_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/userbm/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECASH_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICECASH_OF_PROVIDER_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/provider/all/{posId}/{providerId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECASH_OF_PROVIDER_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/provider/page/{posId}/{providerId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICECASH_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/userbm/provider/all/{posId}/{providerId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECASH_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCash/pos/userbm/provider/page/{posId}/{providerId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
}
