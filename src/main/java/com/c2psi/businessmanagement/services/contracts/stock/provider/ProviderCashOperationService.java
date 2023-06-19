package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ProviderCashOperationService {
    ProviderCashOperationDto updateProviderCashOperation(ProviderCashOperationDto procopDto);
    Boolean isProviderCashOperationDeleatable(Long procopDto);
    Boolean deleteProviderCashOperationById(Long procopId);
    ProviderCashOperationDto findProviderCashOperationById(Long procopId);

    List<ProviderCashOperationDto> findAllProviderCashOperation(Long procopId);
    Page<ProviderCashOperationDto> findPageProviderCashOperation(Long procopId, int pagenum, int pagesize);
    List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Long procopId, Instant startDate, Instant endDate);
    Page<ProviderCashOperationDto> findPageProviderCashOperationBetween(Long procopId, Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize);
    List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Long procopId, Instant startDate, Instant endDate,
                                                                  OperationType opType);
    Page<ProviderCashOperationDto> findPageProviderCashOperationBetween(Long procopId, Instant startDate, Instant endDate,
                                                                       OperationType opType, int pagenum, int pagesize);
}
