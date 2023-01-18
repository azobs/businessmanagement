package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

import java.util.Date;
import java.util.List;

public interface SaleInvoiceCashService {
    SaleInvoiceCashDto saveSaleInvoiceCash(SaleInvoiceCashDto saleicashDto);
    Boolean deleteSaleInvoiceCashById(Long saleicashDto);
    SaleInvoiceCashDto findSaleInvoiceCashById(Long saleicash_id);
    SaleInvoiceCashDto findSaleInvoiceCashByCode(String saleicash_code,
                                                 PointofsaleDto posDto);
    List<SaleInvoiceCashDto> findAllSaleInvoiceCashBetween(PointofsaleDto posDto,
                                                    Date startDate, Date endDate);
    List<SaleInvoiceCashDto> findAllSaleInvoiceCashBetween(PointofsaleDto posDto,
                                                           ClientDto cltDto,
                                                           Date startDate,
                                                           Date endDate);

    List<SaleInvoiceCashDto> findAllSaleInvoiceCashBetween(PointofsaleDto posDto,
                                                           UserBMDto userbmDto,
                                                           Date startDate,
                                                           Date endDate);
    List<SaleInvoiceCashDto> findAllSaleInvoiceCashBetween(PointofsaleDto posDto,
                                                           ClientDto cltDto,
                                                           UserBMDto userbmDto,
                                                           Date startDate,
                                                           Date endDate);
}
