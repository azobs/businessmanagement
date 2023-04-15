package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProviderService {
    ProviderDto saveProvider(ProviderDto providerDto);
    ProviderDto updateProvider(ProviderDto providerDto);
    ProviderDto findProviderByNameofPos(String providerName, Long posId);
    Boolean isProviderUniqueForPos(String providerName, Long posId);
    List<ProviderDto> findAllProviderofPos(Long posId);
    Page<ProviderDto> findPageProviderofPos(Long posId, int pagenum, int pagesize);
    List<ProviderDto> findAllProviderofArticleInPos(Long artId, Long posId);
    Page<ProviderDto> findPageProviderofArticleInPos(Long artId, Long posId, int pagenum, int pagesize);
    Boolean deleteProviderById(Long id);
}
