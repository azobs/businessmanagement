package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientCashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientCashAccountRepository
        extends JpaRepository<ClientCashAccount, Long> {
    Optional<ClientCashAccount> findClientCashAccountById(Long ccaId);
}
