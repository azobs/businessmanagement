package com.c2psi.businessmanagement.utils.client.command;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SaleInvoiceCashApiConstant {
    /***************************************
     * End point de SaleInvoiceCashApi
     */
    String SALEINVOICECASH_ENDPOINT = APP_ROOT+"/saleInvoiceCash";
    String CREATE_SALEINVOICECASH_ENDPOINT = APP_ROOT+"/saleInvoiceCash/create";
    String UPDATE_SALEINVOICECASH_ENDPOINT = APP_ROOT+"/saleInvoiceCash/update";
    String DELETE_SALEINVOICECASH_BY_ID_ENDPOINT = APP_ROOT+"/saleInvoiceCash/delete/id/{saleiCapsuleId}";
    String FIND_SALEINVOICECASH_BY_ID_ENDPOINT = APP_ROOT+"/saleInvoiceCash/id/{saleiCapsuleId}";
    String FIND_SALEINVOICECASH_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/saleInvoiceCash/pos/code/{saleiCapsuleCode}/{posId}";
    String FIND_ALL_SALEINVOICECASH_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/all/between/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/page/between/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_OF_CLIENT_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/client/all/between/{clientId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_OF_CLIENT_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/client/page/between/{clientId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/userbm/all/between/{userbmId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/userbm/page/between/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/all/between/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/page/between/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/userbm/all/between/{userbmId}/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/userbm/page/between/{userbmId}/{posId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/client/all/between/{clientId}/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/client/page/between/{clientId}/{posId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/client/userbm/page/between/{clientId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICECASH_FOR_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/" +
                    "{from}/{to}";
    String FIND_PAGE_SALEINVOICECASH_FOR_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleInvoiceCash/pos/client/userbm/page/between/{clientId}/{userbmId}/{posId}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";

}
