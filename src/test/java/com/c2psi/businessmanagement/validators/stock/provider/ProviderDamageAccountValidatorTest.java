package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderDamageAccountValidatorTest {

    @Test
    public void validate() {
        ProviderDamageAccountDto providerDamageAccountDto = ProviderDamageAccountDto.builder()
                .pdaProviderDto(ProviderDto.builder().build())
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(BigDecimal.valueOf(10))
                .build();
        List<String> errors = ProviderDamageAccountValidator.validate(providerDamageAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The amount in mvt in the operation must be positive"));
    }

    @Test
    public void validateNull() {
        ProviderDamageAccountDto providerDamageAccountDto = ProviderDamageAccountDto.builder()
                .pdaProviderDto(ProviderDto.builder().build())
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(BigDecimal.valueOf(10))
                .build();
        List<String> errors = ProviderDamageAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderDamageAccountDto providerDamageAccountDto = ProviderDamageAccountDto.builder()
                .pdaProviderDto(null)
                .pdaArticleDto(null)
                .pdaNumber(null)
                .build();
        List<String> errors = ProviderDamageAccountValidator.validate(providerDamageAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The number of damage product cannot be null"));
        assertTrue(errors.contains("The provider associated with the account cannot be null"));
        assertTrue(errors.contains("The article associated with the account cannot be null"));
    }
}