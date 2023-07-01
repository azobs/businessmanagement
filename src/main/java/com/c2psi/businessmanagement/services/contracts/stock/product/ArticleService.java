package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ArticleService {
    List<ArticleDto> findAllArticleofPos(Long posId);
    Page<ArticleDto> findPageArticleofPos(Long posId, int pagenum, int pagesize);
    List<ArticleDto> findAllArticleofPosOrderByCreationDate(Long posId);
    Page<ArticleDto> findPageArticleofPosOrderByCreationDate(Long posId, int pagenum, int pagesize);
    List<ArticleDto> findAllArticleofCat(Long catId);
    Page<ArticleDto> findPageArticleofCat(Long catId, int pagenum, int pagesize);
    ArticleDto saveArticle(ArticleDto artDto);
    ArticleDto updateArticle(ArticleDto artDto);
    ArticleDto fixQuantityofArticle(ArticleDto artDto);
    ArticleDto updateUnitofArticle(ArticleDto artDto);
    ArticleDto updateBasePriceofArticle(ArticleDto artDto);
    ArticleDto findArticleByCodeInPos(String artCode, Long posId);
    ArticleDto findArticleById(Long artId);
    Boolean isArticleUniqueInPos(String artCode, Long posId);
    Boolean isArticleDeleteable(Long artId);
    List<ArticleDto> findAllArticleofProviderInPos(Long posId, Long providerId);
    Page<ArticleDto> findPageArticleofProviderInPos(Long posId, Long providerId, int pagenum, int pagesize);
    Page<ArticleDto> findPageArticleofPointofsaleContaining(Long posId, String motif, int pagenum, int pagesize);
    /*Page<ArticleDto> findPageArticleOfEnterpriseContaining(Long entId, String motif, int pagenum, int pagesize);
    List<ArticleDto> findAllArticleofEnterprise(Long entId);
    Page<ArticleDto> findPageofArticleofEnterprise(Long entId, int pagenum, int pagesize);*/
    ArticleDto addQuantityofArticle(Long artId, BigDecimal quantityToAdd);
    ArticleDto reduceQuantityofArticle(Long artId, BigDecimal quantityToReduce);
    ArticleDto addDamageArticleof(Long artId, BigDecimal qteToAdd);
    ArticleDto reduceDamageArticle(Long artId, BigDecimal qteToReduce);

    /**************************************************************************************************************
     * Methode pour avoir le prix par defaut pour les client divers ou alors les clients a qui aucun prixspecial
     * n'est associe. Car si un prixpecial est associe a un client cest ce prix la quil faudra retourner
     * @param qteCommand
     * @param articleId
     * @return
     */
    BigDecimal getCommonEffectivePriceToApplied(Long articleId, BigDecimal qteCommand);
    Boolean deleteArticleById(Long art_id);


}
