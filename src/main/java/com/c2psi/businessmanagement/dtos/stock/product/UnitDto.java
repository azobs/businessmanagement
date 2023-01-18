package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Unit;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UnitDto {
    Long id;
    @NotNull
    @NotEmpty
    String unitName;
    String unitAbbreviation;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unit for 1 Pointofsale
    @NotNull
    PointofsaleDto unitPosDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static UnitDto fromEntity(Unit unit){
        if(unit == null){
            return null;
        }
        return UnitDto.builder()
                .id(unit.getId())
                .unitAbbreviation(unit.getUnitAbbreviation())
                .unitName(unit.getUnitName())
                .unitPosDto(PointofsaleDto.fromEntity(unit.getUnitPos()))
                .build();
    }
    public static Unit toEntity(UnitDto unitDto){
        if(unitDto == null){
            return null;
        }
        Unit unit = new Unit();
        unit.setId(unitDto.getId());
        unit.setUnitAbbreviation(unitDto.getUnitAbbreviation());
        unit.setUnitName(unitDto.getUnitName());
        unit.setUnitPos(PointofsaleDto.toEntity(unitDto.getUnitPosDto()));
        return unit;
    }
}
