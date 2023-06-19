package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitValidatorTest {

    @Test
    public void validate() {
        UnitDto unitDto = UnitDto.builder()
                .unitPosId(PointofsaleDto.builder().build().getId())
                .unitName("sdsds")
                .unitAbbreviation("dd")
                .build();
        List<String> errors = UnitValidator.validate(unitDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The conversion factor must be positive"));
    }

    @Test
    public void validateNull() {
        UnitDto unitDto = UnitDto.builder()
                .unitPosId(PointofsaleDto.builder().build().getId())
                .unitName("sdsds")
                .unitAbbreviation("dd")
                .build();
        List<String> errors = UnitValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        UnitDto unitDto = UnitDto.builder()
                .unitPosId(null)
                .unitName(null)
                .unitAbbreviation(null)
                .build();
        List<String> errors = UnitValidator.validate(unitDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The unit name cannot be null"));
        assertTrue(errors.contains("The unit name cannot be empty"));
        assertTrue(errors.contains("The unit name cannot be blank"));
        assertTrue(errors.contains("The pointofsale associated cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        UnitDto unitDto = UnitDto.builder()
                .unitPosId(PointofsaleDto.builder().build().getId())
                .unitName("")
                .unitAbbreviation("")
                .build();
        List<String> errors = UnitValidator.validate(unitDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The unit name cannot be empty"));
        assertTrue(errors.contains("The unit name cannot be blank"));
        assertTrue(errors.contains("The unit name size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        UnitDto unitDto = UnitDto.builder()
                .unitPosId(PointofsaleDto.builder().build().getId())
                .unitName("       ")
                .unitAbbreviation("       ")
                .build();
        List<String> errors = UnitValidator.validate(unitDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The unit name cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        UnitDto unitDto = UnitDto.builder()
                .unitPosId(PointofsaleDto.builder().build().getId())
                .unitName("sdasdsafasfsdfdfdsfsdfdsfdsfdfdsfdsfdsfdsfdsfsdfdsfdsfds")
                .unitAbbreviation("fdgfvnvcxvcvnmhgjfgfdgsdfdfdsgdfhdghnv")
                .build();
        List<String> errors = UnitValidator.validate(unitDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The unit name size must be between 3 and 20 characters"));
    }
}