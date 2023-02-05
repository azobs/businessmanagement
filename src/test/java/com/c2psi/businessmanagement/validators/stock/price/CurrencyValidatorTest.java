package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyValidatorTest {

    @Test
    public void validate() {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName("dsdsds")
                .currencyShortname("dsdsds")
                .build();

        List<String> errors = CurrencyValidator.validate(currencyDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName("dsdsds")
                .currencyShortname("dsdsds")
                .build();

        List<String> errors = CurrencyValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName(null)
                .currencyShortname(null)
                .build();

        List<String> errors = CurrencyValidator.validate(currencyDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The currency name cannot be null value"));
        assertTrue(errors.contains("The currency name cannot be empty value"));
        assertTrue(errors.contains("The currency name cannot be blank value"));
        assertTrue(errors.contains("The currency shortname cannot be null value"));
        assertTrue(errors.contains("The currency shortname cannot be empty value"));
        assertTrue(errors.contains("The currency shortname cannot be blank value"));
    }

    @Test
    public void validateEmptyValue() {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName("")
                .currencyShortname("")
                .build();

        List<String> errors = CurrencyValidator.validate(currencyDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The currency name cannot be empty value"));
        assertTrue(errors.contains("The currency name cannot be blank value"));
        assertTrue(errors.contains("The currency shortname cannot be empty value"));
        assertTrue(errors.contains("The currency shortname cannot be blank value"));
    }

    @Test
    public void validateBlankValue() {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .currencyName("    ")
                .currencyShortname("      ")
                .build();

        List<String> errors = CurrencyValidator.validate(currencyDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The currency name cannot be blank value"));
        assertTrue(errors.contains("The currency shortname cannot be blank value"));
    }



}