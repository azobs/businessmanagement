package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientDamageOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDamageOperationRepository
        extends JpaRepository<ClientDamageOperation, Long> {
}
