package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.UnitConversion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class UnitConversionDto {
    @ApiModelProperty(value = "The id of the conversion rule between unit", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The conversion factor cannot be null")
    @Positive(message = "The conversion factor must be positive")
    @ApiModelProperty(value = "The conversion factor", name = "conversionFactor", dataType = "BigDecimal")
    BigDecimal conversionFactor;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unitconversion for 1 unit source
    @NotNull(message = "The source unit(conditionnement) cannot be null")
    @ApiModelProperty(value = "The unit from which to convert", name = "unitSourceDto", dataType = "UnitDto")
    UnitDto unitSourceDto;
    //Many unitconversion for 1 unit destination
    @NotNull(message = "The destination unit(conditionnement) cannot be null")
    @ApiModelProperty(value = "The unit to which to convert", name = "unitDestinationDto", dataType = "UnitDto")
    UnitDto unitDestinationDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static UnitConversionDto fromEntity(UnitConversion uc){
        if(uc == null){
            return null;
        }
        return UnitConversionDto.builder()
                .id(uc.getId())
                .conversionFactor(uc.getConversionFactor())
                .unitDestinationDto(UnitDto.fromEntity(uc.getUnitDestination()))
                .unitSourceDto(UnitDto.fromEntity(uc.getUnitSource()))
                .build();
    }
    public static UnitConversion toEntity(UnitConversionDto ucDto){
        if(ucDto ==  null){
            return null;
        }
        UnitConversion uc = new UnitConversion();
        uc.setConversionFactor(ucDto.getConversionFactor());
        uc.setUnitDestination(UnitDto.toEntity(ucDto.getUnitDestinationDto()));
        uc.setUnitSource(UnitDto.toEntity(ucDto.getUnitSourceDto()));
        return uc;
    }
}
