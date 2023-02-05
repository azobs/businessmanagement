package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderPackagingOperationValidatorTest {

    @Test
    public void validate() {
        ProviderPackagingOperationDto providerPackagingOperationDto = ProviderPackagingOperationDto.builder()
                .propoOperationDto(OperationDto.builder().build())
                .propoProPackagingAccountDto(ProviderPackagingAccountDto.builder().build())
                .propoUserbmDto(UserBMDto.builder().build())
                .propoNumberinmvt(Integer.valueOf(1))
                .build();

        List<String> errors = ProviderPackagingOperationValidator.validate(providerPackagingOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The provider name cannot be blank"));
    }

    @Test
    public void validateNull() {
        ProviderPackagingOperationDto providerPackagingOperationDto = ProviderPackagingOperationDto.builder()
                .propoOperationDto(OperationDto.builder().build())
                .propoProPackagingAccountDto(ProviderPackagingAccountDto.builder().build())
                .propoUserbmDto(UserBMDto.builder().build())
                .propoNumberinmvt(Integer.valueOf(1))
                .build();

        List<String> errors = ProviderPackagingOperationValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre--"));
    }

    @Test
    public void validateNullValue() {
        ProviderPackagingOperationDto providerPackagingOperationDto = ProviderPackagingOperationDto.builder()
                .propoOperationDto(null)
                .propoProPackagingAccountDto(null)
                .propoUserbmDto(null)
                .propoNumberinmvt(null)
                .build();

        List<String> errors = ProviderPackagingOperationValidator.validate(providerPackagingOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The packaging operation cannot be null"));
        assertTrue(errors.contains("The number of packaging in mvt cannot be null"));
        assertTrue(errors.contains("The provider packaging account cannot be null"));
        assertTrue(errors.contains("The userbm associated with the operation cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        ProviderPackagingOperationDto providerPackagingOperationDto = ProviderPackagingOperationDto.builder()
                .propoOperationDto(OperationDto.builder().build())
                .propoProPackagingAccountDto(ProviderPackagingAccountDto.builder().build())
                .propoUserbmDto(UserBMDto.builder().build())
                .propoNumberinmvt(Integer.valueOf(0))
                .build();

        List<String> errors = ProviderPackagingOperationValidator.validate(providerPackagingOperationDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The number of packaging in mvt must be positive"));
    }
}