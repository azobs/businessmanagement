package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
