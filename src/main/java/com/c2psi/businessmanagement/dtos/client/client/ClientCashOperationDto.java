package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ClientCashOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class ClientCashOperationDto {
    Long id;
    /*Instant cco_date;
    String cco_object;
    String cco_description;
    OperationType cco_Type;*/
    @Valid
    OperationDto ccoOperationDto;
    @NotNull(message = "The amount in mvt cannot be null")
    @Positive(message = "The amount in mvt must be positive")
    BigDecimal ccoAmountinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientcashOperation for 1 ClientcashAccount
    @NotNull(message = "The client associated with the operation cannot be null")
    ClientCashAccountDto ccoCltCashAccountDto;
    @NotNull(message = "The user associated with the operation cannot be null")
    UserBMDto ccoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientCashOperationDto fromEntity(ClientCashOperation ccop){
        if(ccop == null){
            return null;
        }
        return ClientCashOperationDto.builder()
                .id(ccop.getId())
                .ccoOperationDto(OperationDto.fromEntity(ccop.getCcoOperation()))
                .ccoAmountinmvt(ccop.getCcoAmountinmvt())
                .ccoCltCashAccountDto(ClientCashAccountDto.fromEntity(ccop.getCcoCltCashAccount()))
                .ccoUserbmDto(UserBMDto.fromEntity(ccop.getCcoUserbm()))
                .build();
    }
    public static ClientCashOperation toEntity(ClientCashOperationDto ccop_dto){
        if(ccop_dto == null){
            return null;
        }
        ClientCashOperation ccop = new ClientCashOperation();
        ccop.setId(ccop_dto.getId());
        ccop.setCcoOperation(OperationDto.toEntity(ccop_dto.getCcoOperationDto()));
        ccop.setCcoAmountinmvt(ccop_dto.getCcoAmountinmvt());
        ccop.setCcoCltCashAccount(ClientCashAccountDto.toEntity(ccop_dto.getCcoCltCashAccountDto()));
        ccop.setCcoUserbm(UserBMDto.toEntity(ccop_dto.getCcoUserbmDto()));
        return ccop;
    }
}
