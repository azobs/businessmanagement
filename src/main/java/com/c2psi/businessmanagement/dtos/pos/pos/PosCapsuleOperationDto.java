package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class PosCapsuleOperationDto {
    @ApiModelProperty(value = "The Id of the PosCapsuleOperationDto in the DB", name = "id", dataType = "Long")
    Long id;
    @Valid
    @ApiModelProperty(value = "The embedded operation", name = "poscsoOperationDto", dataType = "OperationDto")
    OperationDto poscsoOperationDto;

    @NotNull(message = "The number in mouvement cannot be null")
    @Positive(message = "The number in mouvement must be positive")
    @ApiModelProperty(value = "The number of capsule in mouvement", name = "poscsoNumberinmvt", dataType = "BigDecimal")
    BigDecimal poscsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosCapsuleOperation for 1 PosCapsuleAccount
    @NotNull(message = "The pos capsule account cannot be null")
    @ApiModelProperty(value = "The associated PosCapsuleAccountDto", name = "poscsoPosCapsuleAccountDto", dataType = "PosCapsuleAccountDto")
    PosCapsuleAccountDto poscsoPosCapsuleAccountDto;

    @NotNull(message = "The user that execute the operation cannot be null")
    @ApiModelProperty(value = "The associated UserBM", name = "poscsoUserbmDto", dataType = "UserBMDto")
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
