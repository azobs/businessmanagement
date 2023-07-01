package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface InventoryService {
    InventoryDto saveInventory(InventoryDto invDto);
    InventoryDto updateInventory(InventoryDto invDto);
    Boolean isInventoryUniqueinPos(String invCode, Long posId);
    InventoryDto findInventoryById(Long invId);
    InventoryDto findInventoryByCodeinPos(String invCode, Long posId);
    List<InventoryDto> findAllInventoryinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<InventoryDto> findPageInventoryinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                     int pagenum, int pagesize);
    Boolean isInventoryDeleteable(Long invId);
    Boolean deleteInventoryById(Long invId);
}
