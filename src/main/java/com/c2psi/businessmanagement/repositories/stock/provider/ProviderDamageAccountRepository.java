package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderDamageAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDamageAccountRepository
        extends JpaRepository<ProviderDamageAccount, Long> {
}
