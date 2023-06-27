package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ClientPackagingAccountService {
    ClientPackagingAccountDto saveClientPackagingAccount(ClientPackagingAccountDto cltpackaccDto);
    ClientPackagingAccountDto findClientPackagingAccountById(Long cltpackaccId);
    ClientPackagingAccountDto findClientPackagingAccountByClientAndPackaging(Long packagingId, Long clientId);
    List<ClientPackagingAccountDto> findAllPackagingAccountofClient(Long clientId);
    Page<ClientPackagingAccountDto> findPagePackagingAccountofClient(Long clientId, int pagenum, int pagesize);
    List<ClientPackagingAccountDto> findAllClientPackagingAccountinPos(Long posId);
    Page<ClientPackagingAccountDto> findPageClientPackagingAccountinPos(Long posId, int pagenum, int pagesize);
    Boolean deleteClientPackagingAccountById(Long cltpackaccId);
    Boolean isClientPackagingAccountDeleteable(Long cltpackaccId);
    Boolean savePackagingOperationforClient(Long cltpackaccId, BigDecimal qte, OperationType operationType,
                                              Long userBMId, String opObject, String opDescription);
    Boolean savePackagingOperationforClient(ClientPackagingAccountDto clientPackAccDto,
                                           ClientPackagingOperationDto clientPackOpDto);
}
