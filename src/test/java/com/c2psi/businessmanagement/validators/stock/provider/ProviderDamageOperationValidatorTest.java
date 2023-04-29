package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderDamageOperationValidatorTest {

    @Test
    public void validate() {
        ProviderDamageOperationDto providerDamageOperationDto = ProviderDamageOperationDto.builder()
                .prodoOperationDto(OperationDto.builder().build())
                .prodoProDamageAccountDto(ProviderDamageAccountDto.builder().build())
                .prodoUserbmDto(UserBMDto.builder().build())
                .prodoNumberinmvt(BigDecimal.valueOf(10))
                .build();
        List<String> errors = ProviderDamageOperationValidator.validate(providerDamageOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The number of damage product cannot be null"));
    }

    @Test
    public void validateNull() {
        ProviderDamageOperationDto providerDamageOperationDto = ProviderDamageOperationDto.builder()
                .prodoOperationDto(OperationDto.builder().build())
                .prodoProDamageAccountDto(ProviderDamageAccountDto.builder().build())
                .prodoUserbmDto(UserBMDto.builder().build())
                .prodoNumberinmvt(BigDecimal.valueOf(12))
                .build();
        List<String> errors = ProviderDamageOperationValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderDamageOperationDto providerDamageOperationDto = ProviderDamageOperationDto.builder()
                .prodoOperationDto(null)
                .prodoProDamageAccountDto(null)
                .prodoUserbmDto(null)
                .prodoNumberinmvt(null)
                .build();
        List<String> errors = ProviderDamageOperationValidator.validate(providerDamageOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The damage operation cannot be null"));
        assertTrue(errors.contains("The number in mvt in the damage operation cannot be null"));
        assertTrue(errors.contains("The damage account cannot be null"));
        assertTrue(errors.contains("The userbm associated with the operation cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        ProviderDamageOperationDto providerDamageOperationDto = ProviderDamageOperationDto.builder()
                .prodoOperationDto(OperationDto.builder().build())
                .prodoProDamageAccountDto(ProviderDamageAccountDto.builder().build())
                .prodoUserbmDto(UserBMDto.builder().build())
                .prodoNumberinmvt(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ProviderDamageOperationValidator.validate(providerDamageOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The number in mvt in the damage operation must be positive"));
    }
}