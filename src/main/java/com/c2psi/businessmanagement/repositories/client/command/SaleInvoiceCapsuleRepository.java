package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.SaleInvoiceCapsule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleInvoiceCapsuleRepository
        extends JpaRepository<SaleInvoiceCapsule, Long> {
}
