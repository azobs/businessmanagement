package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
