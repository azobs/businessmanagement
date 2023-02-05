package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CapsuleArrivalValidatorTest {

    @Test
    public void validate() {
        CapsuleArrivalDto capsuleArrivalDto = CapsuleArrivalDto.builder()
                .capsaArtDto(ArticleDto.builder().build())
                .capsaSicapsDto(null)
                .capsaDeliveryquantity(Integer.valueOf(100))
                .capsaQuantitycapschanged(Integer.valueOf(100))
                .build();

        List<String> errors = CapsuleArrivalValidator.validate(capsuleArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CapsuleArrivalDto capsuleArrivalDto = CapsuleArrivalDto.builder()
                .capsaArtDto(ArticleDto.builder().build())
                .capsaSicapsDto(null)
                .capsaDeliveryquantity(Integer.valueOf(100))
                .capsaQuantitycapschanged(Integer.valueOf(100))
                .build();

        List<String> errors = CapsuleArrivalValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        CapsuleArrivalDto capsuleArrivalDto = CapsuleArrivalDto.builder()
                .capsaArtDto(null)
                .capsaSicapsDto(null)
                .capsaDeliveryquantity(null)
                .capsaQuantitycapschanged(null)
                .build();

        List<String> errors = CapsuleArrivalValidator.validate(capsuleArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The delivery quantity cannot be null"));
        assertTrue(errors.contains("The quantity capsule changed cannot be null"));
        assertTrue(errors.contains("The article associated with the capsule arrival cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        CapsuleArrivalDto capsuleArrivalDto = CapsuleArrivalDto.builder()
                .capsaArtDto(ArticleDto.builder().build())
                .capsaSicapsDto(null)
                .capsaDeliveryquantity(Integer.valueOf(-100))
                .capsaQuantitycapschanged(Integer.valueOf(-100))
                .build();

        List<String> errors = CapsuleArrivalValidator.validate(capsuleArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The delivery quantity of capsule changed must be positive"));
        assertTrue(errors.contains("The quantity capsule changed must be positive"));
    }



}