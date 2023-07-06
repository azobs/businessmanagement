package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderCapsuleOperationDto {
    @ApiModelProperty(value = "The Id of the ProviderCapsuleOperationDto in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The operation associated cannot be null")
    @ApiModelProperty(value = "The Operation data", name = "proscoOperationDto", dataType = "OperationDto")
    OperationDto proscoOperationDto;
    @NotNull(message = "The number in mvt in the operation cannot be null")
    @Positive(message = "The number in mvt in the operation must be positive")
    @ApiModelProperty(value = "The number of cover in mouvement", name = "procsoNumberinmvt", dataType = "String")
    BigDecimal procsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProviderCapsuleOperation for 1 ProviderCapsuleAccount
    @NotNull(message = "The provider account associated cannot ne null")
    @ApiModelProperty(value = "The capsule account linked with the operation", name = "procsoProCapsuleAccountDto",
            dataType = "ProviderCapsuleAccountDto")
    ProviderCapsuleAccountDto procsoProCapsuleAccountDto;
    @NotNull(message = "The userbm associated with the operation cannot be null")
    @ApiModelProperty(value = "The user that launch the operation", name = "procsoUserbmDto", dataType = "UserBMDto")
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
