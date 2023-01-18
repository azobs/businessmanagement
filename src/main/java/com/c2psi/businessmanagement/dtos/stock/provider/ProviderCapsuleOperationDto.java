package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderCapsuleOperationDto {
    Long id;
    OperationDto proscoOperationDto;
    Integer procsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProviderCapsuleOperation for 1 ProviderCapsuleAccount

    ProviderCapsuleAccountDto procsoProCapsuleAccountDto;

    UserBMDto procsoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderCapsuleOperationDto fromEntity(ProviderCapsuleOperation pcop){
        if(pcop == null){
            return null;
        }
        return ProviderCapsuleOperationDto.builder()
                .id(pcop.getId())
                .proscoOperationDto(OperationDto.fromEntity(pcop.getProscoOperation()))
                .procsoNumberinmvt(pcop.getProcsoNumberinmvt())
                .procsoProCapsuleAccountDto(ProviderCapsuleAccountDto.fromEntity(pcop.getProcsoProCapsuleAccount()))
                .procsoUserbmDto(UserBMDto.fromEntity(pcop.getProcsoUserbm()))
                .build();
    }

    public static ProviderCapsuleOperation toEntity(ProviderCapsuleOperationDto pcopDto){
        if(pcopDto == null){
            return null;
        }
        ProviderCapsuleOperation pcop = new ProviderCapsuleOperation();
        pcop.setProcsoProCapsuleAccount(ProviderCapsuleAccountDto.toEntity(pcopDto.getProcsoProCapsuleAccountDto()));
        pcop.setProcsoUserbm(UserBMDto.toEntity(pcopDto.getProcsoUserbmDto()));
        pcop.setProcsoNumberinmvt(pcopDto.getProcsoNumberinmvt());
        pcop.setProscoOperation(OperationDto.toEntity(pcopDto.getProscoOperationDto()));
        pcop.setId(pcopDto.getId());
        return pcop;
    }
}
