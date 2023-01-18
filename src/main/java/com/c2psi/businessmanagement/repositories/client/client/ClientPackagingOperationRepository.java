package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientPackagingOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientPackagingOperationRepository
        extends JpaRepository<ClientPackagingOperation, Long> {
}
