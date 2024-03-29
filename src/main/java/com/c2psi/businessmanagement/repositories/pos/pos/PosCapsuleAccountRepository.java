package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.models.PosCapsuleAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosCapsuleAccountRepository
        extends JpaRepository<PosCapsuleAccount, Long> {
    Optional<PosCapsuleAccount> findPosCapsuleAccountById(Long posCapsAccountId);
    @Query("SELECT poscapsacc FROM PosCapsuleAccount poscapsacc WHERE poscapsacc.pcsaArticle.id=:artId AND poscapsacc.pcsaPointofsaleId=:posId")
    Optional<PosCapsuleAccount> findPosCapsuleAccountofArticleInPos(@Param("artId") Long artId, @Param("posId") Long posId);
    @Query("SELECT poscapsacc FROM PosCapsuleAccount poscapsacc WHERE poscapsacc.pcsaPointofsaleId=:posId ORDER BY poscapsacc.pcsaArticle.artPf.pfProduct.prodCat.catName ASC, poscapsacc.pcsaArticle.artName")
    Optional<List<PosCapsuleAccount>> findAllCapsuleAccountInPos(@Param("posId") Long posId);
    @Query("SELECT poscapsacc FROM PosCapsuleAccount poscapsacc WHERE poscapsacc.pcsaPointofsaleId=:posId ORDER BY poscapsacc.pcsaArticle.artPf.pfProduct.prodCat.catName ASC, poscapsacc.pcsaArticle.artName")
    Optional<Page<PosCapsuleAccount>> findPageCapsuleAccountInPos(@Param("posId") Long posId, Pageable pageable);
    @Query("SELECT poscapsacc FROM PosCapsuleAccount poscapsacc WHERE poscapsacc.pcsaArticle.id=:artId ORDER BY poscapsacc.pcsaArticle.artPf.pfProduct.prodCat.catName ASC, poscapsacc.pcsaArticle.artName")
    Optional<List<PosCapsuleAccount>> findAllCapsuleAccountofArticle(@Param("artId") Long artId);
    @Query("SELECT poscapsacc FROM PosCapsuleAccount poscapsacc WHERE poscapsacc.pcsaArticle.id=:artId ORDER BY poscapsacc.pcsaArticle.artPf.pfProduct.prodCat.catName ASC, poscapsacc.pcsaArticle.artName")
    Optional<Page<PosCapsuleAccount>> findPageCapsuleAccountofArticle(@Param("artId") Long artId, Pageable pageable);

}
