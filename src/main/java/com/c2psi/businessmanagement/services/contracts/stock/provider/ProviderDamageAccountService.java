package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProviderDamageAccountService {
    ProviderDamageAccountDto saveProviderDamageAccount(ProviderDamageAccountDto prodamaccDto);
    ProviderDamageAccountDto findProviderDamageAccountById(Long prodamaccId);
    ProviderDamageAccountDto findProviderDamageAccountofArticleinPos(Long providerId, Long artId);
    List<ProviderDamageAccountDto> findAllProviderDamageAccountinPos(Long providerId);
    Page<ProviderDamageAccountDto> findPageProviderDamageAccountinPos(Long providerId, int pagenum, int pagesize);
    Boolean isProviderDamageAccountDeleteable(Long prodamaccId);
    Boolean deleteProviderDamageAccountById(Long prodamaccId);
    Boolean saveDamageOperation(Long prodamaccId, BigDecimal qte, OperationType operationType,
                                 Long userbmId, String opObject, String opDescription );
}
