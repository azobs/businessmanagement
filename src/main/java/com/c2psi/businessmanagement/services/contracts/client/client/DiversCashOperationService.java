package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface DiversCashOperationService {
    DiversCashOperationDto updateDiversCashOperation(DiversCashOperationDto dcaopDto);
    Boolean isDiversCashOperationDeleatable(Long dcaopDto);
    Boolean deleteDiversCashOperationById(Long dcaopId);
    DiversCashOperationDto findDiversCashOperationById(Long dcaopId);
    List<DiversCashOperationDto> findAllDiversCashOperation(Long dcaopId);
    Page<DiversCashOperationDto> findPageDiversCashOperation(Long dcaopId, int pagenum, int pagesize);
    List<DiversCashOperationDto> findAllDiversCashOperationBetween(Long dcaopId, Instant startDate, Instant endDate);
    Page<DiversCashOperationDto> findPageDiversCashOperationBetween(Long dcaopId, Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);
    List<DiversCashOperationDto> findAllDiversCashOperationBetween(Long dcaopId, Instant startDate, Instant endDate,
                                                                   OperationType opType);
    Page<DiversCashOperationDto> findPageDiversCashOperationBetween(Long dcaopId, Instant startDate, Instant endDate,
                                                                    OperationType opType, int pagenum, int pagesize);
}
