package com.c2psi.businessmanagement.repositories.stock.price;

import com.c2psi.businessmanagement.models.CurrencyConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CurrencyConversionRepository
        extends JpaRepository<CurrencyConversion, Long> {
    Optional<CurrencyConversion> findCurrencyConversionById(Long curconvId);

    @Query("SELECT curconv FROM CurrencyConversion  curconv WHERE curconv.currencySource.id=:currencySourceId AND curconv.currencyDestination.id=:currencyDestinationId")
    Optional<CurrencyConversion> findConversionRuleBetween(Long currencySourceId, Long currencyDestinationId);

    @Query("SELECT curconv FROM CurrencyConversion  curconv WHERE (curconv.currencySource.id=:currencySourceId AND curconv.currencyDestination.id=:currencyDestinationId) OR (curconv.currencySource.id=:currencyDestinationId AND curconv.currencyDestination.id=:currencySourceId)")
    Optional<CurrencyConversion> findConversionRuleBetweenViceVersa(Long currencySourceId, Long currencyDestinationId);

    @Query("SELECT curconv FROM CurrencyConversion curconv WHERE curconv.currencySource.id=:currencySource")
    Optional<List<CurrencyConversion>> findAllCurrencyConversionLinkWith(Long currencySource);
}
