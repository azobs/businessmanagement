package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitConversionRepository
        extends JpaRepository<UnitConversion, Long> {
}
