package com.c2psi.businessmanagement.validators.client.delivery;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeliveryDetailsValidatorTest {

    @Test
    public void validate() {
        DeliveryDetailsDto deliveryDetailsDto = DeliveryDetailsDto.builder()
                .ddDeliveryDto(DeliveryDto.builder().build())
                .ddPackagingDto(PackagingDto.builder().build())
                .ddNumberofpackageused(BigDecimal.valueOf(5))
                .ddNumberofpackagereturn(BigDecimal.valueOf(4))
                .build();

        List<String> errors = DeliveryDetailsValidator.validate(deliveryDetailsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        DeliveryDetailsDto deliveryDetailsDto = DeliveryDetailsDto.builder()
                .ddDeliveryDto(DeliveryDto.builder().build())
                .ddPackagingDto(PackagingDto.builder().build())
                .ddNumberofpackageused(BigDecimal.valueOf(5))
                .ddNumberofpackagereturn(BigDecimal.valueOf(4))
                .build();

        List<String> errors = DeliveryDetailsValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        DeliveryDetailsDto deliveryDetailsDto = DeliveryDetailsDto.builder()
                .ddDeliveryDto(null)
                .ddPackagingDto(null)
                .ddNumberofpackageused(null)
                .ddNumberofpackagereturn(null)
                .build();

        List<String> errors = DeliveryDetailsValidator.validate(deliveryDetailsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The number of package used for the delivery details cannot be null"));
        assertTrue(errors.contains("The number of package return after delivery cannot be null"));
        assertTrue(errors.contains("The packaging type used for the delivery details cannot be null"));
        assertTrue(errors.contains("The delivery associated with that delivery details cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        DeliveryDetailsDto deliveryDetailsDto = DeliveryDetailsDto.builder()
                .ddDeliveryDto(DeliveryDto.builder().build())
                .ddPackagingDto(PackagingDto.builder().build())
                .ddNumberofpackageused(BigDecimal.valueOf(0))
                .ddNumberofpackagereturn(BigDecimal.valueOf(-1))
                .build();

        List<String> errors = DeliveryDetailsValidator.validate(deliveryDetailsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The number of package used for the delivery details mut b positive"));
        assertTrue(errors.contains("The number of package return after delivery must be positive or null"));
    }


}