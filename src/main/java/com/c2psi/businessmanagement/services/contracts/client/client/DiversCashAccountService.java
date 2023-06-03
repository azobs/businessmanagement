package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashAccountDto;

import java.math.BigDecimal;

public interface DiversCashAccountService {
    Boolean saveCashOperation(Long dcaId, BigDecimal amount, OperationType operationType, Long userbmId,
                              String opObject, String opDescription);

    DiversCashAccountDto saveDiversCashAccount(DiversCashAccountDto dcaDto);

    Boolean deleteDiversCashAccountById(Long dcaId);
    Boolean isDiversCashAccountDeleteable(Long dcaId);
    DiversCashAccountDto findDiversCashAccountById(Long dcaId);
    DiversCashAccountDto findDiversCashAccountIfExistOrCreate();
}
