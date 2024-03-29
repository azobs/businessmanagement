package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PosOperationDto {
    @ApiModelProperty(value = "The id of PosOperationDto", name = "id", dataType = "Long")
    Long id;
    @Valid
    @ApiModelProperty(value = "The embedded operation", name = "posopOperationDto", dataType = "OperationDto")
    OperationDto posopOperationDto;
    @NotNull(message = "The user that execute the operation cannot be null")
    @ApiModelProperty(value = "The userBM associated", name = "posopUserbmDto", dataType = "UserBMDto")
    UserBMDto posopUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosOperationDto fromEntity(PosOperation posop){
        if(posop == null){
            return null;
        }
        return PosOperationDto.builder()
                .id(posop.getId())
                .posopOperationDto(OperationDto.fromEntity(posop.getPosopOperation()))
                .posopUserbmDto(UserBMDto.fromEntity(posop.getPosopUserbm()))
                .build();
    }

    public static PosOperation toEntity(PosOperationDto posopDto){
        if(posopDto == null){
            return null;
        }
        PosOperation posop = new PosOperation();
        posop.setId(posopDto.getId());
        posop.setPosopOperation(OperationDto.toEntity(posopDto.getPosopOperationDto()));
        posop.setPosopUserbm(UserBMDto.toEntity(posopDto.getPosopUserbmDto()));

        return posop;
    }
}
