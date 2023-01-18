package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class PosCapsuleOperationDto {
    Long id;
    @Valid
    OperationDto poscsoOperationDto;

    @NotNull(message = "The number in mouvement cannot be null")
    @Positive(message = "The number in mouvement must be positive")
    Integer poscsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosCapsuleOperation for 1 PosCapsuleAccount
    @NotNull(message = "The pos capsule account cannot be null")
    PosCapsuleAccountDto poscsoPosCapsuleAccountDto;

    @NotNull(message = "The user that execute the operation cannot be null")
    UserBMDto poscsoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosCapsuleOperationDto fromEntity(PosCapsuleOperation pcsop){
        if(pcsop == null){
            return null;
        }
        return PosCapsuleOperationDto.builder()
                .id(pcsop.getId())
                .poscsoOperationDto(OperationDto.fromEntity(pcsop.getPoscsoOperation()))
                .poscsoNumberinmvt(pcsop.getPoscsoNumberinmvt())
                .poscsoPosCapsuleAccountDto(PosCapsuleAccountDto.fromEntity(pcsop.getPoscsoPosCapsuleAccount()))
                .poscsoUserbmDto(UserBMDto.fromEntity(pcsop.getPoscsoUserbm()))
                .build();
    }
    public static PosCapsuleOperation toEntity(PosCapsuleOperationDto pcsopDto){
        if(pcsopDto == null){
            return null;
        }
        PosCapsuleOperation pcsop = new PosCapsuleOperation();
        pcsop.setId(pcsopDto.getId());
        pcsop.setPoscsoOperation(OperationDto.toEntity(pcsopDto.getPoscsoOperationDto()));
        pcsop.setPoscsoNumberinmvt(pcsopDto.getPoscsoNumberinmvt());
        pcsop.setPoscsoPosCapsuleAccount(PosCapsuleAccountDto.toEntity(pcsopDto.getPoscsoPosCapsuleAccountDto()));
        pcsop.setPoscsoUserbm(UserBMDto.toEntity(pcsopDto.getPoscsoUserbmDto()));
        return pcsop;
    }
}
