package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ClientDamageOperationService {
    ClientDamageOperationDto updateClientDamageOperation(ClientDamageOperationDto ccopDto);
    Boolean isClientDamageOperationDeleteable(Long ccopId);
    Boolean deleteClientDamageOperationById(Long ccopId);
    List<ClientDamageOperationDto> findAllClientDamageOperation(Long ccaccId);
    Page<ClientDamageOperationDto> findPageClientDamageOperation(Long ccaccId, int pagenum, int pagesize);
    List<ClientDamageOperationDto> findAllClientDamageOperationofType(Long ccaccId, OperationType opType);
    Page<ClientDamageOperationDto> findPageClientDamageOperationofType(Long ccaccId, OperationType opType,
                                                                         int pagenum, int pagesize);
    List<ClientDamageOperationDto> findAllClientDamageOperationBetween(
            Long ccaccId, Instant startDate, Instant endDate);

    Page<ClientDamageOperationDto> findPageClientDamageOperationBetween(
            Long ccaccId, Instant startDate, Instant endDate, int pagenum, int pagesize);
    List<ClientDamageOperationDto> findAllClientDamageOperationBetween(
            Long ccaccId, OperationType opType, Instant startDate, Instant endDate);
    Page<ClientDamageOperationDto> findPageClientDamageOperationBetween(
            Long ccaccId, OperationType opType, Instant startDate, Instant endDate, int pagenum, int pagesize);
}
