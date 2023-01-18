package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.util.List;

public interface ProviderService {
    List<ProviderDto> findAllProvider(PointofsaleDto posDto);
    ProviderDto saveProvider(ProviderDto providerDto);
    List<ProviderDto> findAllProvider(PointofsaleDto posDto, ArticleDto artDto);
    Boolean damageArticleChangedByProvider(ArticleDto artDto, Integer qteNeeded,
                                 Integer qte_changed);
    Boolean deleteProviderById(Long id);
}
