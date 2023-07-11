package com.c2psi.businessmanagement.utils.client.command;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface CommandApiConstant {
    /**************************
     * End point de CommandApi
     */
    String COMMAND_ENDPOINT = APP_ROOT+"/command";
    String CREATE_COMMAND_ENDPOINT = APP_ROOT+"/command/create";
    String UPDATE_COMMAND_ENDPOINT = APP_ROOT+"/command/update";
    String SET_SALEINVOICE_FOR_COMMANDSTATUS_ENDPOINT = APP_ROOT+"/command/saleInvoice/{cmdId}/{saleInvoiceId}/{cmdStatus}";
    String ASSIGN_COMMAND_TO_DELIVERY_ENDPOINT = APP_ROOT+"/command/assignToDelivery/{cmdId}/{deliveryId}";
    String RESET_DELIVERY_OF_COMMAND_ENDPOINT = APP_ROOT+"/command/removeToDelivery/{cmdId}";
    String SWITCH_COMMANDSTATE_ENDPOINT = APP_ROOT+"/command/state/{cmdId}/{commandState}";
    String FIND_COMMAND_BY_ID_ENDPOINT = APP_ROOT+"/command/id/{cmdId}";
    String FIND_COMMAND_BY_CODE_IN_POS_ENDPOINT = APP_ROOT+"/command/code/{cmdCode}/{posId}";
    String DELETE_COMMAND_BY_ID_ENDPOINT = APP_ROOT+"/command/delete/id/{cmdId}";

    String FIND_ALL_COMMAND_IN_POS_BETWEEN_ENDPOINT = APP_ROOT+"/command/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/all/{posId}/{commandState}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/page/{posId}/{commandState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/type/all/{posId}/{commandType}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/type/page/{posId}/{commandType}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/status/all/{posId}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/status/page/{posId}/{commandStatus}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/deliveryState/all/{posId}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/deliveryState/page/{posId}/{deliveryState}/{from}/{to}/{pagenum}/" +
                    "{pagesize}";

    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/all/{posId}/{commandState}/{commandType}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/page/{posId}/{commandState}/{commandType}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/status/all/{posId}/{commandState}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/status/page/{posId}/{commandState}/{commandStatus}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/deliveryState/all/{posId}/{commandState}/{deliveryState}/{from}/" +
                    "{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/deliveryState/page/{posId}/{commandState}/{deliveryState}/{from}" +
                    "/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/type/status/all/{posId}/{commandType}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/type/status/all/{posId}/{commandType}/{commandStatus}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/type/deliveryState/all/{posId}/{commandType}/{deliveryState}/{from}/" +
                    "{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/type/deliveryState/page/{posId}/{commandType}/{deliveryState}/{from}/" +
                    "{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/status/all/{posId}/{commandState}/{commandType}/" +
                    "{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/status/all/{posId}/{commandState}/{commandType}/" +
                    "{commandStatus}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/deliveryState/all/{posId}/{commandState}/{commandType}/" +
                    "{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/deliveryState/page/{posId}/{commandState}/{commandType}/" +
                    "{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/status/deliveryState/all/{posId}/{commandState}/{commandStatus}/" +
                    "{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/status/deliveryState/page/{posId}/{commandState}/{commandStatus}/" +
                    "{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/status/deliveryState/all/{posId}/{commandState}/" +
                    "{commandType}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/state/type/status/deliveryState/page/{posId}/{commandState}/" +
                    "{commandType}/{commandStatus}/{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/all/{posId}/{clientId}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/page/{posId}/{clientId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/state/all/{posId}/{clientId}/{commandState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/state/page/{posId}/{clientId}/{commandState}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/all/{posId}/{clientId}/{commandType}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/page/{posId}/{clientId}/{commandType}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/status/all/{posId}/{clientId}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/status/page/{posId}/{clientId}/{commandStatus}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/deliveryState/all/{posId}/{clientId}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/deliveryState/page/{posId}/{clientId}/{deliveryState}/{from}/" +
                    "{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/status/all/{posId}/{clientId}/{commandType}/{commandStatus}" +
                    "/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/status/page/{posId}/{clientId}/{commandType}/" +
                    "{commandStatus}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/deliveryState/all/{posId}/{clientId}/{commandType}/" +
                    "{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/deliveryState/page/{posId}/{clientId}/{commandType}/" +
                    "{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/state/status/all/{posId}/{clientId}/{commandType}/" +
                    "{commandState}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/state/status/page/{posId}/{clientId}/{commandType}/" +
                    "{commandState}/{commandStatus}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/state/deliveryState/all/{posId}/{clientId}/{commandType}/" +
                    "{commandState}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/state/deliveryState/page/{posId}/{clientId}/{commandType}/" +
                    "{commandState}/{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/state/status/deliveryState/all/{posId}/{clientId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/type/state/status/deliveryState/page/{posId}/{clientId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/all/{posId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/all/{posId}/{userbmId}/{commandType}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/page/{posId}/{userbmId}/{commandType}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/state/all/{posId}/{userbmId}/{commandState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/state/page/{posId}/{userbmId}/{commandState}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/status/all/{posId}/{userbmId}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/status/page/{posId}/{userbmId}/{commandStatus}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/deliveryState/all/{posId}/{userbmId}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/deliveryState/page/{posId}/{userbmId}/{deliveryState}/{from}/" +
                    "{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/all/{posId}/{userbmId}/{commandType}/{commandState}/" +
                    "{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/page/{posId}/{userbmId}/{commandType}/" +
                    "{commandState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/status/all/{posId}/{userbmId}/{commandType}/" +
                    "{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/status/page/{posId}/{userbmId}/{commandType}/" +
                    "{commandStatus}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/deliveryState/all/{posId}/{userbmId}/{commandType}/" +
                    "{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/deliveryState/page/{posId}/{userbmId}/{commandType}/" +
                    "{deliveryState}/{from}/{to}";

    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/status/all/{posId}/{userbmId}/{commandType}/" +
                    "{commandState}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/status/page/{posId}/{userbmId}/{commandType}/" +
                    "{commandState}/{commandStatus}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/deliveryState/all/{posId}/{userbmId}/{commandType}/" +
                    "{commandState}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/deliveryState/page/{posId}/{userbmId}/{commandType}/" +
                    "{commandState}/{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/status/deliveryState/all/{posId}/{userbmId}/{commandType}/" +
                    "{commandState}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/status/deliveryState/page/{posId}/{userbmId}/{commandType}/" +
                    "{commandStatus}/{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/status/deliveryState/all/{posId}/{userbmId}/" +
                    "{commandType}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/userbm/type/state/status/deliveryState/page/{posId}/{userbmId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/all/{posId}/{clientId}/{userbmId}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/page/{posId}/{clientId}/{userbmId}/{from}/{to}/" +
                    "{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/all/{posId}/{clientId}/{userbmId}/{commandType}" +
                    "/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/page/{posId}/{clientId}/{userbmId}/{commandType}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/state/all/{posId}/{clientId}/{userbmId}/{commandState}/" +
                    "{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/state/page/{posId}/{clientId}/{userbmId}/{commandState}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/status/all/{posId}/{clientId}/{userbmId}/{commandStatus}/" +
                    "{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/status/page/{posId}/{clientId}/{userbmId}/{commandStatus}/" +
                    "{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/deliveryState/all/{posId}/{clientId}/{userbmId}/" +
                    "{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/deliveryState/page/{posId}/{clientId}/{userbmId}/" +
                    "{deliveryState}/{from}/{to}/{pagenum}/{pagesize}";

    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/status/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/status/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{from}/{to}/{pagenum}/{pagesize}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/deliveryState/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/deliveryState/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{deliveryState}/{from}/{to}";

    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/status/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/status/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{from}/{to}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/deliveryState/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/deliveryState/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{deliveryState}/{from}/{to}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/status/deliveryState/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/status/deliveryState/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/status/deliveryState/all/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{deliveryState}/{from}/{to}";
    String FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT =
            APP_ROOT+"/command/pos/client/userbm/type/state/status/deliveryState/page/{posId}/{clientId}/{userbmId}/" +
                    "{commandType}/{commandState}/{commandStatus}/{deliveryState}/{from}/{to}";

    String FIND_ALL_COMMAND_OF_LOADING_ENDPOINT = APP_ROOT+"/command/pos/loading/all/{loadingId}/{posId}";
    String FIND_PAGE_COMMAND_OF_LOADING_ENDPOINT = APP_ROOT+"/command/pos/loading/page/{loadingId}/{posId}/{pagenum}/{pagesize}";

}
