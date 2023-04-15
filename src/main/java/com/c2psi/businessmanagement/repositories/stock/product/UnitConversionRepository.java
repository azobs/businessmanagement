package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.CurrencyConversion;
import com.c2psi.businessmanagement.models.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnitConversionRepository
        extends JpaRepository<UnitConversion, Long> {
    Optional<UnitConversion> findUnitConversionById(Long unitconvId);
    @Query("SELECT unitconv FROM UnitConversion  unitconv WHERE unitconv.unitSource.id=:unitSourceId AND unitconv.unitDestination.id=:unitDestinationId")
    Optional<UnitConversion> findConversionRuleBetween(Long unitSourceId, Long unitDestinationId);
    @Query("SELECT unitconv FROM UnitConversion unitconv WHERE unitconv.unitSource.id=:unitSourceId")
    Optional<List<UnitConversion>> findAllUnitConversionLinkWith(Long unitSourceId);
}
