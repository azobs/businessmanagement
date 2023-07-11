package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SupplyInvoiceCapsuleApiConstant {
    String SUPPLYINVOICECAPSULE_ENDPOINT = APP_ROOT+"/supplyInvoiceCapsule";
    String CREATE_SUPPLYINVOICECAPSULE_ENDPOINT = APP_ROOT+"/supplyInvoiceCapsule/create";
    String UPDATE_SUPPLYINVOICECAPSULE_ENDPOINT = APP_ROOT+"/supplyInvoiceCapsule/update";
    String DELETE_SUPPLYINVOICECAPSULE_BY_ID_ENDPOINT = APP_ROOT+"/supplyInvoiceCapsule/delete/id/{sicapsId}";
    String FIND_SUPPLYINVOICECAPSULE_BY_ID_ENDPOINT = APP_ROOT+"/supplyInvoiceCapsule/id/{sicapsId}";
    String FIND_SUPPLYINVOICECAPSULE_BY_CODE_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/code/pos/{sicapsCode}/{posId}";
    String FIND_ALL_SUPPLYINVOICECAPSULE_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECAPSULE_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICECAPSULE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/userbm/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECAPSULE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICECAPSULE_OF_PROVIDER_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/provider/all/{posId}/{providerId}/{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECAPSULE_OF_PROVIDER_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/provider/page/{posId}/{providerId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SUPPLYINVOICECAPSULE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/userbm/provider/all/{posId}/{providerId}/{userbmId}/" +
                    "{from}/{to}";
    String FIND_PAGE_SUPPLYINVOICECAPSULE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/supplyInvoiceCapsule/pos/userbm/provider/page/{posId}/{providerId}/{userbmId}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";

}
