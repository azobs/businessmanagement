package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryLineService {
    InventoryLineDto saveInventoryLine(InventoryLineDto invlineDto);
    InventoryLineDto updateInventoryLine(InventoryLineDto invlineDto);
    Boolean isInventoryLineUniqueinInv(Long invId, Long artId);
    InventoryLineDto findInventoryLineById(Long invlineId);
    InventoryLineDto findInventoryLineByArticleinInv(Long invId, Long articleId);
    Boolean isInventoryLineDeleteable(Long invLineId);
    Boolean deleteInventoryLineById(Long invlineId);
    List<InventoryLineDto> findAllInventorylineofInv(Long invId);
    Page<InventoryLineDto> findPageInventorylineofInv(Long invId, int pagenum, int pagesize);
}
