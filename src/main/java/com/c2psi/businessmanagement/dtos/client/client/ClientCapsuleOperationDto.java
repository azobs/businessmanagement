package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ClientCapsuleOperation;
import com.c2psi.businessmanagement.models.UserBM;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class ClientCapsuleOperationDto {
    Long id;
    /*Instant cltcso_date;
    String cltcso_object;
    String cltcso_description;
    OperationType cltcso_Type;*/
    @Valid
    OperationDto cltcsoOperationDto;
    @NotNull(message = "The number in mvt in the operation cannot be null")
    @Positive(message = "The number in mvt in the operation must be positive")
    BigDecimal cltcsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientCapsuleOperation for 1 ClientCapsuleAccount
    @NotNull(message = "The client concerned by the operation cannot be null")
    ClientCapsuleAccountDto cltcsoCltCapsuleAccountDto;
    @NotNull(message = "The user who make the operation cannot be null")
    UserBMDto cltcsoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientCapsuleOperationDto fromEntity(ClientCapsuleOperation ccsop){
        if(ccsop == null){
            return null;
        }
        return ClientCapsuleOperationDto.builder()
                .id(ccsop.getId())
                .cltcsoOperationDto(OperationDto.fromEntity(ccsop.getCltcsoOperation()))
                .cltcsoNumberinmvt(ccsop.getCltcsoNumberinmvt())
                .cltcsoCltCapsuleAccountDto(ClientCapsuleAccountDto.fromEntity(ccsop.getCltcsoCltCapsuleAccount()))
                .cltcsoUserbmDto(UserBMDto.fromEntity(ccsop.getCltcsoUserbm()))
                .build();
    }

    public static ClientCapsuleOperation toEntity(ClientCapsuleOperationDto ccsop_dto){
        if(ccsop_dto == null){
            return null;
        }
        ClientCapsuleOperation ccsop = new ClientCapsuleOperation();
        ccsop.setId(ccsop_dto.getId());
        ccsop.setCltcsoOperation(OperationDto.toEntity(ccsop_dto.getCltcsoOperationDto()));
        ccsop.setCltcsoNumberinmvt(ccsop_dto.getCltcsoNumberinmvt());
        ccsop.setCltcsoCltCapsuleAccount(ClientCapsuleAccountDto.toEntity(ccsop_dto.getCltcsoCltCapsuleAccountDto()));
        ccsop.setCltcsoUserbm(UserBMDto.toEntity(ccsop_dto.getCltcsoUserbmDto()));
        return ccsop;
    }
}
