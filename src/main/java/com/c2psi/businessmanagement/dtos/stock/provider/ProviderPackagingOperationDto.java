package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ProviderPackagingOperation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderPackagingOperationDto {
    Long id;
    OperationDto propoOperationDto;
    Integer propoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientpackagingOperation for 1 ClientPackagingAccount

    ProviderPackagingAccountDto propoProPackagingAccountDto;

    UserBMDto propoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderPackagingOperationDto fromEntity(ProviderPackagingOperation ppop){
        if(ppop == null){
            return null;
        }
        return ProviderPackagingOperationDto.builder()
                .id(ppop.getId())
                .propoOperationDto(OperationDto.fromEntity(ppop.getPropoOperation()))
                .propoProPackagingAccountDto(ProviderPackagingAccountDto.fromEntity(ppop.getPropoProPackagingAccount()))
                .propoUserbmDto(UserBMDto.fromEntity(ppop.getPropoUserbm()))
                .propoNumberinmvt(ppop.getPropoNumberinmvt())
                .build();
    }
    public static ProviderPackagingOperation toEntity(ProviderPackagingOperationDto ppopDto){
        if(ppopDto == null){
            return null;
        }
        ProviderPackagingOperation ppop = new ProviderPackagingOperation();
        ppop.setId(ppopDto.getId());
        ppop.setPropoNumberinmvt(ppopDto.getPropoNumberinmvt());
        ppop.setPropoProPackagingAccount(ProviderPackagingAccountDto.toEntity(ppopDto.getPropoProPackagingAccountDto()));
        ppop.setPropoUserbm(UserBMDto.toEntity(ppopDto.getPropoUserbmDto()));
        ppop.setPropoOperation(OperationDto.toEntity(ppopDto.getPropoOperationDto()));
        return ppop;
    }
}
