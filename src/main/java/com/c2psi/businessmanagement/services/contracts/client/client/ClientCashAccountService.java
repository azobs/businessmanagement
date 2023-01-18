package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

public interface ClientCashAccountService {
    ClientCashAccountDto saveClientCashAccount(ClientCashAccountDto ccaDto);
    ClientCashAccountDto findClientCashAccountById(Long id);
    /****************************************************************************
     * A certain amount of money can be paid in favor of a client. This method
     * will help to register this kind of paiement.
     *The amount will be added to the amount present in the account the an operation
     *that describe the paiement will also be added in the system
     * @param pcaDto
     * @param amount
     * @param userbmDto
     * @param clientDto
     * @return
     */
    boolean saveCashDeposit(PosCashAccountDto pcaDto, Double amount,
                             UserBMDto userbmDto, ClientDto clientDto);
    boolean saveCashDeposit(PosCashAccountDto pcaDto, Double amount,
                            UserBMDto userbmDto, ClientDto clientDto,
                            SaleInvoiceCashDto saleicashDto);
    boolean saveCashWithdrawal(PosCashAccountDto pcaDto, Double amount,
                            UserBMDto userbmDto, ClientDto clientDto);
}
