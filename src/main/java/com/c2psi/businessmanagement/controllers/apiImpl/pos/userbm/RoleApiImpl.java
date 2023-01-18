package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.controllers.api.pos.userbm.RoleApi;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleApiImpl implements RoleApi {
    private RoleService roleService;
    @Autowired
    public RoleApiImpl(
            RoleService roleService
    )
    {
        this.roleService = roleService;
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        return roleService.saveRole(roleDto);
    }

    @Override
    public RoleDto findRoleById(Long id) {
        return roleService.findRoleById(id);
    }

    @Override
    public RoleDto findRoleByRolename(String roleName, Long entId) {
        return roleService.findRoleByRolename(roleName, entId);
    }

    @Override
    public Boolean deleteRoleById(Long id) {
        return roleService.deleteRoleById(id);
    }

    @Override
    public Boolean deleteRoleByRolename(String roleName, Long entId) {
        return roleService.deleteRoleByRolename(roleName, entId);
    }

    @Override
    public List<RoleDto> findAllRoleOfEnterprise(Long entId) {
        return roleService.findAllRoleOfEnterprise(entId);
    }

    @Override
    public List<RoleDto> findAllRoleofUserBM(Long userbmId) {
        return roleService.findAllRoleofUserBM(userbmId);
    }
}
