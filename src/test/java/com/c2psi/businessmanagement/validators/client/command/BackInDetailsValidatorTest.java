package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BackInDetailsValidatorTest {

    @Test
    public void validate() {
        BackInDetailsDto backInDetailsDto = BackInDetailsDto.builder()
                .bidXArticleDto(ArticleDto.builder().build())
                .bidbiDto(BackInDto.builder().build())
                .bidComment(null)
                .bidQuantity(BigDecimal.valueOf(15))
                .build();

        List<String> errors = BackInDetailsValidator.validate(backInDetailsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        BackInDetailsDto backInDetailsDto = BackInDetailsDto.builder()
                .bidXArticleDto(ArticleDto.builder().build())
                .bidbiDto(BackInDto.builder().build())
                .bidComment(null)
                .bidQuantity(BigDecimal.valueOf(15))
                .build();

        List<String> errors = BackInDetailsValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le  parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        BackInDetailsDto backInDetailsDto = BackInDetailsDto.builder()
                .bidXArticleDto(null)
                .bidbiDto(null)
                .bidComment(null)
                .bidQuantity(null)
                .build();

        List<String> errors = BackInDetailsValidator.validate(backInDetailsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The backindetails quantity cannot be null"));
        assertTrue(errors.contains("The backindetails article cannot be null"));
        assertTrue(errors.contains("The backindetails backin cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        BackInDetailsDto backInDetailsDto = BackInDetailsDto.builder()
                .bidXArticleDto(ArticleDto.builder().build())
                .bidbiDto(BackInDto.builder().build())
                .bidComment(null)
                .bidQuantity(BigDecimal.valueOf(0))
                .build();

        List<String> errors = BackInDetailsValidator.validate(backInDetailsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The backindetails quantity must be positive"));
    }

}