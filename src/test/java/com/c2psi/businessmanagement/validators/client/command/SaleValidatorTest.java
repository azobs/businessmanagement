package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.Enumerations.SaleType;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SaleValidatorTest {

    @Test
    public void validate() {
        SaleDto saleDto = SaleDto.builder()
                .saleCommandDto(CommandDto.builder().build())
                .saleArticleDto(ArticleDto.builder().build())
                .saleFinalprice(BigDecimal.valueOf(1000))
                .saleQuantity(BigDecimal.valueOf(10))
                .saleComment("  ")
                .saleType(SaleType.Details)
                .build();

        List<String> errors = SaleValidator.validate(saleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        SaleDto saleDto = SaleDto.builder()
                .saleCommandDto(CommandDto.builder().build())
                .saleArticleDto(ArticleDto.builder().build())
                .saleFinalprice(BigDecimal.valueOf(1000))
                .saleQuantity(BigDecimal.valueOf(10))
                .saleComment("  ")
                .saleType(SaleType.Details)
                .build();

        List<String> errors = SaleValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        SaleDto saleDto = SaleDto.builder()
                .saleCommandDto(null)
                .saleArticleDto(null)
                .saleFinalprice(null)
                .saleQuantity(null)
                .saleComment("  ")
                .saleType(null)
                .build();

        List<String> errors = SaleValidator.validate(saleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(5, errors.size());
        assertTrue(errors.contains("The command code cannot be null"));
        assertTrue(errors.contains("The sale final price cannot be null"));
        assertTrue(errors.contains("The sale type cannot be null"));
        assertTrue(errors.contains("The command associated to the sale details cannot be null"));
        assertTrue(errors.contains("The article associated to the sale details cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        SaleDto saleDto = SaleDto.builder()
                .saleCommandDto(CommandDto.builder().build())
                .saleArticleDto(ArticleDto.builder().build())
                .saleFinalprice(BigDecimal.valueOf(0))
                .saleQuantity(BigDecimal.valueOf(0))
                .saleComment("  ")
                .saleType(SaleType.Details)
                .build();

        List<String> errors = SaleValidator.validate(saleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The sale quantity sale must be positive"));
        assertTrue(errors.contains("The sale final price must be positive"));
    }


}