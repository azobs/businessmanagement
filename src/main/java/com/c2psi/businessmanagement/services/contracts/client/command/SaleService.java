package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface SaleService {
    SaleDto saveSale(SaleDto saleDto);
    SaleDto findSaleById(Long saleId);
    Boolean deleteSaleById(Long saleId);
    List<SaleDto> findAllSale(ArticleDto artDto);
    List<SaleDto> findAllSale(CommandDto cmdDto);
    SaleDto findSaleInCommand(CommandDto cmdDto, ArticleDto artDto);
    Boolean isSaleUniqueInCommand(CommandDto cmdDto, ArticleDto artDto);
}
