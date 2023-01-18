package com.c2psi.businessmanagement.dtos.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.UserBM;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Builder
public class UserBMDto {
    Long id;

    @NotNull(message = "The user login cannot be null")
    @NotEmpty(message = "The user login cannot be empty")
    @NotBlank(message = "The user login cannot be blank value")
    @Size(min = 3, max = 20, message = "The user login size must be at least 3 and at most 20")
    String bmLogin;

    @NotNull(message = "The user password cannot be null")
    @NotEmpty(message = "The user password cannot be empty")
    @NotBlank(message = "The user password cannot be blank value")
    String bmPassword;

    @NotNull(message = "The user repassword cannot be null")
    @NotEmpty(message = "The user repassword cannot be empty")
    @NotBlank(message = "The user repassword cannot be blank value")
    String bmRepassword;

    @NotNull(message = "The user name cannot be null")
    @NotEmpty(message = "The user name cannot be empty")
    @NotBlank(message = "The user name cannot be blank value")
    @Size(max = 30, message = "The user name size must have at least 30 characters")
    String bmName;

    @Size(max = 30, message = "The user surname size if is precised must have at least 30 characters")
    String bmSurname;

    @NotNull(message = "The date of birth of the user cannot be null")
    @Past(message = "The user date of birth cannot be the current date or a date in the future")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date bmDob;

    String bmCni;
    String bmPicture;
    @NotNull(message = "The user state cannot be null")
    UserBMState bmState;
    @NotNull(message = "The user type cannot be null")
    UserBMType bmUsertype;//AdminEnterprise, AdminBM, Employe
    @Valid
    AddressDto bmAddressDto;

    /******************************
     * Relation between Dto entities  *
     * ****************************/

    PointofsaleDto bmPosDto;
    /*@JsonIgnore
    List<UserBMRoleDto> userBMRoleDtoList;*/
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static UserBMDto fromEntity(UserBM userBM){
        if(userBM == null){
            return null;
        }
        return UserBMDto.builder()
                .id(userBM.getId())
                .bmLogin(userBM.getBmLogin())
                .bmPassword(userBM.getBmPassword())
                .bmRepassword(userBM.getBmPassword())
                .bmName(userBM.getBmName())
                .bmSurname(userBM.getBmSurname())
                .bmDob(userBM.getBmDob())
                .bmCni(userBM.getBmCni())
                .bmPicture(userBM.getBmPicture())
                .bmState(userBM.getBmState())
                .bmUsertype(userBM.getBmUsertype())
                .bmAddressDto(AddressDto.fromEntity(userBM.getBmAddress()))
                .bmPosDto(PointofsaleDto.fromEntity(userBM.getBmPos()))
                /*.userBMRoleDtoList(userBM.getUserBMRoleList() != null ?
                        userBM.getUserBMRoleList().stream()
                        .map(UserBMRoleDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static UserBM toEntity(UserBMDto userBMDto){
        if(userBMDto == null){
            return null;
        }
        UserBM userBM = new UserBM();
        userBM.setId(userBMDto.getId());
        userBM.setBmLogin(userBMDto.getBmLogin());
        userBM.setBmPassword(userBMDto.getBmPassword());
        userBM.setBmName(userBMDto.getBmName());
        userBM.setBmSurname(userBMDto.getBmSurname());
        userBM.setBmDob(userBMDto.getBmDob());
        userBM.setBmCni(userBMDto.getBmCni());
        userBM.setBmPicture(userBMDto.getBmPicture());
        userBM.setBmState(userBMDto.getBmState());
        userBM.setBmUsertype(userBMDto.getBmUsertype());
        userBM.setBmAddress(AddressDto.toEntity(userBMDto.getBmAddressDto()));
        userBM.setBmPos(PointofsaleDto.toEntity(userBMDto.getBmPosDto()));
        /*userBM.setUserBMRoleList(userBMDto.getUserBMRoleDtoList() != null ?
                userBMDto.getUserBMRoleDtoList().stream()
                .map(UserBMRoleDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return userBM;
    }
}
