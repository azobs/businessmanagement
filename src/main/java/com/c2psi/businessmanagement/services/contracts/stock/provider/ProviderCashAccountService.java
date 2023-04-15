package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.math.BigDecimal;

public interface ProviderCashAccountService {
    /************************************************************************
     *A certain amount of money can be paid in favor of a provider. This method
     *will help to register this kind of paiement.
     *The amount will be added to the amount present in the account the an operation
     * that describe the paiement will also be added in the system
     */
    Boolean saveCashOperation(Long pcaId, BigDecimal amount, OperationType operationType,
                             Long userbmId, String opObject, String opDescription);
    ProviderCashAccountDto saveProviderCashAccount(ProviderCashAccountDto pcaDto);
    Boolean deleteProviderCashAccountById(Long id);
    Boolean isProviderCashAccountDeleteable(Long pcaId);

    ProviderCashAccountDto findProviderCashAccountById(Long pcaId);
}
