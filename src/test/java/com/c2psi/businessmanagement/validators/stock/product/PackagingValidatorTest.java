package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PackagingValidatorTest {

    @Test
    public void validate() {
        PackagingDto packagingDto = PackagingDto.builder()
                .packPosDto(PointofsaleDto.builder().build())
                .packProviderDto(ProviderDto.builder().build())
                .packPrice(BigDecimal.valueOf(3600))
                .packFirstcolor("blue")
                .packDescription("   ")
                .packLabel("casier")
                .build();

        List<String> errors = PackagingValidator.validate(packagingDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        PackagingDto packagingDto = PackagingDto.builder()
                .packPosDto(PointofsaleDto.builder().build())
                .packProviderDto(ProviderDto.builder().build())
                .packPrice(BigDecimal.valueOf(3600))
                .packFirstcolor("blue")
                .packDescription("   ")
                .packLabel("casier")
                .build();

        List<String> errors = PackagingValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        PackagingDto packagingDto = PackagingDto.builder()
                .packPosDto(null)
                .packProviderDto(null)
                .packPrice(null)
                .packFirstcolor(null)
                .packDescription(null)
                .packLabel(null)
                .build();

        List<String> errors = PackagingValidator.validate(packagingDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(9, errors.size());
        assertTrue(errors.contains("The packaging label cannot be null"));
        assertTrue(errors.contains("The packaging label cannot be empty"));
        assertTrue(errors.contains("The packaging label cannot be blank"));
        assertTrue(errors.contains("The packaging color cannot be blank"));
        assertTrue(errors.contains("The packaging color cannot be empty"));
        assertTrue(errors.contains("The packaging color cannot be null"));
        assertTrue(errors.contains("The packaging price cannot be null"));
        assertTrue(errors.contains("The provider of a packaging cannot be null"));
        assertTrue(errors.contains("The point of sale associated with the packaging cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        PackagingDto packagingDto = PackagingDto.builder()
                .packPosDto(PointofsaleDto.builder().build())
                .packProviderDto(ProviderDto.builder().build())
                .packPrice(BigDecimal.valueOf(3600))
                .packFirstcolor("")
                .packDescription("")
                .packLabel("")
                .build();

        List<String> errors = PackagingValidator.validate(packagingDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The packaging label cannot be empty"));
        assertTrue(errors.contains("The packaging label cannot be blank"));
        assertTrue(errors.contains("The packaging label size must be between 3 and 20 characters"));
        assertTrue(errors.contains("The packaging color cannot be blank"));
        assertTrue(errors.contains("The packaging color cannot be empty"));
        assertTrue(errors.contains("The packaging color size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        PackagingDto packagingDto = PackagingDto.builder()
                .packPosDto(PointofsaleDto.builder().build())
                .packProviderDto(ProviderDto.builder().build())
                .packPrice(BigDecimal.valueOf(3600))
                .packFirstcolor("     ")
                .packDescription("       ")
                .packLabel("       ")
                .build();

        List<String> errors = PackagingValidator.validate(packagingDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The packaging label cannot be blank"));
        assertTrue(errors.contains("The packaging color cannot be blank"));
    }


    @Test
    public void validateSizeValue() {
        PackagingDto packagingDto = PackagingDto.builder()
                .packPosDto(PointofsaleDto.builder().build())
                .packProviderDto(ProviderDto.builder().build())
                .packPrice(BigDecimal.valueOf(-3600))
                .packFirstcolor("dsdsds")
                .packDescription("dsdsdsds")
                .packLabel("dsdsdsds")
                .build();

        List<String> errors = PackagingValidator.validate(packagingDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The packaging price must be positive or zero"));
    }

}