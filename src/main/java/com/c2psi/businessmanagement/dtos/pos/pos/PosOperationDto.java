package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PosOperationDto {
    Long id;
    @Valid
    OperationDto posopOperationDto;
    @NotNull(message = "The user that execute the operation cannot be null")
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
