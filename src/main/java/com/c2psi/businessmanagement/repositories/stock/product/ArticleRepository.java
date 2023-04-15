package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleById(Long artId);
    @Query("SELECT art FROM Article art WHERE art.artCode=:artCode AND art.artPos.id=:posId")
    Optional<Article> findArticleByArtCodeAndPos(@Param("artCode") String artCode, @Param("posId") Long posId);
    @Query("SELECT art FROM Article art WHERE art.artPos.id=:posId ORDER BY art.artPf.pfProduct.prodCat.catName ASC, art.artName ASC")
    Optional<List<Article>> findAllArticleofPos(@Param("posId") Long posId);
    @Query("SELECT art FROM Article art WHERE art.artPos.id=:posId ORDER BY art.artPf.pfProduct.prodCat.catName ASC, art.artName ASC")
    Optional<Page<Article>> findPageArticleofPos(@Param("posId") Long posId, Pageable pageable);
    @Query("SELECT art FROM Article  art WHERE art.artName LIKE :sample AND art.artPos.id=:posId ORDER BY art.artName ASC")
    Optional<Page<Article>> findAllByArtNameInPosContaining(@Param("posId") Long posId, @Param("sample") String sample, Pageable pageable);

    @Query("SELECT art FROM Article  art WHERE art.artName LIKE :sample AND art.artPos.posEnterprise.id=:entId ORDER BY art.artName ASC")
    Optional<Page<Article>> findAllByArtNameInEntContaining(@Param("entId") Long entId, @Param("sample") String sample, Pageable pageable);

    @Query("SELECT art FROM Article art WHERE art.artPos.posEnterprise.id=:entId ORDER BY art.artPf.pfProduct.prodCat.catName ASC, art.artName ASC")
    Optional<List<Article>> findAllArticleofEnterprise(@Param("entId") Long entId);
    @Query("SELECT art FROM Article art WHERE art.artPos.posEnterprise.id=:entId ORDER BY art.artPf.pfProduct.prodCat.catName ASC, art.artName ASC")
    Optional<Page<Article>> findPageArticleofEnterprise(@Param("entId") Long entId, Pageable pageable);
}
