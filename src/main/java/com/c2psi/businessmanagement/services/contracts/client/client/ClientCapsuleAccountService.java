package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ClientCapsuleAccountService {
    ClientCapsuleAccountDto saveClientCapsuleAccount(ClientCapsuleAccountDto ccaccDto);
    ClientCapsuleAccountDto findClientCapsuleAccountById(Long ccaccId);
    ClientCapsuleAccountDto findClientCapsuleAccountofArticleinPos(Long clientId, Long artId);
    List<ClientCapsuleAccountDto> findAllClientCapsuleAccountinPos(Long clientId);
    Page<ClientCapsuleAccountDto> findPageClientCapsuleAccountinPos(Long clientId, int pagenum, int pagesize);
    Boolean isClientCapsuleAccountDeleteable(Long ccaccId);
    Boolean deleteClientCapsuleAccountById(Long ccaccId);

    Boolean saveCapsuleOperation(Long ccaccId, BigDecimal qte, OperationType operationType,
                                 Long userbmId, String opObject, String opDescription );
    //Boolean saveCapsuleOperation(PosCapsuleAccountDto poscapaccDto, PosCapsuleOperationDto poscapopDto);
    Boolean saveCapsuleOperation(ClientCapsuleAccountDto clientCapsuleAccountDto,
                                 ClientCapsuleOperationDto clientCapsuleOperationDto);
}
