package com.c2psi.businessmanagement.repositories.stock.price;

import com.c2psi.businessmanagement.models.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasePriceRepository extends JpaRepository<BasePrice, Long> {
    Optional<BasePrice> findBasePriceById(Long basePriceId);
}
