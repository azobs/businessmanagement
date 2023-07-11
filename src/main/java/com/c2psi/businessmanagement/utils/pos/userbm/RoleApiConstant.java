package com.c2psi.businessmanagement.utils.pos.userbm;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface RoleApiConstant {
    String ROLE_ENDPOINT = APP_ROOT+"/role";
    String CREATE_ROLE_ENDPOINT = APP_ROOT+"/role/create";
    String FIND_ROLE_BY_ID_ENDPOINT = APP_ROOT+"/role/{idRole}";
    String FIND_ROLE_BY_ROLENAME_ENDPOINT = APP_ROOT+"/role/{roleName}/{entId}";
    String DELETE_ROLE_BY_ID_ENDPOINT = APP_ROOT+"/role/delete/{roleId}";
    String DELETE_ROLE_BY_ROLENAME_ENDPOINT = APP_ROOT+"/role/delete/{roleName}/{entId}";
    String FIND_ALL_ROLE_OF_ENTERPRISE_ENDPOINT = APP_ROOT+"/role/all/ent/{entId}";
    String FIND_ALL_ROLE_OF_USERBM_ENDPOINT = APP_ROOT+"/role/all/userbm/{userbmId}";
}
