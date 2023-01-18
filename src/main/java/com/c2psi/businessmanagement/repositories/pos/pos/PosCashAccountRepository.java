package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.models.PosCashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosCashAccountRepository
        extends JpaRepository<PosCashAccount, Long> {
    Optional<PosCashAccount> findPosCashAccountById(Long pcaId);
}
