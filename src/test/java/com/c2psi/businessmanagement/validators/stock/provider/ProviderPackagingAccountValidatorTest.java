package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderPackagingAccountValidatorTest {

    @Test
    public void validate() {
        ProviderPackagingAccountDto providerPackagingAccountDto = ProviderPackagingAccountDto.builder()
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaProviderDto(ProviderDto.builder().build())
                .ppaNumber(Integer.valueOf(10))
                .build();

        List<String> errors = ProviderPackagingAccountValidator.validate(providerPackagingAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The provider name cannot be blank"));
    }

    @Test
    public void validateNull() {
        ProviderPackagingAccountDto providerPackagingAccountDto = ProviderPackagingAccountDto.builder()
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaProviderDto(ProviderDto.builder().build())
                .ppaNumber(Integer.valueOf(10))
                .build();

        List<String> errors = ProviderPackagingAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderPackagingAccountDto providerPackagingAccountDto = ProviderPackagingAccountDto.builder()
                .ppaPackagingDto(null)
                .ppaProviderDto(null)
                .ppaNumber(null)
                .build();

        List<String> errors = ProviderPackagingAccountValidator.validate(providerPackagingAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The number in the packaging account cannot ne null"));
        assertTrue(errors.contains("The provider associated with the account cannot be null"));
        assertTrue(errors.contains("The packaging associated with the account cannot be null"));
    }
}