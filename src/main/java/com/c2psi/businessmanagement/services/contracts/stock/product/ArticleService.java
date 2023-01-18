package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.math.BigDecimal;
import java.util.List;

public interface ArticleService {
    List<ArticleDto> findAllArticle(PointofsaleDto posDto);

    ArticleDto fixQuantity(ArticleDto artDto, Integer new_quantity);
    ArticleDto saveArticle(ArticleDto artDto);
    ArticleDto findArticle(String art_code, PointofsaleDto posDto);
    ArticleDto findArticle(Long art_id);
    Boolean isArticleUnique(String art_code, PointofsaleDto posDto);
    Boolean deleteArticleById(Long art_id);
    Boolean isStockArticleEmpty(ArticleDto artDto);
    List<ArticleDto> findAllArticleOfProvider(PointofsaleDto posDto,
                                              ProviderDto providerDto);
    List<ArticleDto> findAllArticleOfPointofsale(PointofsaleDto posDto);
    List<ArticleDto> findAllArticleOfPointofsaleContaining(PointofsaleDto posDto,
                                                           String motif);
    List<ArticleDto> findAllArticleOfEnterprise(EnterpriseDto entDto);
    List<ArticleDto> findAllArticle();
    ArticleDto addQuantity(ArticleDto artDto, Integer quantityToAdd);
    ArticleDto reduceQuantity(ArticleDto artDto, Integer quantityToReduce);
    ArticleDto addDamageArticle(ArticleDto artDto, Integer qteToAdd);
    ArticleDto reduceDamageArticle(ArticleDto artDto, Integer qteToReduce);

}
