package com.c2psi.businessmanagement.repositories.stock.price;

import com.c2psi.businessmanagement.models.SpecialPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialPriceRepository extends JpaRepository<SpecialPrice, Long> {
}
