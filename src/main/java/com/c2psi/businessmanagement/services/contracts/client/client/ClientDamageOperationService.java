package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;

import java.util.Date;
import java.util.List;

public interface ClientDamageOperationService {
    ClientDamageOperationDto saveClientDamageOperation(ClientDamageOperationDto cltdopDto);
    ClientDamageOperationDto findClientDamageOperationById(Long id);
    List<ClientDamageOperationDto> findAllByClientDamageAccount(
            ClientDamageAccountDto cltdaccDto);
    List<ClientDamageOperationDto> findAllByClientDamageAccount(
            ClientDamageAccountDto cltdaccDto, OperationType opType);
    List<ClientDamageOperationDto> findAllByClientDamageAccountBetween(
            ClientDamageAccountDto cltdaccDto, Date startDate, Date endDate);
    List<ClientDamageOperationDto> findAllByClientDamageAccountBetween(
            ClientDamageAccountDto cltdaccDto, Date startDate, Date endDate,
            OperationType opType);
    Boolean deleteClientDamageOperationById(Long cltdopId);
}
