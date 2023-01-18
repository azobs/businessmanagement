package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;

import java.util.Date;
import java.util.List;

public interface ClientCapsuleOperationService {
    ClientCapsuleOperationDto saveClientCapsuleOperation(ClientCapsuleOperationDto cltcsopDto);

    ClientCapsuleOperationDto findClientCapsuleOperationById(Long id);

    /******************************************************************************************
     * Cette methode retourne la liste des operations effectue sur un compte capsule d'un client
     * @param cltcsaccDto
     * @return
     */
    List<ClientCapsuleOperationDto> findAllByClientCapsuleAccount(
            ClientCapsuleAccountDto cltcsaccDto);

    List<ClientCapsuleOperationDto> findAllByClientCapsuleAccount(
            ClientCapsuleAccountDto cltcsaccDto, OperationType opType);

    List<ClientCapsuleOperationDto> findAllByClientCapsuleAccountBetween(
            ClientCapsuleAccountDto cltcsaccDto, Date startDate, Date endDate);

    List<ClientCapsuleOperationDto> findAllByClientCapsuleAccountBetween(
            ClientCapsuleAccountDto cltcsaccDto, Date startDate, Date endDate,
            OperationType opType);

    Boolean deleteClientCapsuleOperationById(Long cltcsop_id);
}
