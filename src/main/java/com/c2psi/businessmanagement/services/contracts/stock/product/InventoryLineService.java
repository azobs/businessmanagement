package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;

public interface InventoryLineService {
    InventoryLineDto saveInventoryLine(InventoryLineDto invlineDto);
    Boolean deleteInventoryLineById(Long id);
}
