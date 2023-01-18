package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

import java.util.Date;
import java.util.List;

public interface SaleInvoiceCapsuleService {
    SaleInvoiceCapsuleDto saveSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleicapsDto);
    Boolean deleteSaleInvoiceCapsuleById(Long saleicapsDto);
    SaleInvoiceCapsuleDto findSaleInvoiceCapsuleById(Long saleicapsId);
    SaleInvoiceCapsuleDto findSaleInvoiceCapsuleByCode(String saleicaps_code,
                                                 PointofsaleDto posDto);
    List<SaleInvoiceCapsuleDto> findAllSaleInvoiceCapsuleBetween(
            PointofsaleDto posDto, Date startDate, Date endDate);

    List<SaleInvoiceCapsuleDto> findAllSaleInvoiceCapsuleBetween(
            PointofsaleDto posDto, ClientDto cltDto, Date startDate, Date endDate);

    List<SaleInvoiceCapsuleDto> findAllSaleInvoiceCapsuleBetween(
            PointofsaleDto posDto, UserBMDto userbmDto, Date startDate, Date endDate);
    List<SaleInvoiceCapsuleDto> findAllSaleInvoiceCapsuleBetween(
            PointofsaleDto posDto, ClientDto cltDto, UserBMDto userbmDto,
            Date startDate, Date endDate);
}
