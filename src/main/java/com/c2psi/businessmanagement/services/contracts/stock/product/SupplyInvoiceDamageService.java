package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SupplyInvoiceDamageService {
    SupplyInvoiceDamageDto saveSupplyInvoiceDamage(SupplyInvoiceDamageDto sidamDto);
    SupplyInvoiceDamageDto updateSupplyInvoiceDamage(SupplyInvoiceDamageDto sidamDto);
    Boolean isSupplyInvoiceDamageUnique(String sicapsCode, Long posId);
    Boolean isSupplyInvoiceDamageDeleteable(Long sidamId);
    Boolean deleteSupplyInvoiceDamageById(Long sidamId);
    SupplyInvoiceDamageDto findSupplyInvoiceDamageById(Long sidamId);
    SupplyInvoiceDamageDto findSupplyInvoiceDamageByCode(String sidamCode, Long posId);
    List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageBetween(Long posId, Instant startDate, Instant endDate);
    Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageBetween(Long posId, Instant startDate, Instant endDate,
                                                                      int pagenum, int pagesize);
    List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                             Instant endDate);
    Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                              Instant endDate, int pagenum, int pagesize);
    List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                               Instant endDate);
    Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                                Instant endDate, int pagenum, int pagesize);
    List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                        Long userBMId, Instant startDate,
                                                                                        Instant endDate);
    Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                         Long userBMId, Instant startDate,
                                                                                         Instant endDate, int pagenum,
                                                                                         int pagesize);
}
