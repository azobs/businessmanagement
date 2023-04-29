package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientDamageAccount;
import com.c2psi.businessmanagement.models.ClientDamageAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientDamageAccountRepository
        extends JpaRepository<ClientDamageAccount, Long> {
    Optional<ClientDamageAccount> findClientDamageAccountById(Long cdaccId);
    @Query("SELECT cdacc FROM ClientDamageAccount cdacc WHERE cdacc.cdaClient.id=:ClientId AND cdacc.cdaArticle.id=:artId")
    Optional<ClientDamageAccount> findClientDamageAccountofArticleinPos(@Param("ClientId") Long ClientId,
                                                                          @Param("artId") Long artId);
    @Query("SELECT cdacc FROM ClientDamageAccount cdacc WHERE cdacc.cdaClient.id=:ClientId")
    Optional<List<ClientDamageAccount>> findAllClientDamageAccountinPos(@Param("ClientId") Long ClientId);
    @Query("SELECT cdacc FROM ClientDamageAccount cdacc WHERE cdacc.cdaClient.id=:ClientId")
    Optional<Page<ClientDamageAccount>> findPageClientDamageAccountinPos(@Param("ClientId") Long ClientId,
                                                                           Pageable pageable);
}
