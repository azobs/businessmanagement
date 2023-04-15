package com.c2psi.businessmanagement.repositories.stock.price;

import com.c2psi.businessmanagement.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findCurrencyById(Long id);
    Optional<Currency> findCurrencyByCurrencyNameAndAndCurrencyShortname(String currencyName,
                                                                         String currencyShortName);




}
