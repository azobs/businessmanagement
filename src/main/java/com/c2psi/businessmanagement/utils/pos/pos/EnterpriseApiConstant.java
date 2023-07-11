package com.c2psi.businessmanagement.utils.pos.pos;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface EnterpriseApiConstant {
    String ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise";
    String CREATE_ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise/create";
    String UPDATE_ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise/update";
    String SET_ADMIN_ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise/setAdmin";
    String FIND_ENTERPRISE_BY_ID_ENDPOINT = APP_ROOT+"/enterprise/{enterpriseId}";
    String FIND_ENTERPRISE_BY_NAME_ENDPOINT = APP_ROOT+"/enterprise/name/{entName}";
    String FIND_ENTERPRISE_BY_NIU_ENDPOINT = APP_ROOT+"/enterprise/niu/{entNiu}";
    String DELETE_ENTERPRISE_BY_ID_ENDPOINT = APP_ROOT+"/enterprise/delete/{entId}";
    String DELETE_ENTERPRISE_BY_NAME_ENDPOINT = APP_ROOT+"/enterprise/delete/name/{entName}";
    String DELETE_ENTERPRISE_BY_NIU_ENDPOINT = APP_ROOT+"/enterprise/delete/niu/{entNiu}";
    String FIND_ALL_ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise/all";
    String FIND_ALL_POINTOFSALE_OF_ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise/allPointofsale/{entId}";
    String GET_ENTERPRISE_TURNOVER_ENDPOINT = APP_ROOT+"/enterprise/getTurnover/{entId}/{startDate}/{endDate}";
    String FIND_ALL_EMPLOYE_OF_ENTERPRISE_ENDPOINT = APP_ROOT+"/enterprise/allEmploye/{entId}";
    String GET_ENTERPRISE_TOTAL_CASH_ENDPOINT = APP_ROOT+"/enterprise/getTotalCash/{entId}";
    String GET_ENTERPRISE_NUMBER_OF_DAMAGE_ENDPOINT = APP_ROOT+"/enterprise/getNumberofDamage/{entId}";
    String GET_ENTERPRISE_NUMBER_OF_DAMAGE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/enterprise/getNumberofDamage/{entId}/{artId}";
    String GET_ENTERPRISE_NUMBER_OF_CAPSULE_ENDPOINT = APP_ROOT+"/enterprise/getNumberofCover/{entId}";
    String GET_ENTERPRISE_NUMBER_OF_CAPSULE_OF_ARTICLE_ENDPOINT = APP_ROOT+"/enterprise/getNumberofCover/{entId}/{artId}";
    String GET_ENTERPRISE_NUMBER_OF_PACKAGING_ENDPOINT = APP_ROOT+"/enterprise/getNumberofPackaging/{entId}";
    String GET_ENTERPRISE_NUMBER_OF_PACKAGING_OF_PROVIDER_ENDPOINT = APP_ROOT+"/enterprise/getNumberofCover/{entId}/{providerId}";

}
