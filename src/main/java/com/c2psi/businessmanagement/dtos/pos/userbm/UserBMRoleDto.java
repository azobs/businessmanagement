package com.c2psi.businessmanagement.dtos.pos.userbm;

import com.c2psi.businessmanagement.models.UserBMRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

@Data
@Builder
public class UserBMRoleDto {
    Long id;
    @NotNull(message = "Attribution date cannot be null")
    @PastOrPresent(message = "The attribution date cannot be in the future")
    Instant userbmroleAttributionDate;
    @NotNull(message = "The role to be attributed cannot be null")
    RoleDto userbmroleRoleDto;
    @NotNull(message = "The userbm to be attributed cannot be null")
    UserBMDto userbmroleUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static UserBMRoleDto fromEntity(UserBMRole userBMRole){
        if(userBMRole == null){
            return null;
        }
        return UserBMRoleDto.builder()
                .id(userBMRole.getId())
                .userbmroleAttributionDate(userBMRole.getUserbmroleAttributionDate())
                .userbmroleRoleDto(RoleDto.fromEntity(userBMRole.getUserbmroleRole()))
                .userbmroleUserbmDto(UserBMDto.fromEntity(userBMRole.getUserbmroleUserbm()))
                .build();
    }
    public static UserBMRole toEntity(UserBMRoleDto userBMRoleDto){
        if(userBMRoleDto == null){
            return null;
        }
        UserBMRole userBMRole = new UserBMRole();
        userBMRole.setId(userBMRoleDto.getId());
        userBMRole.setUserbmroleAttributionDate(userBMRoleDto.getUserbmroleAttributionDate());
        userBMRole.setUserbmroleRole(RoleDto.toEntity(userBMRoleDto.getUserbmroleRoleDto()));
        userBMRole.setUserbmroleUserbm(UserBMDto.toEntity(userBMRoleDto.getUserbmroleUserbmDto()));
        return userBMRole;
    }
}
