package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;

import java.util.List;

public interface CashArrivalService {
    CashArrivalDto saveCashArrival(CashArrivalDto cashaDto);
    Boolean deleteCashArrivalById(Long cashaDto);
    CashArrivalDto findCashArrivalById(Long cashaDto);
    List<CashArrivalDto> findAllCashArrival(SupplyInvoiceCashDto sicashDto);
}
