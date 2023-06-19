package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderCapsuleAccountValidatorTest {

    @Test
    public void validate() {
        ProviderCapsuleAccountDto providerCapsuleAccountDto = ProviderCapsuleAccountDto.builder()
                .pcsaProviderDto(ProviderDto.builder().build())
                .pcsaArticleDto(ArticleDto.builder().build())
                .pcsaNumber(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ProviderCapsuleAccountValidator.validate(providerCapsuleAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The unit name size must be between 3 and 20 characters"));
    }

    @Test
    public void validateNull() {
        ProviderCapsuleAccountDto providerCapsuleAccountDto = ProviderCapsuleAccountDto.builder()
                .pcsaProviderDto(ProviderDto.builder().build())
                .pcsaArticleDto(ArticleDto.builder().build())
                .pcsaNumber(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ProviderCapsuleAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderCapsuleAccountDto providerCapsuleAccountDto = ProviderCapsuleAccountDto.builder()
                .pcsaProviderDto(null)
                .pcsaArticleDto(null)
                .pcsaNumber(null)
                .build();
        List<String> errors = ProviderCapsuleAccountValidator.validate(providerCapsuleAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The number of capsule in the account cannot be null"));
        assertTrue(errors.contains("The provider associated cannot be null"));
        assertTrue(errors.contains("The article associated cannot be null"));
    }
}