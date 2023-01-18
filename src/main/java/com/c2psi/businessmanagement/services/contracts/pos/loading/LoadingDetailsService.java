package com.c2psi.businessmanagement.services.contracts.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface LoadingDetailsService {
    LoadingDetailsDto saveLoadingDetails(LoadingDetailsDto ldDto);
    LoadingDetailsDto findLoadingDetailsById(Long ld_id);
    List<LoadingDetailsDto> findAllLoadingDetails(LoadingDto loadingDto);
    LoadingDetailsDto findLoadingDetailsForArticle(LoadingDto loadingDto,
                                                  ArticleDto artDto);
    Boolean isLoadingDetailsUniqueForArticle(LoadingDto loadingDto,
                                            ArticleDto artDto);
    Boolean deleteLoadingDetailsById(Long id);
}
