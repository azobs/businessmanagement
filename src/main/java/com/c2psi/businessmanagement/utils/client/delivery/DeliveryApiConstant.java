package com.c2psi.businessmanagement.utils.client.delivery;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface DeliveryApiConstant {
    /***************************************
     * End point de DeliveryApi
     */

    String DELIVERY_ENDPOINT = APP_ROOT+"/delivery";
    String CREATE_DELIVERY_ENDPOINT = APP_ROOT+"/delivery/create";
    String UPDATE_DELIVERY_ENDPOINT = APP_ROOT+"/delivery/update";
    String FIND_DELIVERY_BY_ID_ENDPOINT  = APP_ROOT+"/delivery/id/{deliveryId}";
    String FIND_DELIVERY_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/delivery/pos/code/{deliveryCode}/{posId}";
    String DELETE_DELIVERY_BY_ID_ENDPOINT = APP_ROOT+"/delivery/delete/id/{deliveryId}";
    String FIND_ALL_DELIVERY_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/delivery/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_DELIVERY_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_DELIVERY_IN_POS_WITH_STATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/state/all/{deliveryState}/{posId}/{from}/{to}";
    String FIND_PAGE_DELIVERY_IN_POS_WITH_STATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/state/page/{deliveryState}/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_DELIVERY_IN_POS_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/userbm/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_DELIVERY_IN_POS_FOR_USERBM_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_DELIVERY_IN_POS_FOR_USERBM_WITH_STATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/userbm/state/all/{deliveryState}/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_DELIVERY_IN_POS_FOR_USERBM_WITH_STATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/delivery/pos/userbm/state/page/{deliveryState}/{posId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String CREATE_DELIVERYDETAILS_ENDPOINT = APP_ROOT+"/delivery/details/create";
    String UPDATE_DELIVERYDETAILS_ENDPOINT = APP_ROOT+"/delivery/details/update";
    String FIND_DELIVERYDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/delivery/details/id/{ddId}";
    String FIND_DELIVERYDETAILS_IN_DELIVERY_WITH_PACKAGING_ENDPOINT =
            APP_ROOT+"/delivery/details/packaging/pos/{packagingId}/{deliveryId}";
    String DELETE_DELIVERYDETAILS_BY_ID_ENDPOINT = APP_ROOT+"/delivery/details/delete/id/{ddId}";
    String FIND_ALL_DELIVERYDETAILS_IN_DELIVERY_ENDPOINT = APP_ROOT+"/delivery/details/all/{deliveryId}";
    String FIND_PAGE_DELIVERYDETAILS_IN_DELIVERY_ENDPOINT = APP_ROOT+"/delivery/details/page/{deliveryId}";
}
