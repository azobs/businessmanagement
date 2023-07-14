package com.c2psi.businessmanagement.services.contracts.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleDto saveRole(RoleDto roleDto);
    RoleDto findRoleById(Long id);
    RoleDto findRoleByRolename(RoleType roleName, Long entId);
    RoleDto findRoleByRolename(RoleType roleName);
    Optional<RoleDto> isRoleByRolenameExist(RoleType roleName);
    Boolean isRoleDeleteable(Long id);
    Boolean deleteRoleById(Long id);
    Boolean deleteRoleByRolename(RoleType roleName, Long entId);
    List<RoleDto> findAllRoleOfEnterprise(Long entId);
    List<RoleDto> findAllRoleofUserBM(Long userbmId);
}
