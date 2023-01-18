package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.InventoryLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLineRepository extends JpaRepository<InventoryLine, Long> {

}
