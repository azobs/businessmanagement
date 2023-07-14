package com.c2psi.businessmanagement.dtos.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.models.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class RoleDto {
    Long id;
    @NotNull(message = "The rolename cannot be null")
    RoleType roleName;
    @NotNull(message = "The rolealias cannot be null")
    @Size(min = 2, max = 20, message = "The rolealias size must be between 2 and 20 characters")
    String roleAlias;
    String roleDescription;
    /*******************************************************************
     * @NotNull(message = "Each role must belonging to an enterprise")
     * L'entreprise peut etre null au cas ou le role est un role de
     * la plateforme comme ADMIN
     */
    EnterpriseDto roleEntDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static RoleDto fromEntity(Role role){
        if(role == null){
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .roleAlias(role.getRoleAlias())
                .roleDescription(role.getRoleDescription())
                .roleName(role.getRoleName())
                .roleEntDto(EnterpriseDto.fromEntity(role.getRoleEnt()))
                .build();
    }
    public static Role toEntity(RoleDto roleDto){
        if(roleDto == null){
            return null;
        }
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setRoleAlias(roleDto.getRoleAlias());
        role.setRoleDescription(roleDto.getRoleDescription());
        role.setRoleName(roleDto.getRoleName());
        role.setRoleEnt(EnterpriseDto.toEntity(roleDto.roleEntDto));
        return role;
    }
}
