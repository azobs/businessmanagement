package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderDamageAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderDamageAccountRepository
        extends JpaRepository<ProviderDamageAccount, Long> {

    Optional<ProviderDamageAccount> findProviderDamageAccountById(Long prodamaccId);

    @Query("SELECT prodamacc FROM ProviderDamageAccount prodamacc WHERE prodamacc.pdaProvider.id=:providerId AND prodamacc.pdaArticle.id=:artId")
    Optional<ProviderDamageAccount> findProviderDamageAccountofArticleinPos(@Param("providerId") Long providerId,
                                                                            @Param("artId") Long artId);

    @Query("SELECT prodamacc FROM ProviderDamageAccount prodamacc WHERE prodamacc.pdaProvider.id=:providerId")
    Optional<List<ProviderDamageAccount>> findAllProviderDamageAccountinPos(@Param("providerId") Long providerId);


    @Query("SELECT prodamacc FROM ProviderDamageAccount prodamacc WHERE prodamacc.pdaProvider.id=:providerId")
    Optional<Page<ProviderDamageAccount>> findPageProviderDamageAccountinPos(@Param("providerId") Long providerId,
                                                                             Pageable pageable);
}
