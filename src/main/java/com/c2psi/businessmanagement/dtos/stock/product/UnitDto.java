package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Unit;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UnitDto {
    Long id;
    @NotNull(message = "The unit name cannot be null")
    @NotEmpty(message = "The unit name cannot be empty")
    @NotBlank(message = "The unit name cannot be blank")
    @Size(min = 3, max = 20, message = "The unit name size must be between 3 and 20 characters")
    String unitName;
    String unitAbbreviation;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unit for 1 Pointofsale
    @NotNull(message = "The pointofsale associated cannot be null")
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
