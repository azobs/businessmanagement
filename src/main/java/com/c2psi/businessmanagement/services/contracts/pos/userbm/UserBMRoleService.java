package com.c2psi.businessmanagement.services.contracts.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;

import java.util.List;

public interface UserBMRoleService {
    UserBMRoleDto saveUserBMRole(UserBMRoleDto userbmRoleDto);
    UserBMRoleDto findUserBMRoleById(Long id);
    UserBMRoleDto findUserBMRoleByUserBMandRole(UserBMDto userbmDto,
                                                RoleDto roleDto);
    
    List<UserBMRoleDto> findAllUserBMRoleofEnterprise(EnterpriseDto entDto);
    List<UserBMRoleDto> findAllUserBMRoleofPointofsale(PointofsaleDto posDto);
    Boolean isUserBMRoleDeleteable(Long id);
    Boolean deleteUserBMRoleById(Long id);

}
