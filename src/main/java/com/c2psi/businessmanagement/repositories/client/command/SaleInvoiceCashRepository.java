package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.SaleInvoiceCash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleInvoiceCashRepository
        extends JpaRepository<SaleInvoiceCash, Long> {
}
