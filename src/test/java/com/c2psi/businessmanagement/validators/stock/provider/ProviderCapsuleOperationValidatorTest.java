package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderCapsuleOperationValidatorTest {

    @Test
    public void validate() {
        ProviderCapsuleOperationDto providerCapsuleOperationDto = ProviderCapsuleOperationDto.builder()
                .proscoOperationDto(OperationDto.builder().build())
                .procsoProCapsuleAccountDto(ProviderCapsuleAccountDto.builder().build())
                .procsoUserbmDto(UserBMDto.builder().build())
                .procsoNumberinmvt(BigDecimal.valueOf(1))
                .build();
        List<String> errors = ProviderCapsuleOperationValidator.validate(providerCapsuleOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The unit name size must be between 3 and 20 characters"));
    }

    @Test
    public void validateNull() {
        ProviderCapsuleOperationDto providerCapsuleOperationDto = ProviderCapsuleOperationDto.builder()
                .proscoOperationDto(OperationDto.builder().build())
                .procsoProCapsuleAccountDto(ProviderCapsuleAccountDto.builder().build())
                .procsoUserbmDto(UserBMDto.builder().build())
                .procsoNumberinmvt(BigDecimal.valueOf(1))
                .build();
        List<String> errors = ProviderCapsuleOperationValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderCapsuleOperationDto providerCapsuleOperationDto = ProviderCapsuleOperationDto.builder()
                .proscoOperationDto(null)
                .procsoProCapsuleAccountDto(null)
                .procsoUserbmDto(null)
                .procsoNumberinmvt(null)
                .build();
        List<String> errors = ProviderCapsuleOperationValidator.validate(providerCapsuleOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The operation associated cannot be null"));
        assertTrue(errors.contains("The number in mvt in the operation cannot be null"));
        assertTrue(errors.contains("The provider account associated cannot ne null"));
        assertTrue(errors.contains("The userbm associated with the operation cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        ProviderCapsuleOperationDto providerCapsuleOperationDto = ProviderCapsuleOperationDto.builder()
                .proscoOperationDto(OperationDto.builder().build())
                .procsoProCapsuleAccountDto(ProviderCapsuleAccountDto.builder().build())
                .procsoUserbmDto(UserBMDto.builder().build())
                .procsoNumberinmvt(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ProviderCapsuleOperationValidator.validate(providerCapsuleOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The number in mvt in the operation must be positive"));
    }
}