package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProviderPackagingAccountService {
    ProviderPackagingAccountDto saveProviderPackagingAccount(ProviderPackagingAccountDto propackaccDto);
    ProviderPackagingAccountDto findProviderPackagingAccountById(Long propackaccId);
    ProviderPackagingAccountDto findProviderPackagingAccount(Long packagingId, Long providerId);
    List<ProviderPackagingAccountDto> findAllPackagingAccountofProvider(Long providerId);
    Page<ProviderPackagingAccountDto> findPagePackagingAccountofProvider(Long providerId, int pagenum, int pagesize);
    Boolean deleteProviderPackagingAccountById(Long propackaccId);
    Boolean isProviderPackagingAccountDeleteable(Long propackaccId);
    Boolean savePackagingOperationforProvider(Long propackaccId, BigDecimal qte, OperationType operationType,
                                   Long userBMId, String opObject, String opDescription);
    //Boolean saveCapsuleOperation(PosCapsuleAccountDto poscapaccDto, PosCapsuleOperationDto poscapopDto);
    Boolean savePackagingOperationforProvider(ProviderPackagingAccountDto providerPackagingAccountDto,
                                              ProviderPackagingOperationDto providerPackagingOperationDto);
}
