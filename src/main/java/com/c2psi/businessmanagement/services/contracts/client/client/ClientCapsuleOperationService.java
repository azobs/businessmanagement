package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ClientCapsuleOperationService {
    ClientCapsuleOperationDto updateClientCapsuleOperation(ClientCapsuleOperationDto ccopDto);
    Boolean isClientCapsuleOperationDeleteable(Long ccopId);
    Boolean deleteClientCapsuleOperationById(Long ccopId);
    List<ClientCapsuleOperationDto> findAllClientCapsuleOperation(Long ccaccId);
    Page<ClientCapsuleOperationDto> findPageClientCapsuleOperation(Long ccaccId, int pagenum, int pagesize);
    List<ClientCapsuleOperationDto> findAllClientCapsuleOperationofType(Long ccaccId, OperationType opType);
    Page<ClientCapsuleOperationDto> findPageClientCapsuleOperationofType(Long ccaccId, OperationType opType,
                                                                             int pagenum, int pagesize);
    List<ClientCapsuleOperationDto> findAllClientCapsuleOperationBetween(
            Long ccaccId, Instant startDate, Instant endDate);

    Page<ClientCapsuleOperationDto> findPageClientCapsuleOperationBetween(
            Long ccaccId, Instant startDate, Instant endDate, int pagenum, int pagesize);
    List<ClientCapsuleOperationDto> findAllClientCapsuleOperationBetween(
            Long ccaccId, OperationType opType, Instant startDate, Instant endDate);
    Page<ClientCapsuleOperationDto> findPageClientCapsuleOperationBetween(
            Long ccaccId, OperationType opType, Instant startDate, Instant endDate, int pagenum, int pagesize);
}
