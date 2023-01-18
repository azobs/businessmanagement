package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderPackagingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderPackagingAccountRepository
        extends JpaRepository<ProviderPackagingAccount, Long> {
}
