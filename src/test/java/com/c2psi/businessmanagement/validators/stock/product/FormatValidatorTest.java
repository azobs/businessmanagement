package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FormatValidatorTest {

    @Test
    public void validate() {
        FormatDto formatDto = FormatDto.builder()
                .formatPosId(PointofsaleDto.builder().build().getId())
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatName("sdsds")
                .build();

        List<String> errors = FormatValidator.validate(formatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        FormatDto formatDto = FormatDto.builder()
                .formatPosId(PointofsaleDto.builder().build().getId())
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatName("sdsds")
                .build();

        List<String> errors = FormatValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        FormatDto formatDto = FormatDto.builder()
                .formatPosId(null)
                .formatCapacity(null)
                .formatName(null)
                .build();

        List<String> errors = FormatValidator.validate(formatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(5, errors.size());
        assertTrue(errors.contains("The format name cannot be null"));
        assertTrue(errors.contains("The format name cannot be empty"));
        assertTrue(errors.contains("The format name cannot be blank"));
        assertTrue(errors.contains("The capacity cannot be null"));
        assertTrue(errors.contains("The point of sale associated to the format cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        FormatDto formatDto = FormatDto.builder()
                .formatPosId(PointofsaleDto.builder().build().getId())
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatName("")
                .build();

        List<String> errors = FormatValidator.validate(formatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The format name cannot be empty"));
        assertTrue(errors.contains("The format name cannot be blank"));
        assertTrue(errors.contains("The format name size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        FormatDto formatDto = FormatDto.builder()
                .formatPosId(PointofsaleDto.builder().build().getId())
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatName("   ")
                .build();

        List<String> errors = FormatValidator.validate(formatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The format name cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        FormatDto formatDto = FormatDto.builder()
                .formatPosId(PointofsaleDto.builder().build().getId())
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatName("ashasakhsadhksajfdhkjsdfhjkasdhjsakdjsakdhskadhksa")
                .build();

        List<String> errors = FormatValidator.validate(formatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The format name size must be between 3 and 20 characters"));
    }

}