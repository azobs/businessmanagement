package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ClientDamageAccountService {
    ClientDamageAccountDto saveClientDamageAccount(ClientDamageAccountDto cdaccDto);
    ClientDamageAccountDto findClientDamageAccountById(Long cdaccId);
    ClientDamageAccountDto findClientDamageAccountofArticleinPos(Long clientId, Long artId);
    List<ClientDamageAccountDto> findAllClientDamageAccountinPos(Long clientId);
    Page<ClientDamageAccountDto> findPageClientDamageAccountinPos(Long clientId, int pagenum, int pagesize);
    Boolean isClientDamageAccountDeleteable(Long cdaccId);
    Boolean deleteClientDamageAccountById(Long cdaccId);

    Boolean saveDamageOperation(Long cdaccId, BigDecimal qte, OperationType operationType,
                                 Long userbmId, String opObject, String opDescription );
    Boolean saveDamageOperation(ClientDamageAccountDto poscapaccDto, ClientDamageOperationDto poscapopDto);

}
