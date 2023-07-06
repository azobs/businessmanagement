package com.c2psi.businessmanagement.dtos.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.UserBM;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Builder
public class UserBMDto {
    @ApiModelProperty(value = "The Id of the UserBM in the DB", name = "id", dataType = "Long")
    Long id;

    @NotNull(message = "The user login cannot be null")
    @NotEmpty(message = "The user login cannot be empty")
    @NotBlank(message = "The user login cannot be blank value")
    @Size(min = 3, max = 75, message = "The user login size must be at least 3 and at most 75")
    @ApiModelProperty(value = "Login used for connection", name = "bmLogin", dataType = "String", example = "login",
            required = true)
    String bmLogin;

    @NotNull(message = "The user password cannot be null")
    @NotEmpty(message = "The user password cannot be empty")
    @NotBlank(message = "The user password cannot be blank value")
    @ApiModelProperty(value = "Password used for connection", name = "bmPassword", dataType = "String",
            example = "abc15ABC>com", required = true)
    String bmPassword;

    @NotNull(message = "The user repassword cannot be null")
    @NotEmpty(message = "The user repassword cannot be empty")
    @NotBlank(message = "The user repassword cannot be blank value")
    @ApiModelProperty(value = "RePassword used for connection", name = "bmRepassword", dataType = "String",
            example = "abc15ABC>com", required = true)
    String bmRepassword;

    @NotNull(message = "The user name cannot be null")
    @NotEmpty(message = "The user name cannot be empty")
    @NotBlank(message = "The user name cannot be blank value")
    @Size(max = 50, message = "The user name size must have at least 50 characters")
    @ApiModelProperty(value = "The first names of the UserBM", name = "bmName", dataType = "String", example = "name_",
            required = true)
    String bmName;

    @Size(max = 30, message = "The user surname size if is precised must have at least 30 characters")
    @ApiModelProperty(value = "The last names of the UserBM", name = "bmSurname", dataType = "String",
            example = "surname_")
    String bmSurname;

    @NotNull(message = "The date of birth of the user cannot be null")
    @Past(message = "The user date of birth cannot be the current date or a date in the future")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    //@Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "The UserBM date of birth", name = "bmDob", dataType = "Date", example = "1989-05-15",
            required = true)
    Date bmDob;

    @ApiModelProperty(value = "The cni number of the UserBM", name = "bmCni", dataType = "String", example = "107235260")
    String bmCni;
    @ApiModelProperty(value = "The image name of the UserBM", name = "bmPicture", dataType = "String", example = "tof.jpg")
    String bmPicture;
    @NotNull(message = "The user state cannot be null")
    @ApiModelProperty(value = "The state in which the userBM is", name = "bmState", dataType = "String",
            example = "Activated", required = true, allowableValues = "Activated; Deactivated; Connected; Disconnected")
    UserBMState bmState;
    @NotNull(message = "The user type cannot be null")
    @ApiModelProperty(value = "The type of the UserBM", name = "bmUsertype", dataType = "String",
            example = "AdminBM", required = true, allowableValues = "AdminBM; AdminEnterprise; Employe")
    UserBMType bmUsertype;//AdminEnterprise, AdminBM, Employe
    @Valid
    AddressDto bmAddressDto;

    /******************************
     * Relation between Dto entities  *
     * ****************************/

    //PointofsaleDto bmPosDto;
    @ApiModelProperty(value = "The id of the pointofsale if the UserBM is an employee", name = "bmPosId",
            dataType = "Long")
    Long bmPosId;
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
                //.bmPosDto(PointofsaleDto.fromEntity(userBM.getBmPos()))
                .bmPosId(userBM.getBmPosId())

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
        //userBM.setBmPos(PointofsaleDto.toEntity(userBMDto.getBmPosDto()));
        userBM.setBmPosId(userBMDto.getBmPosId());

        return userBM;
    }
}
