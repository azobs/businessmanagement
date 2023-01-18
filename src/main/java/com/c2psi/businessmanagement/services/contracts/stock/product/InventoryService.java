package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;

import java.util.Date;
import java.util.List;

public interface InventoryService {
    InventoryDto saveInventory(InventoryDto invDto);

    InventoryDto findInventoryByCode(String inv_code, PointofsaleDto posDto);

    List<InventoryDto> findAllInventoryBetween(Date startDate, Date endDate);

    Boolean deleteInventoryById(Long id);
}
