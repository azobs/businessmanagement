package com.c2psi.businessmanagement.repositories.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.models.SpecialPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpecialPriceRepository extends JpaRepository<SpecialPrice, Long> {
    Optional<SpecialPrice> findSpecialPriceById(Long specialPriceId);
    @Query("SELECT sp FROM SpecialPrice sp WHERE sp.spBaseprice.id=:basePriceId")
    Optional<List<SpecialPrice>> findListofSpecialPriceOf(Long basePriceId);
}
