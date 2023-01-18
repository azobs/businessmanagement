package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;

import java.util.Date;
import java.util.List;

public interface ClientPackagingOperationService {
    ClientPackagingOperationDto saveClientPackagingOperation(ClientPackagingOperationDto cltpopDto);
    ClientPackagingOperationDto findClientPackagingOperationById(Long id);
    List<ClientPackagingOperationDto> findAllByClientPackagingAccount(
            ClientPackagingAccountDto cltpaccDto);
    List<ClientPackagingOperationDto> findAllByClientPackagingAccount(
            ClientPackagingAccountDto cltpaccDto, OperationType op_type);
    List<ClientPackagingOperationDto> findAllByClientPackagingAccountBetween(
            ClientPackagingAccountDto cltpaccDto, Date startDate, Date endDate);
    List<ClientPackagingOperationDto> findAllByClientPackagingAccountBetween(
            ClientPackagingAccountDto cltpaccDto, Date startDate, Date endDate,
            OperationType op_type);
    Boolean deleteClientPackagingOperationById(Long cltpop_id);
}
