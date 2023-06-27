package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ClientDamageOperationService {
    ClientDamageOperationDto updateClientDamageOperation(ClientDamageOperationDto cdamopDto);
    Boolean isClientDamageOperationDeleteable(Long cdamopId);
    Boolean deleteClientDamageOperationById(Long cdamopId);
    List<ClientDamageOperationDto> findAllClientDamageOperation(Long cdamaccId);
    Page<ClientDamageOperationDto> findPageClientDamageOperation(Long cdamaccId, int pagenum, int pagesize);
    List<ClientDamageOperationDto> findAllClientDamageOperationofType(Long cdamaccId, OperationType opType);
    Page<ClientDamageOperationDto> findPageClientDamageOperationofType(Long cdamaccId, OperationType opType,
                                                                         int pagenum, int pagesize);
    List<ClientDamageOperationDto> findAllClientDamageOperationBetween(
            Long cdamaccId, Instant startDate, Instant endDate);

    Page<ClientDamageOperationDto> findPageClientDamageOperationBetween(
            Long cdamaccId, Instant startDate, Instant endDate, int pagenum, int pagesize);
    List<ClientDamageOperationDto> findAllClientDamageOperationofTypeBetween(
            Long cdamaccId, OperationType opType, Instant startDate, Instant endDate);
    Page<ClientDamageOperationDto> findPageClientDamageOperationofTypeBetween(
            Long cdamaccId, OperationType opType, Instant startDate, Instant endDate, int pagenum, int pagesize);
}
