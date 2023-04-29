package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientCapsuleAccount;
import com.c2psi.businessmanagement.models.ClientCapsuleAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientCapsuleAccountRepository
        extends JpaRepository<ClientCapsuleAccount, Long> {

    Optional<ClientCapsuleAccount> findClientCapsuleAccountById(Long ccaccId);
    @Query("SELECT ccacc FROM ClientCapsuleAccount ccacc WHERE ccacc.ccsaClient.id=:ClientId AND ccacc.ccsaArticle.id=:artId")
    Optional<ClientCapsuleAccount> findClientCapsuleAccountofArticleinPos(@Param("ClientId") Long ClientId,
                                                                              @Param("artId") Long artId);
    @Query("SELECT ccacc FROM ClientCapsuleAccount ccacc WHERE ccacc.ccsaClient.id=:ClientId")
    Optional<List<ClientCapsuleAccount>> findAllClientCapsuleAccountinPos(@Param("ClientId") Long ClientId);
    @Query("SELECT ccacc FROM ClientCapsuleAccount ccacc WHERE ccacc.ccsaClient.id=:ClientId")
    Optional<Page<ClientCapsuleAccount>> findPageClientCapsuleAccountinPos(@Param("ClientId") Long ClientId,
                                                                               Pageable pageable);

}
