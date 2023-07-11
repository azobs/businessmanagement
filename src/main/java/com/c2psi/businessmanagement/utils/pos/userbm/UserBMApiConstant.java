package com.c2psi.businessmanagement.utils.pos.userbm;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface UserBMApiConstant {
    String USERBM_ENDPOINT = APP_ROOT+"/userbm";
    String CREATE_USERBM_ENDPOINT = APP_ROOT+"/userbm/create";
    String UPDATE_USERBM_ENDPOINT = APP_ROOT+"/userbm/update";
    String SWITCH_USERBMSTATE_ENDPOINT = APP_ROOT+"/userbm/switchUserBMState";
    String RESETPASSWORD_ENDPOINT = APP_ROOT+"/userbm/resetPassword";
    String FIND_USERBM_BY_LOGIN_ENDPOINT  = APP_ROOT+"/userbm/login/{bmLogin}";
    String FIND_USERBM_BY_CNI_ENDPOINT  = APP_ROOT+"/userbm/cni/{bmCni}";
    String FIND_USERBM_BY_EMAIL_ENDPOINT  = APP_ROOT+"/userbm/email/{bmEmail}";
    String FIND_USERBM_BY_FULLNAME_ENDPOINT  = APP_ROOT+"/userbm/fullname/{bmName}/{bmSurname}/{bmDob}";
    String FIND_USERBM_BY_ID_ENDPOINT  = APP_ROOT+"/userbm/id/{bmId}";
    String FIND_ALL_USERBM_OF_TYPE_ENDPOINT  = APP_ROOT+"/userbm/type/{bmUsertype}";
    String FIND_ALL_USERBM_ENDPOINT  = APP_ROOT+"/userbm/all";
    String FIND_ALL_USERBM_OF_POS_ENDPOINT  = APP_ROOT+"/userbm/pos/{posId}";

    String FIND_PAGE_USERBM_OF_POS_ENDPOINT  = APP_ROOT+"/userbm/page/pos/{posId}/{sample}/{pagenum}/{pagesize}";
    String FIND_PAGE_USERBM_ENDPOINT  = APP_ROOT+"/userbm/page";
    String FIND_ALL_USERBM_CONTAINING_SAMPLE_ENDPOINT  = APP_ROOT+"/userbm/page/{sample}";
    String FIND_PAGE_USERBM_CONTAINING_SAMPLE_ENDPOINT  = APP_ROOT+"/userbm/page/{sample}/{pagenum}/{pagesize}";
    String DELETE_USERBM_BY_LOGIN_ENDPOINT  = APP_ROOT+"/userbm/delete/login/{bmLogin}";
    String DELETE_USERBM_BY_CNI_ENDPOINT  = APP_ROOT+"/userbm/delete/cni/{bmCni}";
    String DELETE_USERBM_BY_FULLNAME_ENDPOINT  = APP_ROOT+"/userbm/delete/fullname/{bmName}/{bmSurname}/{bmDob}";
    String DELETE_USERBM_BY_ID_ENDPOINT  = APP_ROOT+"/userbm/delete/id/{bmId}";
}
