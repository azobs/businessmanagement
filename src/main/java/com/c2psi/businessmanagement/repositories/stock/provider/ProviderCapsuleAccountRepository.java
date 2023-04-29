package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderCapsuleAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderCapsuleAccountRepository
        extends JpaRepository<ProviderCapsuleAccount, Long> {
    Optional<ProviderCapsuleAccount> findProviderCapsuleAccountById(Long procapsaccId);
    @Query("SELECT procapsacc FROM ProviderCapsuleAccount procapsacc WHERE procapsacc.pcsaProvider.id=:providerId AND procapsacc.pcsaArticle.id=:artId")
    Optional<ProviderCapsuleAccount> findProviderCapsuleAccountofArticleinPos(@Param("providerId") Long providerId,
                                                                              @Param("artId") Long artId);
    @Query("SELECT procapsacc FROM ProviderCapsuleAccount procapsacc WHERE procapsacc.pcsaProvider.id=:providerId")
    Optional<List<ProviderCapsuleAccount>> findAllProviderCapsuleAccountinPos(@Param("providerId") Long providerId);
    @Query("SELECT procapsacc FROM ProviderCapsuleAccount procapsacc WHERE procapsacc.pcsaProvider.id=:providerId")
    Optional<Page<ProviderCapsuleAccount>> findPageProviderCapsuleAccountinPos(@Param("providerId") Long providerId,
                                                                               Pageable pageable);

}
