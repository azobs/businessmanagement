package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosCashOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class PosCashOperationDto {
    @ApiModelProperty(value = "The id of the PosCashOperationDto", name = "id", dataType = "Long")
    Long id;
    @Valid
    @ApiModelProperty(value = "The id of the Operation", name = "poscoOperationDto", dataType = "OperationDto")
    OperationDto poscoOperationDto;
    @NotNull(message = "The amount in mvt cannot be null")
    @Positive(message = "The amount in mvt cannot must be positive")
    @ApiModelProperty(value = "The amount in mouvement in the operation", name = "poscoAmountinmvt", dataType = "String")
    BigDecimal poscoAmountinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosPackagingOperation for 1 PosPackagingAccount

    @NotNull(message = "The cash account associate with the operation cannot be null")
    @ApiModelProperty(value = "The account that will be affected by the operation", name = "poscoPosCashAccountDto",
            dataType = "PosCashAccountDto")
    PosCashAccountDto poscoPosCashAccountDto;

    @NotNull(message = "The user associate with the operation cannot be null")
    @ApiModelProperty(value = "The user that launch the operation", name = "poscoUserbmDto",
            dataType = "UserBMDto")
    UserBMDto poscoUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosCashOperationDto fromEntity(PosCashOperation pcop){
        if(pcop == null){
            return null;
        }
        return PosCashOperationDto.builder()
                .id(pcop.getId())
                .poscoOperationDto(OperationDto.fromEntity(pcop.getPoscoOperation()))
                .poscoAmountinmvt(pcop.getPoscoAmountinmvt())
                .poscoPosCashAccountDto(PosCashAccountDto.fromEntity(pcop.getPoscoPosCashAccount()))
                .poscoUserbmDto(UserBMDto.fromEntity(pcop.getPoscoUserbm()))
                .build();
    }
    public static PosCashOperation toEntity(PosCashOperationDto pcopDto){
        if(pcopDto == null){
            return null;
        }
        PosCashOperation pcop = new PosCashOperation();
        pcop.setId(pcopDto.getId());
        pcop.setPoscoPosCashAccount(PosCashAccountDto.toEntity(pcopDto.getPoscoPosCashAccountDto()));
        pcop.setPoscoUserbm(UserBMDto.toEntity(pcopDto.getPoscoUserbmDto()));
        pcop.setPoscoAmountinmvt(pcopDto.getPoscoAmountinmvt());
        pcop.setPoscoOperation(OperationDto.toEntity(pcopDto.getPoscoOperationDto()));
        return pcop;
    }
}
