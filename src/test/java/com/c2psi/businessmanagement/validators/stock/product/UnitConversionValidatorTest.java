package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitConversionValidatorTest {

    @Test
    public void validate() {
        UnitConversionDto unitConversionDto = UnitConversionDto.builder()
                .unitDestinationDto(UnitDto.builder().build())
                .unitSourceDto(UnitDto.builder().build())
                .conversionFactor(BigDecimal.valueOf(2))
                .build();

        List<String> errors = UnitConversionValidator.validate(unitConversionDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The point of sale associated cannot be null"));
    }

    @Test
    public void validateNull() {
        UnitConversionDto unitConversionDto = UnitConversionDto.builder()
                .unitDestinationDto(UnitDto.builder().build())
                .unitSourceDto(UnitDto.builder().build())
                .conversionFactor(BigDecimal.valueOf(2))
                .build();

        List<String> errors = UnitConversionValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        UnitConversionDto unitConversionDto = UnitConversionDto.builder()
                .unitDestinationDto(null)
                .unitSourceDto(null)
                .conversionFactor(null)
                .build();

        List<String> errors = UnitConversionValidator.validate(unitConversionDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The conversion factor cannot be null"));
        assertTrue(errors.contains("The source unit(conditionnement) cannot be null"));
        assertTrue(errors.contains("The destination unit(conditionnement) cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        UnitConversionDto unitConversionDto = UnitConversionDto.builder()
                .unitDestinationDto(UnitDto.builder().build())
                .unitSourceDto(UnitDto.builder().build())
                .conversionFactor(BigDecimal.valueOf(0))
                .build();

        List<String> errors = UnitConversionValidator.validate(unitConversionDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The conversion factor must be positive"));
    }
}