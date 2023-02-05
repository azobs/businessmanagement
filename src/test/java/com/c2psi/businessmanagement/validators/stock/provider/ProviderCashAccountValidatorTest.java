package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderCashAccountValidatorTest {

    @Test
    public void validate() {
        ProviderCashAccountDto providerCashAccountDto = ProviderCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(0))
                .build();

        List<String> errors = ProviderCashAccountValidator.validate(providerCashAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The unit name size must be between 3 and 20 characters"));
    }

    @Test
    public void validateNull() {
        ProviderCashAccountDto providerCashAccountDto = ProviderCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(0))
                .build();

        List<String> errors = ProviderCashAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderCashAccountDto providerCashAccountDto = ProviderCashAccountDto.builder()
                .pcaBalance(null)
                .build();

        List<String> errors = ProviderCashAccountValidator.validate(providerCashAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The provider account balance cannot be null"));
    }
}