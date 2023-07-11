package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface UnitApiConstant {
    String UNIT_ENDPOINT = APP_ROOT+"/unit";
    String CREATE_UNIT_ENDPOINT = APP_ROOT+"/unit/create";
    String UPDATE_UNIT_ENDPOINT = APP_ROOT+"/unit/update";
    String FIND_UNIT_BY_ID_ENDPOINT = APP_ROOT+"/unit/id/{unitId}";
    String FIND_UNIT_BY_NAME_ENDPOINT = APP_ROOT+"/unit/name/{unitName}/{posId}";
    String FIND_ALL_UNIT_IN_POS_ENDPOINT = APP_ROOT+"/unit/all/pos/{posId}";
    String FIND_PAGE_UNIT_IN_POS_ENDPOINT = APP_ROOT+"/unit/page/pos/{posId}/{pagenum}/{pagesize}";
    String DELETE_UNIT_BY_ID_ENDPOINT = APP_ROOT+"/unit/delete/id/{unitId}";
    String CREATE_UNITCONVERSION_ENDPOINT = APP_ROOT+"/unitconversion/create";
    String UPDATE_UNITCONVERSION_ENDPOINT = APP_ROOT+"/unitconversion/update";
    String CONVERTTO_UNIT_ENDPOINT = APP_ROOT+"/unitconversion/convert/{quantity}/{from}/{to}";
    String FIND_UNITCONVERSION_BY_ID_ENDPOINT = APP_ROOT+"/unitconversion/id/{unitconvId}";
    String FIND_CONVERSIONRULE_BETWEEN_ENDPOINT = APP_ROOT+"/unitconversion/rule/{from}/{to}";
    String FIND_ALL_CONVERTIBLE_UNIT_BETWEEN_ENDPOINT = APP_ROOT+"/unitconversion/rules/{from}";
    String DELETE_UNITCONVERSION_BY_ID_ENDPOINT = APP_ROOT+"/unitconversion/delete/id/{unitconvId}";
}
