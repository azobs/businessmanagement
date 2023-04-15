package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.models.PosCapsuleAccount;
import com.c2psi.businessmanagement.models.PosDamageAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosDamageAccountRepository
        extends JpaRepository<PosDamageAccount, Long> {
    Optional<PosDamageAccount> findPosDamageAccountById(Long posdamaccId);
    @Query("SELECT posdamacc FROM PosDamageAccount posdamacc WHERE posdamacc.pdaArticle.id=:artId AND posdamacc.pdaPointofsale.id=:posId")
    Optional<PosDamageAccount> findPosDamageAccountofArticleInPos(@Param("artId") Long artId, @Param("posId") Long posId);
    @Query("SELECT posdamacc FROM PosDamageAccount posdamacc WHERE posdamacc.pdaPointofsale.id=:posId ORDER BY posdamacc.pdaArticle.artPf.pfProduct.prodCat.catName ASC, posdamacc.pdaArticle.artName")
    Optional<List<PosDamageAccount>> findAllDamageAccountInPos(@Param("posId") Long posId);
    @Query("SELECT posdamacc FROM PosDamageAccount posdamacc WHERE posdamacc.pdaPointofsale.id=:posId ORDER BY posdamacc.pdaArticle.artPf.pfProduct.prodCat.catName ASC, posdamacc.pdaArticle.artName")
    Optional<Page<PosDamageAccount>> findPageDamageAccountInPos(@Param("posId") Long posId, Pageable pageable);
    @Query("SELECT posdamacc FROM PosDamageAccount posdamacc WHERE posdamacc.pdaArticle.id=:posId ORDER BY posdamacc.pdaArticle.artPf.pfProduct.prodCat.catName ASC, posdamacc.pdaArticle.artName")
    Optional<List<PosDamageAccount>> findAllDamageAccountofArticle(@Param("posId") Long posId);
    @Query("SELECT posdamacc FROM PosDamageAccount posdamacc WHERE posdamacc.pdaArticle.id=:posId ORDER BY posdamacc.pdaArticle.artPf.pfProduct.prodCat.catName ASC, posdamacc.pdaArticle.artName")
    Optional<Page<PosDamageAccount>> findPageDamageAccountofArticle(@Param("posId") Long posId, Pageable pageable);
}
