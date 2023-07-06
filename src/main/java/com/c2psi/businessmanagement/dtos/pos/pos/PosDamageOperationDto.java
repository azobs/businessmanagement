package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosDamageOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class PosDamageOperationDto {
    @ApiModelProperty(value = "The Id of the PosDamageOperationDto in the DB", name = "id", dataType = "Long")
    Long id;
    @Valid
    @ApiModelProperty(value = "The embedded operation", name = "posdoOperationDto", dataType = "OperationDto")
    OperationDto posdoOperationDto;
    @NotNull(message = "The number in mvt cannot be null")
    @Positive(message = "The number in mvt must be positive")
    @ApiModelProperty(value = "The number of damage in mouvement", name = "posdoNumberinmvt", dataType = "String")
    BigDecimal posdoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosDamageOperation for 1 PosDamageAccount

    @NotNull(message = "The damage account cannot be null")
    @ApiModelProperty(value = "The associated PosDamageAccountDto", name = "posdoPosDamageAccountDto", dataType = "PosDamageAccountDto")
    PosDamageAccountDto posdoPosDamageAccountDto;
    @NotNull(message = "The userbm associate with the operation cannot be null")
    @ApiModelProperty(value = "The associated UserBM", name = "posdoUserbmDto", dataType = "UserBMDto")
    UserBMDto posdoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosDamageOperationDto fromEntity(PosDamageOperation pdop){
        if(pdop == null){
            return null;
        }
        return PosDamageOperationDto.builder()
                .id(pdop.getId())
                .posdoNumberinmvt(pdop.getPosdoNumberinmvt())
                .posdoPosDamageAccountDto(PosDamageAccountDto.fromEntity(pdop.getPosdoPosDamageAccount()))
                .posdoUserbmDto(UserBMDto.fromEntity(pdop.getPosdoUserbm()))
                .posdoOperationDto(OperationDto.fromEntity(pdop.getPosdoOperation()))
                .build();
    }
    public static PosDamageOperation toEntity(PosDamageOperationDto pdopDto){
        if(pdopDto == null){
            return null;
        }
        PosDamageOperation pdop = new PosDamageOperation();
        pdop.setId(pdopDto.getId());
        pdop.setPosdoNumberinmvt(pdopDto.getPosdoNumberinmvt());
        pdop.setPosdoOperation(OperationDto.toEntity(pdopDto.getPosdoOperationDto()));
        pdop.setPosdoPosDamageAccount(PosDamageAccountDto.toEntity(pdopDto.getPosdoPosDamageAccountDto()));
        pdop.setPosdoUserbm(UserBMDto.toEntity(pdopDto.getPosdoUserbmDto()));
        return pdop;
    }
}
