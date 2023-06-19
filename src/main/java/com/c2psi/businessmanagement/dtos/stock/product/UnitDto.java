package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Unit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UnitDto {
    @ApiModelProperty(value = "The unit id name of products", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The unit name cannot be null")
    @NotEmpty(message = "The unit name cannot be empty")
    @NotBlank(message = "The unit name cannot be blank")
    @Size(min = 3, max = 20, message = "The unit name size must be between 3 and 20 characters")
    @ApiModelProperty(value = "The unit name of products", name = "unitName", dataType = "String",
            example = "name")
    String unitName;
    @ApiModelProperty(value = "The unit abbreviation name of products", name = "unitAbbreviation", dataType = "String")
    String unitAbbreviation;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unit for 1 Pointofsale
    @NotNull(message = "The pointofsale associated cannot be null")
    @ApiModelProperty(value = "The posId owner of the unit", name = "unitPosId", dataType = "Long")
    Long unitPosId;
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
                //.unitPosDto(PointofsaleDto.fromEntity(unit.getUnitPos()))
                .unitPosId(unit.getUnitPosId())
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
        //unit.setUnitPos(PointofsaleDto.toEntity(unitDto.getUnitPosDto()));
        unit.setUnitPosId(unitDto.getUnitPosId());
        return unit;
    }
}
