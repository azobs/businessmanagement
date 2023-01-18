package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;

import java.util.List;

public interface UnitService {
    UnitDto saveUnit(UnitDto unitDto);
    UnitDto findUnit(Long unit_id);
    UnitDto findUnit(String unit_name, PointofsaleDto posDto);
    Boolean isUnitUnique(UnitDto unitDto);
    List<UnitDto> findAllUnit(PointofsaleDto posDto);
    Boolean deleteUnitById(Long unitDto);
    Boolean isUnitUsed(UnitDto unitDto);
}
