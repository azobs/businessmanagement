package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UnitService {
    UnitDto saveUnit(UnitDto unitDto);
    UnitDto updateUnit(UnitDto unitDto);
    UnitDto findUnitById(Long unitId);
    UnitDto findUnitByUnitnameAndPos(String unitName, Long posId);
    Boolean isUnitUniqueInPos(String unitName, Long posId);
    List<UnitDto> findAllUnitInPos(Long posId);
    Page<UnitDto> findPageUnitInPos(Long posId, int pagenum, int pagesize);
    Boolean deleteUnitById(Long unitId);
    Boolean isUnitDeleteable(Long unitId);
}
