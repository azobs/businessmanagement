package com.c2psi.businessmanagement.validators.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeliveryValidatorTest {

    @Test
    public void validate() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryUserbmDto(UserBMDto.builder().build())
                .deliveryCode("dsds")
                .deliveryComment(null)
                .deliveryDate(new Date().toInstant())
                .deliveryState(DeliveryState.Delivery)
                .build();

        List<String> errors = DeliveryValidator.validate(deliveryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryUserbmDto(UserBMDto.builder().build())
                .deliveryCode("dsds")
                .deliveryComment(null)
                .deliveryDate(new Date().toInstant())
                .deliveryState(DeliveryState.Delivery)
                .build();

        List<String> errors = DeliveryValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryUserbmDto(null)
                .deliveryCode(null)
                .deliveryComment(null)
                .deliveryDate(null)
                .deliveryState(null)
                .build();

        List<String> errors = DeliveryValidator.validate(deliveryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The delivery code cannot be null"));
        assertTrue(errors.contains("The delivery code cannot be empty"));
        assertTrue(errors.contains("The delivery code cannot be blank"));
        assertTrue(errors.contains("The delivery date cannot be null"));
        assertTrue(errors.contains("The delivery state cannot be null"));
        assertTrue(errors.contains("The userbm associated with the delivery cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryUserbmDto(UserBMDto.builder().build())
                .deliveryCode("")
                .deliveryComment(null)
                .deliveryDate(new Date().toInstant())
                .deliveryState(DeliveryState.Delivery)
                .build();

        List<String> errors = DeliveryValidator.validate(deliveryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The delivery code cannot be empty"));
        assertTrue(errors.contains("The delivery code cannot be blank"));
        assertTrue(errors.contains("The delivery code size must be between 3 and 25 characters"));
    }

    @Test
    public void validateBlankValue() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryUserbmDto(UserBMDto.builder().build())
                .deliveryCode("     ")
                .deliveryComment(null)
                .deliveryDate(new Date().toInstant())
                .deliveryState(DeliveryState.Delivery)
                .build();

        List<String> errors = DeliveryValidator.validate(deliveryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The delivery code cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryUserbmDto(UserBMDto.builder().build())
                .deliveryCode("sdsdsdsdsdsdsdsdsdsdsdssdsdsdsdsdsdsdsdsdsdsdsdsds")
                .deliveryComment(null)
                .deliveryDate(new Date().toInstant())
                .deliveryState(DeliveryState.Delivery)
                .build();

        List<String> errors = DeliveryValidator.validate(deliveryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The delivery code size must be between 3 and 25 characters"));
    }



}