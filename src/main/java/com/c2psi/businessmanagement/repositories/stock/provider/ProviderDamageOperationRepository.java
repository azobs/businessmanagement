package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderDamageOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDamageOperationRepository
        extends JpaRepository<ProviderDamageOperation, Long> {
}
