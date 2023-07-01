package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SaleService {
    SaleDto saveSale(SaleDto saleDto);
    SaleDto updateSale(SaleDto saleDto);
    SaleDto findSaleById(Long saleId);
    Boolean isSaleUniqueInCommand(Long cmdId, Long artId);
    Boolean isSaleDeleteableInCommand(Long saleId);
    SaleDto findSaleinCommandaboutArticle(Long cmdId, Long artId);
    Boolean deleteSaleById(Long saleId);
    List<SaleDto> findAllSaleofCommand(Long cmdId);
    Page<SaleDto> findPageSaleofCommand(Long cmdId, int pagenum, int pagesize);
    List<SaleDto> findAllSaleonArticle(Long artId);
    Page<SaleDto> findPageSaleonArticle(Long artId, int pagenum, int pagesize);
    List<SaleDto> findAllSaleonArticleBetween(Long artId, Instant startDate, Instant endDate);
    Page<SaleDto> findPageSaleonArticleBetween(Long artId, Instant startDate, Instant endDate, int pagenum, int pagesize);
}
