package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

public interface ProviderCashAccountService {
    /************************************************************************
     *A certain amount of money can be paid in favor of a provider. This method
     *will help to register this kind of paiement.
     *The amount will be added to the amount present in the account the an operation
     * that describe the paiement will also be added in the system
     * @param pcaDto
     * @param amount
     * @param userbmDto
     * @param providerDto
     * @return
     */
    Boolean saveCashPaiement(PosCashAccountDto pcaDto, Double amount,
                             UserBMDto userbmDto, ProviderDto providerDto);
    ProviderCashAccountDto saveProviderCashAccount(ProviderCashAccountDto pcaDto);
    Boolean deleteProviderCashAccountById(Long id);
}
