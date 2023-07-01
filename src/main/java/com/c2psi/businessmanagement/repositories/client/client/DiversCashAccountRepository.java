package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.DiversCashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiversCashAccountRepository extends JpaRepository<DiversCashAccount, Long> {
    Optional<DiversCashAccount> findDiversCashAccountById(Long dcaId);

    Optional<DiversCashAccount> findDiversCashAccountByAndDiverscashPosId(Long diverscashPosId);

}
