package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;

import java.math.BigDecimal;
import java.util.List;

public interface UnitConversionService {
    UnitConversionDto saveUnitConversion(UnitConversionDto unitconvDto);
    UnitConversionDto updateUnitConversion(UnitConversionDto unitconvDto);
    BigDecimal convertTo(BigDecimal nbToConvert, Long unitSourceId, Long unitDestinationId);
    Boolean isUnitConversionUnique(Long unitSourceId, Long unitDestinationId);
    UnitConversionDto findUnitConversionById(Long unitconvId);
    UnitConversionDto findConversionRuleBetween(Long unitSourceId, Long unitDestinationId);
    List<UnitDto> listofConvertibleUnitWith(Long unitSourceId);
    Boolean isUnitConversionDeleteable(Long unitconvId);
    Boolean deleteUnitConversionById(Long unitconvId);

}
