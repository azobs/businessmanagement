package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProviderCapsuleAccountService {
    ProviderCapsuleAccountDto saveProviderCapsuleAccount(ProviderCapsuleAccountDto pcsaccDto);
    ProviderCapsuleAccountDto findProviderCapsuleAccountById(Long procapaccId);
    ProviderCapsuleAccountDto findProviderCapsuleAccountofArticleinPos(Long providerId, Long artId);
    List<ProviderCapsuleAccountDto> findAllProviderCapsuleAccountinPos(Long providerId);
    Page<ProviderCapsuleAccountDto> findPageProviderCapsuleAccountinPos(Long providerId, int pagenum, int pagesize);
    Boolean isProviderCapsuleAccountDeleteable(Long procsaccId);
    Boolean deleteProviderCapsuleAccountById(Long procsaccId);

    Boolean saveCapsuleOperation(Long procapaccId, BigDecimal qte, OperationType operationType,
                                 Long userbmId, String opObject, String opDescription );
}
