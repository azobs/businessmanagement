package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientCashOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCashOperationRepository
        extends JpaRepository<ClientCashOperation, Long> {
}
