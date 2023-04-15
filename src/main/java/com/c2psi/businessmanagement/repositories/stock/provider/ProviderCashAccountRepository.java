package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderCashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderCashAccountRepository extends JpaRepository<ProviderCashAccount, Long> {
    Optional<ProviderCashAccount> findProviderCashAccountById(Long pcaId);
}
