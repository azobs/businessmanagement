package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;

import java.util.Date;
import java.util.List;

public interface ClientCashOperationService {
    ClientCashOperationDto saveClientCashOperation(ClientCashOperationDto pcopDto);
    Boolean deleteClientCashOperationById(Long ccop_id);
    ClientCashOperationDto findClientCashOperationById(Long ccop_id);
    List<ClientCashOperationDto> findAllClientCashOperation(
            ClientCashAccountDto cltcaccDto);
    List<ClientCashOperationDto> findAllClientCashOperation(
            ClientCashAccountDto cltcaccDto, OperationType opType);
    List<ClientCashOperationDto> findAllClientCashOperationBetween(
            ClientCashAccountDto cltcaccDto, Date startDate, Date endDate);
    List<ClientCashOperationDto> findAllClientCashOperationBetween(
            ClientCashAccountDto cltcaccDto, Date startDate, Date endDate,
            OperationType opType);
}
