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
    List<ClientCashOperationDto> findAllClientCashOperation(Long ccaId);
    Page<ClientCashOperationDto> findPageClientCashOperation(Long ccaId, int pagenum, int pagesize);
    List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccaId, Instant startDate, Instant endDate);
    Page<ClientCashOperationDto> findPageClientCashOperationBetween(Long ccaId, Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize);
    List<ClientCashOperationDto> findAllClientCashOperationofTypeBetween(Long ccaId, OperationType opType,
                                                                         Instant startDate, Instant endDate);
    Page<ClientCashOperationDto> findPageClientCashOperationofTypeBetween(Long ccaId, OperationType opType,
                                                                          Instant startDate, Instant endDate,
                                                                          int pagenum, int pagesize);
}
