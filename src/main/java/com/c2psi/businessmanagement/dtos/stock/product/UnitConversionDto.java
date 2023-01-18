package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.UnitConversion;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class UnitConversionDto {
    Long id;
    @NotNull
    @Positive
    Double conversionFactor;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unitconversion for 1 unit source
    @NotNull
    UnitDto unitSourceDto;
    //Many unitconversion for 1 unit destination
    @NotNull
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
