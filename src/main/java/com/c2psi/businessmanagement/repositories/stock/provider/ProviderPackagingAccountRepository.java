package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderPackagingAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderPackagingAccountRepository
        extends JpaRepository<ProviderPackagingAccount, Long> {
    Optional<ProviderPackagingAccount> findProviderPackagingAccountById(Long propackaccId);
    @Query("SELECT propacksacc FROM ProviderPackagingAccount propacksacc WHERE propacksacc.ppaPackaging.id=:packagingId AND propacksacc.ppaProvider.id=:providerId")
    Optional<ProviderPackagingAccount> findProviderPackagingAccount(@Param("packagingId") Long packagingId, @Param("providerId") Long providerId);
    @Query("SELECT propacksacc FROM ProviderPackagingAccount propacksacc WHERE propacksacc.ppaProvider.id=:providerId ORDER BY propacksacc.ppaPackaging.packLabel ASC")
    Optional<List<ProviderPackagingAccount>> findAllPackagingAccountofProvider(@Param("providerId") Long providerId);
    @Query("SELECT propacksacc FROM ProviderPackagingAccount propacksacc WHERE propacksacc.ppaProvider.id=:providerId ORDER BY propacksacc.ppaPackaging.packLabel ASC")
    Optional<Page<ProviderPackagingAccount>> findPagePackagingAccountofProvider(@Param("providerId") Long providerId, Pageable pageable);
}
