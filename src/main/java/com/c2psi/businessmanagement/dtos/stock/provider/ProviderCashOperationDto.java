package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ProviderCashOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderCashOperationDto {
    @ApiModelProperty(value = "The Id of the ProviderCashOperationDto in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The cash operation cannot be null")
    @ApiModelProperty(value = "The Operation ", name = "pcoOperationDto", dataType = "OperationDto")
    OperationDto pcoOperationDto;
    @NotNull(message = "The amount in mvt in the operation cannot be null")
    @Positive(message = "The amount in mvt in the operation must be positive")
    @ApiModelProperty(value = "The amount in mouvement in the operation ", name = "pcoAmountinmvt", dataType = "String")
    BigDecimal pcoAmountinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProvidercashOperation for 1 ProvidercashAccount
    @NotNull(message = "The provider cash account associated cannot be null")
    @ApiModelProperty(value = "The cash account link with the operation ", name = "pcoProCashAccountDto", dataType = "ProviderCashAccountDto")
    ProviderCashAccountDto pcoProCashAccountDto;
    @NotNull(message = "The userbm associated with the operation cannot be null")
    @ApiModelProperty(value = "The userbm that launch the operation ", name = "pcoUserbmDto", dataType = "UserBMDto")
    UserBMDto pcoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderCashOperationDto fromEntity(ProviderCashOperation pcop){
        if(pcop == null){
            return null;
        }
        return ProviderCashOperationDto.builder()
                .id(pcop.getId())
                .pcoOperationDto(OperationDto.fromEntity(pcop.getPcoOperation()))
                .pcoAmountinmvt(pcop.getPcoAmountinmvt())
                .pcoProCashAccountDto(ProviderCashAccountDto.fromEntity(pcop.getPcoProCashAccount()))
                .pcoUserbmDto(UserBMDto.fromEntity(pcop.getPcoUserbm()))
                .build();
    }
    public static ProviderCashOperation toEntity(ProviderCashOperationDto pcopDto){
        if(pcopDto == null){
            return null;
        }
        ProviderCashOperation pcop = new ProviderCashOperation();
        pcop.setId(pcopDto.getId());
        pcop.setPcoAmountinmvt(pcop.getPcoAmountinmvt());
        pcop.setPcoOperation(OperationDto.toEntity(pcopDto.getPcoOperationDto()));
        pcop.setPcoProCashAccount(ProviderCashAccountDto.toEntity(pcopDto.getPcoProCashAccountDto()));
        pcop.setPcoUserbm(UserBMDto.toEntity(pcopDto.getPcoUserbmDto()));
        return pcop;
    }
}
