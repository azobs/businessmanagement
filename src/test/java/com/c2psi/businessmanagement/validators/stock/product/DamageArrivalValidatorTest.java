package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.DamageArrivalDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DamageArrivalValidatorTest {

    @Test
    public void validate() {
        DamageArrivalDto damageArrivalDto = DamageArrivalDto.builder()
                .damaArt(ArticleDto.builder().build())
                .damaDeliveryquantity(Integer.valueOf(10))
                .damaQuantityartchanged(Integer.valueOf(10))
                .build();

        List<String> errors = DamageArrivalValidator.validate(damageArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        DamageArrivalDto damageArrivalDto = DamageArrivalDto.builder()
                .damaArt(ArticleDto.builder().build())
                .damaDeliveryquantity(Integer.valueOf(10))
                .damaQuantityartchanged(Integer.valueOf(10))
                .build();

        List<String> errors = DamageArrivalValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        DamageArrivalDto damageArrivalDto = DamageArrivalDto.builder()
                .damaArt(null)
                .damaDeliveryquantity(null)
                .damaQuantityartchanged(null)
                .build();

        List<String> errors = DamageArrivalValidator.validate(damageArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The delivery quantity cannot be null"));
        assertTrue(errors.contains("The quantity changed cannot be null"));
        assertTrue(errors.contains("The article associated to the damage arrival cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        DamageArrivalDto damageArrivalDto = DamageArrivalDto.builder()
                .damaArt(ArticleDto.builder().build())
                .damaDeliveryquantity(Integer.valueOf(-10))
                .damaQuantityartchanged(Integer.valueOf(-10))
                .build();

        List<String> errors = DamageArrivalValidator.validate(damageArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The delivery quantity must be positive"));
        assertTrue(errors.contains("The quantity changed must be positive"));
    }
}