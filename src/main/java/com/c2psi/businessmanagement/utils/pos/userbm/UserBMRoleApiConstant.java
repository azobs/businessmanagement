package com.c2psi.businessmanagement.utils.pos.userbm;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface UserBMRoleApiConstant {
    String USERBMROLE_ENDPOINT = APP_ROOT+"/userbmrole";
    String CREATE_USERBMROLE_ENDPOINT = APP_ROOT+"/userbmrole/create";
    String FIND_USERBMROLE_BY_ID_ENDPOINT = APP_ROOT+"/userbmrole/{iduserBMRole}";
    String FIND_USERBMROLE_BY_USERBM_AND_ROLE_ENDPOINT = APP_ROOT+"/userbmrole/{userbmDtoId}/{roleDtoId}";
    String FIND_ALL_USERBMROLE_OF_POS_ENDPOINT = APP_ROOT+"/userbmrole/all";
    String DELETE_USERBMROLE_BY_ID_ENDPOINT = APP_ROOT+"/userbmrole/delete/{userbmroleId}";
}
