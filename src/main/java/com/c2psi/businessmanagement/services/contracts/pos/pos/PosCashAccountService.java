package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;

import java.math.BigDecimal;

public interface PosCashAccountService {
    PosCashAccountDto savePosCashAccount(PosCashAccountDto pcaDto);

    /******************************************************************************
     * A certain amount of money can be paid into account of the point of sale
     * comming from outside. Means it is not in favor of a client or a provider
     * The amount will be added to the amount present in the account.  An operation
     * that describe the paiement will also be added in the system
     * @param pcaId
     * @param amount
     * @param userbmId
     * @return
     */

    Boolean saveCashOperation(Long pcaId, BigDecimal amount, OperationType operationType,
                               Long userbmId, String opObject, String opDescription);
    //Boolean saveCapsuleOperation(PosCapsuleAccountDto poscapaccDto, PosCapsuleOperationDto poscapopDto);
    Boolean saveCashOperation(PosCashAccountDto posCashAccountDto, PosCashOperationDto posCashOperationDto);
    Boolean isPosCashAccountDeleteable(Long id);
    Boolean deletePosCashAccountById(Long id);

    PosCashAccountDto findPosCashAccountById(Long pcaId);


}
