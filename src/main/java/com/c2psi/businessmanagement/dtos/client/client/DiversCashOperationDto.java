package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.DiversCashOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class DiversCashOperationDto {
    Long id;
    @Valid
    OperationDto dcoOperationDto;
    @NotNull(message = "The amount in mvt cannot be null")
    @Positive(message = "The amount in mvt must be positive")
    BigDecimal dcoAmountinmvt;
    @NotNull(message = "The client associated with the operation cannot be null")
    DiversCashAccountDto dcoDiversCashAccountDto;
    @NotNull(message = "The user associated with the operation cannot be null")
    UserBMDto dcoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static DiversCashOperationDto fromEntity(DiversCashOperation dcop){
        if(dcop == null){
            return null;
        }
        return DiversCashOperationDto.builder()
                .id(dcop.getId())
                .dcoOperationDto(OperationDto.fromEntity(dcop.getDcoOperation()))
                .dcoAmountinmvt(dcop.getDcoAmountinmvt())
                .dcoDiversCashAccountDto(DiversCashAccountDto.fromEntity(dcop.getDcoDiversCashAccount()))
                .dcoUserbmDto(UserBMDto.fromEntity(dcop.getDcoUserbm()))
                .build();
    }

    public static DiversCashOperation toEntity(DiversCashOperationDto dcop_dto){
        if(dcop_dto == null){
            return null;
        }
        DiversCashOperation dcop = new DiversCashOperation();
        dcop.setId(dcop_dto.getId());
        dcop.setDcoOperation(OperationDto.toEntity(dcop_dto.getDcoOperationDto()));
        dcop.setDcoAmountinmvt(dcop_dto.getDcoAmountinmvt());
        dcop.setDcoDiversCashAccount(DiversCashAccountDto.toEntity(dcop_dto.getDcoDiversCashAccountDto()));
        dcop.setDcoUserbm(UserBMDto.toEntity(dcop_dto.getDcoUserbmDto()));
        return dcop;
    }
}
