package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyConversionValidatorTest {

    @Test
    public void validate() {
        CurrencyConversionDto currencyConversionDto = CurrencyConversionDto.builder()
                .currencySourceDto(CurrencyDto.builder().build())
                .currencyDestinationDto(CurrencyDto.builder().build())
                .conversionFactor(BigDecimal.valueOf(650))
                .build();

        List<String> errors = CurrencyConversionValidator.validate(currencyConversionDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CurrencyConversionDto currencyConversionDto = CurrencyConversionDto.builder()
                .currencySourceDto(CurrencyDto.builder().build())
                .currencyDestinationDto(CurrencyDto.builder().build())
                .conversionFactor(BigDecimal.valueOf(650))
                .build();

        List<String> errors = CurrencyConversionValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        CurrencyConversionDto currencyConversionDto = CurrencyConversionDto.builder()
                .currencySourceDto(null)
                .currencyDestinationDto(null)
                .conversionFactor(null)
                .build();

        List<String> errors = CurrencyConversionValidator.validate(currencyConversionDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The conversion factor cannot be null"));
        assertTrue(errors.contains("The currency source of the rule cannot be null"));
        assertTrue(errors.contains("The currency destination of the rule cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        CurrencyConversionDto currencyConversionDto = CurrencyConversionDto.builder()
                .currencySourceDto(CurrencyDto.builder().build())
                .currencyDestinationDto(CurrencyDto.builder().build())
                .conversionFactor(BigDecimal.valueOf(0))
                .build();

        List<String> errors = CurrencyConversionValidator.validate(currencyConversionDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The converion factor must be positive"));
    }



}