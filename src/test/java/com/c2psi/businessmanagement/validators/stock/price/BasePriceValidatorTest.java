package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.validators.client.delivery.DeliveryValidator;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasePriceValidatorTest {

    @Test
    public void validate() {
        BasePriceDto basePriceDto = BasePriceDto.builder()
                .bpCurrencyDto(CurrencyDto.builder().build())
                .bpDetailprice(BigDecimal.valueOf(1000))
                .bpPrecompte(BigDecimal.valueOf(100))
                .bpPurchaseprice(BigDecimal.valueOf(1000))
                .bpRistourne(BigDecimal.valueOf(250))
                .bpSemiwholesaleprice(BigDecimal.valueOf(1000))
                .bpWholesaleprice(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = BasePriceValidator.validate(basePriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        BasePriceDto basePriceDto = BasePriceDto.builder()
                .bpCurrencyDto(CurrencyDto.builder().build())
                .bpDetailprice(BigDecimal.valueOf(1000))
                .bpPrecompte(BigDecimal.valueOf(100))
                .bpPurchaseprice(BigDecimal.valueOf(1000))
                .bpRistourne(BigDecimal.valueOf(250))
                .bpSemiwholesaleprice(BigDecimal.valueOf(1000))
                .bpWholesaleprice(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = BasePriceValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        BasePriceDto basePriceDto = BasePriceDto.builder()
                .bpCurrencyDto(null)
                .bpDetailprice(null)
                .bpPrecompte(null)
                .bpPurchaseprice(null)
                .bpRistourne(null)
                .bpSemiwholesaleprice(null)
                .bpWholesaleprice(null)
                .build();

        List<String> errors = BasePriceValidator.validate(basePriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(7, errors.size());
        assertTrue(errors.contains("The purchase price cannot be null"));
        assertTrue(errors.contains("The sale whole price cannot be null"));
        assertTrue(errors.contains("The sale details price cannot be null"));
        assertTrue(errors.contains("The sale semi whole price cannot be null"));
        assertTrue(errors.contains("The procompte value cannot be null"));
        assertTrue(errors.contains("The ristourne value cannot be null"));
        assertTrue(errors.contains("The currency associated to the price cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        BasePriceDto basePriceDto = BasePriceDto.builder()
                .bpCurrencyDto(CurrencyDto.builder().build())
                .bpDetailprice(BigDecimal.valueOf(0))
                .bpPrecompte(BigDecimal.valueOf(-100))
                .bpPurchaseprice(BigDecimal.valueOf(0))
                .bpRistourne(BigDecimal.valueOf(-250))
                .bpSemiwholesaleprice(BigDecimal.valueOf(0))
                .bpWholesaleprice(BigDecimal.valueOf(0))
                .build();

        List<String> errors = BasePriceValidator.validate(basePriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The purchase price cannot be null"));
        assertTrue(errors.contains("The sale whole price must be positive"));
        assertTrue(errors.contains("The sale details price must be positive"));
        assertTrue(errors.contains("The sale semi whole price must be positive"));
        assertTrue(errors.contains("The procompte value must be positive or null"));
        assertTrue(errors.contains("The ristourne value must be positive or null"));
    }

    @Test
    public void validateDetailsAndSemiWholePrice() {
        BasePriceDto basePriceDto = BasePriceDto.builder()
                .bpCurrencyDto(CurrencyDto.builder().build())
                .bpDetailprice(BigDecimal.valueOf(900))
                .bpPrecompte(BigDecimal.valueOf(100))
                .bpPurchaseprice(BigDecimal.valueOf(1000))
                .bpRistourne(BigDecimal.valueOf(250))
                .bpSemiwholesaleprice(BigDecimal.valueOf(1000))
                .bpWholesaleprice(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = BasePriceValidator.validate(basePriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le prix de details ne saurait etre inferieur au prix de semi gros--"));
        assertTrue(errors.contains("--Le prix de details ne saurait etre inferieur au prix de gros--"));
    }

    @Test
    public void validateSemiWholeAndWholePrice() {
        BasePriceDto basePriceDto = BasePriceDto.builder()
                .bpCurrencyDto(CurrencyDto.builder().build())
                .bpDetailprice(BigDecimal.valueOf(1900))
                .bpPrecompte(BigDecimal.valueOf(100))
                .bpPurchaseprice(BigDecimal.valueOf(1000))
                .bpRistourne(BigDecimal.valueOf(250))
                .bpSemiwholesaleprice(BigDecimal.valueOf(1000))
                .bpWholesaleprice(BigDecimal.valueOf(1200))
                .build();

        List<String> errors = BasePriceValidator.validate(basePriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le prix de semi gros ne saurait etre inferieur au prix de gros--"));
    }


}