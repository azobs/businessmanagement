package com.c2psi.businessmanagement.repositories.pos.pos;


import com.c2psi.businessmanagement.models.PosPackagingAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosPackagingAccountRepository extends JpaRepository<PosPackagingAccount, Long> {
    Optional<PosPackagingAccount> findPosPackagingAccountById(Long ppackaccId);
    @Query("SELECT ppacksacc FROM PosPackagingAccount ppacksacc WHERE ppacksacc.ppaPackaging.id=:packagingId AND ppacksacc.ppaPointofsale.id=:posId")
    Optional<PosPackagingAccount> findPosPackagingAccountInPos(@Param("packagingId") Long packagingId, @Param("posId") Long posId);
    @Query("SELECT ppacksacc FROM PosPackagingAccount ppacksacc WHERE ppacksacc.ppaPointofsale.id=:posId ORDER BY ppacksacc.ppaPackaging.packLabel ASC")
    Optional<List<PosPackagingAccount>> findAllPackagingAccountInPos(@Param("posId") Long posId);
    @Query("SELECT ppacksacc FROM PosPackagingAccount ppacksacc WHERE ppacksacc.ppaPointofsale.id=:posId ORDER BY ppacksacc.ppaPackaging.packLabel ASC")
    Optional<Page<PosPackagingAccount>> findPagePackagingAccountInPos(@Param("posId") Long posId, Pageable pageable);
    @Query("SELECT ppacksacc FROM PosPackagingAccount ppacksacc WHERE ppacksacc.ppaPackaging.id=:packagingId ORDER BY ppacksacc.ppaPackaging.packLabel ASC")
    Optional<List<PosPackagingAccount>> findAllPackagingAccount(@Param("packagingId") Long packagingId);
    @Query("SELECT ppacksacc FROM PosPackagingAccount ppacksacc WHERE ppacksacc.ppaPackaging.id=:packagingId ORDER BY ppacksacc.ppaPackaging.packLabel ASC")
    Optional<Page<PosPackagingAccount>> findPagePackagingAccount(@Param("packagingId") Long packagingId, Pageable pageable);
}
