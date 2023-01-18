package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ClientPackagingOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class ClientPackagingOperationDto {
    Long id;
    /*Instant cltpoDate;
    String cltpoObject;
    String cltpoDescription;
    OperationType cltpo_Type;*/
    @Valid
    OperationDto cltpoOperationDto;
    @NotNull(message = "The number in mvt cannot be null")
    @Positive(message = "The number in mvt must be positive")
    Integer cltpoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientpackagingOperation for 1 ClientPackagingAccount

    @NotNull(message = "The client associated with the operation cannot be null")
    ClientPackagingAccountDto cltpoCltPackagingAccountDto;

    @NotNull(message = "The userbm who save the operation cannot be null")
    UserBMDto cltpoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientPackagingOperationDto fromEntity(ClientPackagingOperation cpop){
        if(cpop == null){
            return null;
        }
        return ClientPackagingOperationDto.builder()
                .id(cpop.getId())
                .cltpoOperationDto(OperationDto.fromEntity(cpop.getCltpoOperation()))
                .cltpoNumberinmvt(cpop.getCltpoNumberinmvt())
                .cltpoCltPackagingAccountDto(ClientPackagingAccountDto.fromEntity(cpop.getCltpoCltPackagingAccount()))
                .cltpoUserbmDto(UserBMDto.fromEntity(cpop.getCltpoUserbm()))
                .build();
    }
    public static ClientPackagingOperation toEntity(ClientPackagingOperationDto cpopDto){
        if(cpopDto == null){
            return null;
        }
        ClientPackagingOperation cpop = new ClientPackagingOperation();
        cpop.setId(cpopDto.getId());
        cpop.setCltpoOperation(OperationDto.toEntity(cpopDto.getCltpoOperationDto()));
        cpop.setCltpoNumberinmvt(cpopDto.getCltpoNumberinmvt());
        cpop.setCltpoCltPackagingAccount(ClientPackagingAccountDto.toEntity(cpopDto.getCltpoCltPackagingAccountDto()));
        cpop.setCltpoUserbm(UserBMDto.toEntity(cpopDto.getCltpoUserbmDto()));
        return cpop;
    }
}
