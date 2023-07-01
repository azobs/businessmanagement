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

    List<ProviderCashOperationDto> findAllProviderCashOperation(Long procaId);
    Page<ProviderCashOperationDto> findPageProviderCashOperation(Long procaId, int pagenum, int pagesize);
    List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Long procaId, Instant startDate, Instant endDate);
    Page<ProviderCashOperationDto> findPageProviderCashOperationBetween(Long procaId, Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize);
    List<ProviderCashOperationDto> findAllProviderCashOperationofTypeBetween(Long procaId, OperationType opType,
                                                                             Instant startDate, Instant endDate);
    Page<ProviderCashOperationDto> findPageProviderCashOperationofTypeBetween(Long procaId, OperationType opType,
                                                                              Instant startDate, Instant endDate,
                                                                              int pagenum, int pagesize);
}
