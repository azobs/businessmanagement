package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SupplyInvoiceDamageApiConstant {
    String SUPPLYINVOICEDAMAGE_ENDPOINT = APP_ROOT+"/supplyInvoiceDamage";
    String CREATE_SUPPLYINVOICEDAMAGE_ENDPOINT = APP_ROOT+"/supplyInvoiceDamage/create";
    String UPDATE_SUPPLYINVOICEDAMAGE_ENDPOINT = APP_ROOT+"/supplyInvoiceDamage/update";
    String DELETE_SUPPLYINVOICEDAMAGE_BY_ID_ENDPOINT = APP_ROOT+"/supplyInvoiceDamage/delete/id/{sicashId}";
    String FIND_SUPPLYINVOICEDAMAGE_BY_ID_ENDPOINT = APP_ROOT+"/supplyInvoiceDamage/id/{sicashId}";
    String FIND_SUPPLYINVOICEDAMAGE_BY_CODE_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/code/pos/{sicashCode}/{posId}";
    String FIND_ALL_SUPPLYINVOICEDAMAGE_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICEDAMAGE_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICEDAMAGE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/userbm/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICEDAMAGE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICEDAMAGE_OF_PROVIDER_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/provider/all/{posId}/{providerId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICEDAMAGE_OF_PROVIDER_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/provider/page/{posId}/{providerId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICEDAMAGE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/userbm/provider/all/{posId}/{providerId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICEDAMAGE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceDamage/pos/userbm/provider/page/{posId}/{providerId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
}
