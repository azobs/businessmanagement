package com.c2psi.businessmanagement.utils.client.command;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface SaleInvoiceDamageApiConstant {
    /***************************************
     * End point de SaleDamageCashApi
     */
    String SALEINVOICEDAMAGE_ENDPOINT = APP_ROOT+"/saleDamageCash";
    String CREATE_SALEINVOICEDAMAGE_ENDPOINT = APP_ROOT+"/saleDamageCash/create";
    String UPDATE_SALEINVOICEDAMAGE_ENDPOINT = APP_ROOT+"/saleDamageCash/update";
    String DELETE_SALEINVOICEDAMAGE_BY_ID_ENDPOINT = APP_ROOT+"/saleDamageCash/delete/id/{saleiCapsuleId}";
    String FIND_SALEINVOICEDAMAGE_BY_ID_ENDPOINT = APP_ROOT+"/saleDamageCash/id/{saleiCapsuleId}";
    String FIND_SALEINVOICEDAMAGE_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/saleDamageCash/pos/code/{saleiCapsuleCode}/{posId}";
    String FIND_ALL_SALEINVOICEDAMAGE_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/all/between/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/page/between/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_OF_CLIENT_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/client/all/between/{clientId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_OF_CLIENT_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/client/page/between/{clientId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/userbm/all/between/{userbmId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_OF_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/userbm/page/between/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/all/between/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/page/between/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/userbm/all/between/{userbmId}/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/userbm/page/between/{userbmId}/{posId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/client/all/between/{clientId}/{posId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/client/page/between/{clientId}/{posId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/client/userbm/page/between/{clientId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_SALEINVOICEDAMAGE_FOR_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/" +
                    "{from}/{to}";
    String FIND_PAGE_SALEINVOICEDAMAGE_FOR_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/saleDamageCash/pos/client/userbm/page/between/{clientId}/{userbmId}/{posId}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";
}
