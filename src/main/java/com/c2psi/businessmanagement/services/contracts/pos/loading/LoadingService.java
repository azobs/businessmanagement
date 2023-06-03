package com.c2psi.businessmanagement.services.contracts.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface LoadingService {
    LoadingDto saveLoading(LoadingDto loadingDto);
    LoadingDto updateLoading(LoadingDto loadingDto);
    LoadingDto findLoadingById(Long loadingId);
    Boolean isLoadingUniqueinPos(String loadingCode, Long posId);
    LoadingDto findLoadingByCodeinPos(String loadingCode, Long posId);
    List<LoadingDto> findAllLoadinginPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<LoadingDto> findPageLoadinginPosBetween(Long posId, Instant startDate, Instant endDate, int pagenum, int pagesize);

    List<LoadingDto> findAllLoadingofUserbmManagerinPosBetween(Long posId, Long userbmManagerId,
                                                               Instant startDate, Instant endDate);

    Page<LoadingDto> findPageLoadingofUserbmManagerinPosBetween(Long posId, Long userbmManagerId,
                                                               Instant startDate, Instant endDate, int pagenum, int pagesize);

    List<LoadingDto> findAllLoadingofUserbmSalerinPosBetween(Long posId, Long userbmSalerId,
                                                               Instant startDate, Instant endDate);

    Page<LoadingDto> findPageLoadingofUserbmSalerinPosBetween(Long posId, Long userbmSalerId,
                                                             Instant startDate, Instant endDate, int pagenum, int pagesize);

    List<LoadingDto> findAllLoadingofUserbmManagerandSalerinPosBetween(Long posId, Long userbmManagerId, Long userbmSalerId,
                                                                       Instant startDate, Instant endDate);
    Page<LoadingDto> findPageLoadingofUserbmManagerandSalerinPosBetween(Long posId, Long userbmManagerId, Long userbmSalerId,
                                                                       Instant startDate, Instant endDate, int pagenum, int pagesize);
    Boolean isLoadingDeleteableById(Long loadingId);
    Boolean deleteLoadingById(Long loadingId);
}
