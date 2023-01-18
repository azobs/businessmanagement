package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ClientDamageOperation;
import com.c2psi.businessmanagement.models.UserBM;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class ClientDamageOperationDto {
    Long id;
    /*Instant cltdo_date;
    String cltdo_object;
    String cltdo_description;
    OperationType cltdo_Type;*/
    @Valid
    OperationDto cltdoOperationDto;
    @NotNull(message = "The number in mvt cannot be null")
    @Positive(message = "The number in mvt must be positive")
    Integer cltdoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientDamageOperation for 1 ClientDamageAccount
    @NotNull(message = "The client account affected cannot be null")
    ClientDamageAccountDto cltdoCltDamageAccountDto;
    @NotNull(message = "The userbm associated cannot be null")
    UserBMDto cltdoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientDamageOperationDto fromEntity(ClientDamageOperation cdop){
        if(cdop == null){
            return null;
        }
        return ClientDamageOperationDto.builder()
                .id(cdop.getId())
                .cltdoOperationDto(OperationDto.fromEntity(cdop.getCltdoOperation()))
                .cltdoNumberinmvt(cdop.getCltdoNumberinmvt())
                .cltdoCltDamageAccountDto(ClientDamageAccountDto.fromEntity(cdop.getCltdoCltDamageAccount()))
                .cltdoUserbmDto(UserBMDto.fromEntity(cdop.getCltdoUserbm()))
                .build();
    }

    public static ClientDamageOperation toEntity(ClientDamageOperationDto cdop_dto){
        if(cdop_dto == null){
            return null;
        }
        ClientDamageOperation cdop = new ClientDamageOperation();
        cdop.setId(cdop_dto.getId());
        cdop.setCltdoOperation(OperationDto.toEntity(cdop_dto.getCltdoOperationDto()));
        cdop.setCltdoNumberinmvt(cdop_dto.getCltdoNumberinmvt());
        cdop.setCltdoCltDamageAccount(ClientDamageAccountDto.toEntity(cdop_dto.getCltdoCltDamageAccountDto()));
        cdop.setCltdoUserbm(UserBMDto.toEntity(cdop_dto.getCltdoUserbmDto()));
        return cdop;
    }
}
