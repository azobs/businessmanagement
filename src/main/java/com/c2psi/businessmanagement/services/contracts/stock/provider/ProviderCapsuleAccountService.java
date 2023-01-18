package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.util.List;

public interface ProviderCapsuleAccountService {
    ProviderCapsuleAccountDto saveProviderCapsuleAccount(ProviderCapsuleAccountDto pcsaccDto);
    ProviderCapsuleAccountDto findProviderCapsuleAccountById(Long id);
    ProviderCapsuleAccountDto findProviderCapsuleAccountByPosProviderArticle(PointofsaleDto posDto,
                                                       ProviderDto providerDto,
                                                   ArticleDto artDto);
    List<ProviderCapsuleAccountDto> findAllCapsuleAccountProviderPos(ProviderDto providerDto,
                                                                 PointofsaleDto posDto);
    List<ProviderCapsuleAccountDto> findAllCapsuleAccountArticlePos(ArticleDto artDto,
                                                                  PointofsaleDto posDto);
    Boolean deleteProviderCapsuleAccountById(Long pcsaId);

    Boolean saveCapsulePaiement(ProviderCapsuleAccountDto pcsaDto, Integer amount,
                                UserBMDto userbmDto, ProviderDto providerDto);
}
