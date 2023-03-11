package com.c2psi.businessmanagement.repositories.stock.price;

import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.models.UserBM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findCurrencyById(Long id);
    Optional<Currency> findCurrencyByCurrencyNameAndAndCurrencyShortname(String currencyName,
                                                                         String currencyShortName);


}
