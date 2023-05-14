package com.c2psi.businessmanagement.services.contracts.stock.product;


import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SupplyInvoiceCapsuleService {
    SupplyInvoiceCapsuleDto saveSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto);
    SupplyInvoiceCapsuleDto updateSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto);
    Boolean isSupplyInvoiceCapsuleDeleteable(Long sicapsId);
    Boolean deleteSupplyInvoiceCapsuleById(Long sicapsId);
    SupplyInvoiceCapsuleDto findSupplyInvoiceCapsuleById(Long sicapsId);
    SupplyInvoiceCapsuleDto findSupplyInvoiceCapsuleByCode(String sicapsCode, Long posId);
    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleBetween(Long posId, Instant startDate, Instant endDate);
    Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleBetween(Long posId, Instant startDate, Instant endDate,
                                                                int pagenum, int pagesize);

    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                       Instant endDate);
    Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                        Instant endDate, int pagenum, int pagesize);
    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                         Instant endDate);
    Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                          Instant endDate, int pagenum, int pagesize);

    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                  Long userBMId, Instant startDate,
                                                                                  Instant endDate);
    Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                   Long userBMId, Instant startDate,
                                                                                   Instant endDate, int pagenum,
                                                                                   int pagesize);
    Boolean isSupplyInvoiceCapsuleUnique(String sicapsCode, Long posId);
}
