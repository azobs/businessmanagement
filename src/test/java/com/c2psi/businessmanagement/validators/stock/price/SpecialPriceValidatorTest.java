package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpecialPriceValidatorTest {

    @Test
    public void validate() {
        SpecialPriceDto specialPriceDto = SpecialPriceDto.builder()
                .spBasepriceDto(BasePriceDto.builder().build())
                .spWholesaleprice(BigDecimal.valueOf(100))
                .spPrecompte(BigDecimal.valueOf(100))
                .spRistourne(BigDecimal.valueOf(100))
                .spSemiwholesaleprice(BigDecimal.valueOf(100))
                .spDetailprice(BigDecimal.valueOf(100))
                .build();

        List<String> errors = SpecialPriceValidator.validate(specialPriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        SpecialPriceDto specialPriceDto = SpecialPriceDto.builder()
                .spBasepriceDto(BasePriceDto.builder().build())
                .spWholesaleprice(BigDecimal.valueOf(100))
                .spPrecompte(BigDecimal.valueOf(100))
                .spRistourne(BigDecimal.valueOf(100))
                .spSemiwholesaleprice(BigDecimal.valueOf(100))
                .spDetailprice(BigDecimal.valueOf(100))
                .build();

        List<String> errors = SpecialPriceValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre  a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        SpecialPriceDto specialPriceDto = SpecialPriceDto.builder()
                .spBasepriceDto(null)
                .spWholesaleprice(null)
                .spPrecompte(null)
                .spRistourne(null)
                .spSemiwholesaleprice(null)
                .spDetailprice(null)
                .build();

        List<String> errors = SpecialPriceValidator.validate(specialPriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The special whole price cannot be null"));
        assertTrue(errors.contains("The special detail price cannot be null"));
        assertTrue(errors.contains("The special semi whole price cannot be null"));
        assertTrue(errors.contains("The special precompte cannot be null"));
        assertTrue(errors.contains("The special ristourne cannot be null"));
        assertTrue(errors.contains("The base price associated cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        SpecialPriceDto specialPriceDto = SpecialPriceDto.builder()
                .spBasepriceDto(BasePriceDto.builder().build())
                .spWholesaleprice(BigDecimal.valueOf(0))
                .spPrecompte(BigDecimal.valueOf(-100))
                .spRistourne(BigDecimal.valueOf(-100))
                .spSemiwholesaleprice(BigDecimal.valueOf(0))
                .spDetailprice(BigDecimal.valueOf(0))
                .build();

        List<String> errors = SpecialPriceValidator.validate(specialPriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(5, errors.size());
        assertTrue(errors.contains("The special whole price must be positive"));
        assertTrue(errors.contains("The special detail price must be positive"));
        assertTrue(errors.contains("The special semi whole price must be positive"));
        assertTrue(errors.contains("The special ristourne must be positive or zero"));
        assertTrue(errors.contains("The special precompte must be positive or zero"));
    }



}