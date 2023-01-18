package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
