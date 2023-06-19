package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ClientCashOperationService {
    ClientCashOperationDto updateClientCashOperation(ClientCashOperationDto ccaopDto);
    Boolean isClientCashOperationDeleatable(Long ccaopDto);
    Boolean deleteClientCashOperationById(Long ccaopId);
    ClientCashOperationDto findClientCashOperationById(Long ccaopId);
    List<ClientCashOperationDto> findAllClientCashOperation(Long ccaopId);
    Page<ClientCashOperationDto> findPageClientCashOperation(Long ccaopId, int pagenum, int pagesize);
    List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccaopId, Instant startDate, Instant endDate);
    Page<ClientCashOperationDto> findPageClientCashOperationBetween(Long ccaopId, Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize);
    List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccaopId, Instant startDate, Instant endDate,
                                                                       OperationType opType);
    Page<ClientCashOperationDto> findPageClientCashOperationBetween(Long ccaopId, Instant startDate, Instant endDate,
                                                                        OperationType opType, int pagenum, int pagesize);
}
