package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SupplyInvoiceCashService {
    SupplyInvoiceCashDto saveSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto);
    SupplyInvoiceCashDto updateSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto);
    Boolean isSupplyInvoiceCashDeleteable(Long sicashId);
    Boolean deleteSupplyInvoiceCashById(Long sicashId);
    SupplyInvoiceCashDto findSupplyInvoiceCashById(Long sicashId);
    SupplyInvoiceCashDto findSupplyInvoiceCashByCode(String sicashCode, Long posId);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(Long posId, Instant startDate, Instant endDate);
    Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashBetween(Long posId, Instant startDate, Instant endDate,
                                                               int pagenum, int pagesize);

    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                       Instant endDate);
    Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                        Instant endDate, int pagenum, int pagesize);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                         Instant endDate);
    Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                          Instant endDate, int pagenum, int pagesize);

    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                  Long userBMId, Instant startDate,
                                                                                  Instant endDate);
    Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                   Long userBMId, Instant startDate,
                                                                                   Instant endDate, int pagenum,
                                                                                   int pagesize);
    Boolean isSupplyInvoiceCashUnique(String sicashCode, Long posId);
}
