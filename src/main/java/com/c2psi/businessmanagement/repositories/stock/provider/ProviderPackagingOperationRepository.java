package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.ProviderPackagingOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderPackagingOperationRepository
        extends JpaRepository<ProviderPackagingOperation, Long> {
}
