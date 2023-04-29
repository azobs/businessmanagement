package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;

import java.math.BigDecimal;

public interface ClientCashAccountService {

    Boolean saveCashOperation(Long ccaId, BigDecimal amount, OperationType operationType, Long userbmId,
                              String opObject, String opDescription);

    ClientCashAccountDto saveClientCashAccount(ClientCashAccountDto ccaDto);

    Boolean deleteClientCashAccountById(Long ccaid);
    Boolean isClientCashAccountDeleteable(Long ccaId);
    ClientCashAccountDto findClientCashAccountById(Long ccaId);
}
