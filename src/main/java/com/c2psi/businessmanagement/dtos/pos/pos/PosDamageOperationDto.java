package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.PosDamageOperation;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class PosDamageOperationDto {
    Long id;
    @Valid
    OperationDto posdoOperationDto;
    @NotNull(message = "The number in mvt cannot be null")
    @Positive(message = "The number in mvt must be positive")
    BigDecimal posdoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosDamageOperation for 1 PosDamageAccount

    @NotNull(message = "The damage account cannot be null")
    PosDamageAccountDto posdoPosDamageAccountDto;
    @NotNull(message = "The userbm associate with the operation cannot be null")
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
