package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
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
        RoleType roleType = RoleType.Dg;
        switch (roleName){
            case "Deliver": roleType = RoleType.Deliver;
            case "Dg": roleType = RoleType.Dg;
            case "Manager": roleType = RoleType.Manager;
            case "Pdg": roleType = RoleType.Pdg;
            case "Saler": roleType = RoleType.Saler;
            case "Storekeeper": roleType = RoleType.Storekeeper;
        }
        /*String roleName = "";
        switch (role_dto.getRoleName()){
            case Deliver: roleName = RoleType.Deliver.name();
            case Dg: roleName = RoleType.Dg.name();
            case Manager: roleName = RoleType.Manager.name();
            case Pdg: roleName = RoleType.Pdg.name();
            case Saler: roleName = RoleType.Saler.name();
            case Storekeeper: roleName = RoleType.Storekeeper.name();
        }*/
        return roleService.findRoleByRolename(roleType, entId);
    }

    @Override
    public Boolean deleteRoleById(Long id) {
        return roleService.deleteRoleById(id);
    }

    @Override
    public Boolean deleteRoleByRolename(String roleName, Long entId) {
        RoleType roleType = RoleType.Dg;
        switch (roleName){
            case "Deliver": roleType = RoleType.Deliver;
            case "Dg": roleType = RoleType.Dg;
            case "Manager": roleType = RoleType.Manager;
            case "Pdg": roleType = RoleType.Pdg;
            case "Saler": roleType = RoleType.Saler;
            case "Storekeeper": roleType = RoleType.Storekeeper;
        }
        return roleService.deleteRoleByRolename(roleType, entId);
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
