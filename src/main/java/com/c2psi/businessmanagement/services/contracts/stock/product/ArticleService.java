package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ArticleService {
    List<ArticleDto> findAllArticleofPos(Long posId);
    Page<ArticleDto> findPageofArticleofPos(Long posId, int pagenum, int pagesize);
    ArticleDto saveArticle(ArticleDto artDto);
    ArticleDto updateArticle(ArticleDto artDto);
    ArticleDto fixQuantityofArticle(Long artId, BigDecimal new_quantity);
    ArticleDto updateUnitofArticle(Long artId, Long unitId);
    ArticleDto updateBasePriceofArticle(Long artId, Long basepriceId);
    ArticleDto findArticleByCodeInPos(String artCode, Long posId);
    ArticleDto findArticleById(Long artId);
    Boolean isArticleUniqueInPos(String artCode, Long posId);
    Boolean isArticleDeleteable(Long artId);
    Boolean deleteArticleById(Long art_id);
    List<ArticleDto> findAllArticleOfProviderInPos(Long posId, Long providerId);
    Page<ArticleDto> findPageofArticleofProviderInPos(Long posId, int pagenum, int pagesize);
    Page<ArticleDto> findPageArticleOfPointofsaleContaining(Long posId, String motif, int pagenum, int pagesize);
    Page<ArticleDto> findPageArticleOfEnterpriseContaining(Long entId, String motif, int pagenum, int pagesize);
    List<ArticleDto> findAllArticleofEnterprise(Long entId);
    Page<ArticleDto> findPageofArticleofEnterprise(Long entId, int pagenum, int pagesize);
    ArticleDto addQuantityofArticle(Long artId, BigDecimal quantityToAdd);
    ArticleDto reduceQuantityofArticle(Long artId, BigDecimal quantityToReduce);
    ArticleDto addDamageArticleof(Long artId, BigDecimal qteToAdd);
    ArticleDto reduceDamageArticle(Long artId, BigDecimal qteToReduce);


}
