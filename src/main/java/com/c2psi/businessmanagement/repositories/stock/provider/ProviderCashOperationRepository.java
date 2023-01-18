package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderCashOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderCashOperationRepository
        extends JpaRepository<ProviderCashOperation, Long> {
}
