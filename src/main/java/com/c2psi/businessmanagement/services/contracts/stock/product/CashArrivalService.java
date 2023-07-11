package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface CashArrivalService {
    CashArrivalDto saveCashArrival(CashArrivalDto cashaDto);
    CashArrivalDto updateCashArrival(CashArrivalDto cashaDto);
    Boolean isCashArrivalUnique(Long articleId, Long sicashId);
    Boolean isCashArrivalDeleteable(Long cashaId);
    Boolean deleteCashArrivalById(Long cashaId);
    CashArrivalDto findCashArrivalById(Long cashaId);
    CashArrivalDto findCashArrivalofArticleinSicash(Long articleId, Long sicashId);
    List<CashArrivalDto> findAllCashArrivalinSicash(Long sicashId);
    Page<CashArrivalDto> findPageCashArrivalinSicash(Long sicashId, int pagenum, int pagesize);
    List<CashArrivalDto> findAllCashArrivalofCashArrivalTypeinSicash(CashArrivalType cashArrivalType, Long sicashId);
    Page<CashArrivalDto> findPageCashArrivalofCashArrivalTypeinSicash(CashArrivalType cashArrivalType, Long sicashId,
                                                                      int pagenum, int pagesize);
    List<CashArrivalDto> findAllCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate);
    Page<CashArrivalDto> findPageCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate,
                                                            int pagenum, int pagesize);

    List<CashArrivalDto> findAllCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType cashArrivalType, Long sicashId,
                                                                            Instant startDate, Instant endDate);
    Page<CashArrivalDto> findPageCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType cashArrivalType, Long sicashId,
                                                                            Instant startDate, Instant endDate,
                                                                             int pagenum, int pagesize);

    List<CashArrivalDto> findAllCashArrivalinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<CashArrivalDto> findPageCashArrivalinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                            int pagenum, int pagesize);

}
