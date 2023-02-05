package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderCashOperationValidatorTest {

    @Test
    public void validate() {
        ProviderCashOperationDto providerCashOperationDto = ProviderCashOperationDto.builder()
                .pcoOperationDto(OperationDto.builder().build())
                .pcoProCashAccountDto(ProviderCashAccountDto.builder().build())
                .pcoUserbmDto(UserBMDto.builder().build())
                .pcoAmountinmvt(BigDecimal.valueOf(10))
                .build();
        List<String> errors = ProviderCashOperationValidator.validate(providerCashOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The provider account balance cannot be null"));
    }

    @Test
    public void validateNull() {
        ProviderCashOperationDto providerCashOperationDto = ProviderCashOperationDto.builder()
                .pcoOperationDto(OperationDto.builder().build())
                .pcoProCashAccountDto(ProviderCashAccountDto.builder().build())
                .pcoUserbmDto(UserBMDto.builder().build())
                .pcoAmountinmvt(BigDecimal.valueOf(10))
                .build();
        List<String> errors = ProviderCashOperationValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderCashOperationDto providerCashOperationDto = ProviderCashOperationDto.builder()
                .pcoOperationDto(null)
                .pcoProCashAccountDto(null)
                .pcoUserbmDto(null)
                .pcoAmountinmvt(null)
                .build();
        List<String> errors = ProviderCashOperationValidator.validate(providerCashOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The cash operation cannot be null"));
        assertTrue(errors.contains("The amount in mvt in the operation cannot be null"));
        assertTrue(errors.contains("The provider cash account associated cannot be null"));
        assertTrue(errors.contains("The userbm associated with the operation cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        ProviderCashOperationDto providerCashOperationDto = ProviderCashOperationDto.builder()
                .pcoOperationDto(OperationDto.builder().build())
                .pcoProCashAccountDto(ProviderCashAccountDto.builder().build())
                .pcoUserbmDto(UserBMDto.builder().build())
                .pcoAmountinmvt(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ProviderCashOperationValidator.validate(providerCashOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The amount in mvt in the operation must be positive"));
    }

}