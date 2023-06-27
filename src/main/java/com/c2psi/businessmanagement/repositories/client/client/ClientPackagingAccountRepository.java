package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientPackagingAccount;
import com.c2psi.businessmanagement.models.ProviderPackagingAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientPackagingAccountRepository
        extends JpaRepository<ClientPackagingAccount, Long> {
    Optional<ClientPackagingAccount> findClientPackagingAccountById(Long cltpackaccId);
    @Query("SELECT cltpacksacc FROM ClientPackagingAccount cltpacksacc WHERE cltpacksacc.cpaPackaging.id=:packagingId AND cltpacksacc.cpaClient.id=:clientId")
    Optional<ClientPackagingAccount> findClientPackagingAccount(@Param("packagingId") Long packagingId, @Param("clientId") Long clientId);
    @Query("SELECT cltpacksacc FROM ClientPackagingAccount cltpacksacc WHERE cltpacksacc.cpaClient.id=:clientId ORDER BY cltpacksacc.cpaPackaging.packLabel ASC")
    Optional<List<ClientPackagingAccount>> findAllPackagingAccountofClient(@Param("clientId") Long clientId);
    @Query("SELECT cltpacksacc FROM ClientPackagingAccount cltpacksacc WHERE cltpacksacc.cpaClient.id=:clientId ORDER BY cltpacksacc.cpaPackaging.packLabel ASC")
    Optional<Page<ClientPackagingAccount>> findPagePackagingAccountofClient(@Param("clientId") Long clientId, Pageable pageable);
    @Query("SELECT cltpacksacc FROM ClientPackagingAccount cltpacksacc WHERE cltpacksacc.cpaClient.clientPosId=:posId ORDER BY cltpacksacc.cpaPackaging.packLabel ASC")
    Optional<List<ClientPackagingAccount>> findAllPackagingAccountinPos(@Param("posId") Long posId);
    @Query("SELECT cltpacksacc FROM ClientPackagingAccount cltpacksacc WHERE cltpacksacc.cpaClient.clientPosId=:posId ORDER BY cltpacksacc.cpaPackaging.packLabel ASC")
    Optional<Page<ClientPackagingAccount>> findPagePackagingAccountinPos(@Param("posId") Long posId, Pageable pageable);
}
