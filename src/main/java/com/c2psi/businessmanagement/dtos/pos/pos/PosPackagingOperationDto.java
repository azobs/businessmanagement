package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosPackagingOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class PosPackagingOperationDto {
    Long id;
    @Valid
    OperationDto pospoOperationDto;
    @NotNull(message = "The number in mvt cannot be null")
    @Positive(message = "The number in mvt must be positive")
    BigDecimal pospoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosPackagingOperation for 1 PosPackagingAccount

    @NotNull(message = "The packaging account associated cannot be null")
    PosPackagingAccountDto pospoPosPackagingAccountDto;

    @NotNull(message = "The userbm associate with the operation cannot be null")
    UserBMDto pospoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosPackagingOperationDto fromEntity(PosPackagingOperation ppop){
        if(ppop == null){
            return null;
        }
        return PosPackagingOperationDto.builder()
                .id(ppop.getId())
                .pospoOperationDto(OperationDto.fromEntity(ppop.getPospoOperation()))
                .pospoNumberinmvt(ppop.getPospoNumberinmvt())
                .pospoPosPackagingAccountDto(PosPackagingAccountDto.fromEntity(ppop.getPospoPosPackagingAccount()))
                .pospoUserbmDto(UserBMDto.fromEntity(ppop.getPospoUserbm()))
                .build();
    }
    public static PosPackagingOperation toEntity(PosPackagingOperationDto ppopDto){
        if(ppopDto == null){
            return null;
        }
        PosPackagingOperation ppop = new PosPackagingOperation();
        ppop.setId(ppopDto.getId());
        ppop.setPospoNumberinmvt(ppopDto.getPospoNumberinmvt());
        ppop.setPospoPosPackagingAccount(PosPackagingAccountDto.toEntity(ppopDto.getPospoPosPackagingAccountDto()));
        ppop.setPospoUserbm(UserBMDto.toEntity(ppopDto.getPospoUserbmDto()));
        ppop.setPospoOperation(OperationDto.toEntity(ppopDto.getPospoOperationDto()));
        return ppop;
    }
}
