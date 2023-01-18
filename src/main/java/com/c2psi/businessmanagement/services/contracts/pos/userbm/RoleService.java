package com.c2psi.businessmanagement.services.contracts.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

import java.util.List;

public interface RoleService {
    RoleDto saveRole(RoleDto roleDto);
    RoleDto findRoleById(Long id);
    RoleDto findRoleByRolename(String roleName, Long entId);
    Boolean deleteRoleById(Long id);
    Boolean deleteRoleByRolename(String roleName, Long entId);
    List<RoleDto> findAllRoleOfEnterprise(Long entId);
    List<RoleDto> findAllRoleofUserBM(Long userbmId);
}
