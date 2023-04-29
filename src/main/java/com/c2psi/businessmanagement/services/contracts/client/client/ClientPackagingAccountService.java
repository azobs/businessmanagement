package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ClientPackagingAccountService {
    ClientPackagingAccountDto saveClientPackagingAccount(ClientPackagingAccountDto cltpackaccDto);
    ClientPackagingAccountDto findClientPackagingAccountById(Long cltpackaccId);
    ClientPackagingAccountDto findClientPackagingAccount(Long packagingId, Long clientId);
    List<ClientPackagingAccountDto> findAllPackagingAccountofClient(Long clientId);
    Page<ClientPackagingAccountDto> findPagePackagingAccountofClient(Long clientId, int pagenum, int pagesize);
    Boolean deleteClientPackagingAccountById(Long cltpackaccId);
    Boolean isClientPackagingAccountDeleteable(Long cltpackaccId);
    Boolean savePackagingOperationforClient(Long cltpackaccId, BigDecimal qte, OperationType operationType,
                                              Long userBMId, String opObject, String opDescription);
}
