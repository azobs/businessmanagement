package com.c2psi.businessmanagement.utils.client.command;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SaleInvoiceCapsuleApiConstant {
    /***************************************
     * End point de SaleInvoiceCapsuleApi
     */
    String SALEINVOICECAPSULE_ENDPOINT = APP_ROOT+"/saleInvoiceCapsule";
    String CREATE_SALEINVOICECAPSULE_ENDPOINT = APP_ROOT+"/saleInvoiceCapsule/create";
    String UPDATE_SALEINVOICECAPSULE_ENDPOINT = APP_ROOT+"/saleInvoiceCapsule/update";
    String DELETE_SALEINVOICECAPSULE_BY_ID_ENDPOINT = APP_ROOT+"/saleInvoiceCapsule/delete/id/{saleiCapsuleId}";
    String FIND_SALEINVOICECAPSULE_BY_ID_ENDPOINT = APP_ROOT+"/saleInvoiceCapsule/id/{saleiCapsuleId}";
    String FIND_SALEINVOICECAPSULE_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/saleInvoiceCapsule/pos/code/{saleiCapsuleCode}/{posId}";
    String FIND_ALL_SALEINVOICECAPSULE_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/all/between/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/page/between/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_OF_CLIENT_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/client/all/between/{clientId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_OF_CLIENT_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/client/page/between/{clientId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/userbm/all/between/{userbmId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/userbm/page/between/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/all/between/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/page/between/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/userbm/all/between/{userbmId}/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/userbm/page/between/{userbmId}/{posId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/client/all/between/{clientId}/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/client/page/between/{clientId}/{posId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/client/userbm/page/between/{clientId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECAPSULE_FOR_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/" +
                    "{from}/{to}";
    String FIND_PAGE_SALEINVOICECAPSULE_FOR_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCapsule/pos/client/userbm/page/between/{clientId}/{userbmId}/{posId}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";
}
