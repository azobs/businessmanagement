package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ProviderDamageOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderDamageOperationDto {
    Long id;
    @NotNull(message = "The damage operation cannot be null")
    OperationDto prodoOperationDto;
    @NotNull(message = "The number in mvt in the damage operation cannot be null")
    @Positive(message = "The number in mvt in the damage operation must be positive")
    BigDecimal prodoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProviderDamageOperation for 1 ProviderDamageAccount
    @NotNull(message = "The damage account cannot be null")
    ProviderDamageAccountDto prodoProDamageAccountDto;
    @NotNull(message = "The userbm associated with the operation cannot be null")
    UserBMDto prodoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderDamageOperationDto fromEntity(ProviderDamageOperation pdop){
        if(pdop == null){
            return null;
        }
        return ProviderDamageOperationDto.builder()
                .id(pdop.getId())
                .prodoOperationDto(OperationDto.fromEntity(pdop.getProdoOperation()))
                .prodoNumberinmvt(pdop.getProdoNumberinmvt())
                .prodoProDamageAccountDto(ProviderDamageAccountDto.fromEntity(pdop.getProdoProDamageAccount()))
                .prodoUserbmDto(UserBMDto.fromEntity(pdop.getProdoUserbm()))
                .build();
    }
    public static ProviderDamageOperation toEntity(ProviderDamageOperationDto pdopDto){
        if(pdopDto == null){
            return null;
        }
        ProviderDamageOperation pdop = new ProviderDamageOperation();
        pdop.setId(pdopDto.getId());
        pdop.setProdoProDamageAccount(ProviderDamageAccountDto.toEntity(pdopDto.getProdoProDamageAccountDto()));
        pdop.setProdoUserbm(UserBMDto.toEntity(pdopDto.getProdoUserbmDto()));
        pdop.setProdoNumberinmvt(pdopDto.getProdoNumberinmvt());
        pdop.setProdoOperation(OperationDto.toEntity(pdopDto.getProdoOperationDto()));
        return pdop;
    }
}
