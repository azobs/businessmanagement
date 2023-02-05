package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InventoryValidatorTest {

    @Test
    public void validate() {
        InventoryDto inventoryDto = InventoryDto.builder()
                .invPosDto(PointofsaleDto.builder().build())
                .invDate(new Date().toInstant())
                .invComment("  ")
                .invCode("dsds")
                .build();

        List<String> errors = InventoryValidator.validate(inventoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        InventoryDto inventoryDto = InventoryDto.builder()
                .invPosDto(PointofsaleDto.builder().build())
                .invDate(new Date().toInstant())
                .invComment("  ")
                .invCode("dsds")
                .build();

        List<String> errors = InventoryValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        InventoryDto inventoryDto = InventoryDto.builder()
                .invPosDto(null)
                .invDate(null)
                .invComment(null)
                .invCode(null)
                .build();

        List<String> errors = InventoryValidator.validate(inventoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(5, errors.size());
        assertTrue(errors.contains("The inventory date cannot be null"));
        assertTrue(errors.contains("The inventory code cannot be null"));
        assertTrue(errors.contains("The inventory code cannot be empty"));
        assertTrue(errors.contains("The inventory code cannot be blank"));
        assertTrue(errors.contains("The point of sale associated with the inventory cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        InventoryDto inventoryDto = InventoryDto.builder()
                .invPosDto(PointofsaleDto.builder().build())
                .invDate(new Date().toInstant())
                .invComment("     ")
                .invCode("")
                .build();

        List<String> errors = InventoryValidator.validate(inventoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The inventory code cannot be empty"));
        assertTrue(errors.contains("The inventory code cannot be blank"));
        assertTrue(errors.contains("The inventory code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        InventoryDto inventoryDto = InventoryDto.builder()
                .invPosDto(PointofsaleDto.builder().build())
                .invDate(new Date().toInstant())
                .invComment("     ")
                .invCode("   ")
                .build();

        List<String> errors = InventoryValidator.validate(inventoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The inventory code cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        InventoryDto inventoryDto = InventoryDto.builder()
                .invPosDto(PointofsaleDto.builder().build())
                .invDate(new Date().toInstant())
                .invComment("sdsdsadsadsadsadsadsadsadsadsadsadsdsadsadsdsdsdsa")
                .invCode("sdsadsadsadsadsdsadsdsadsadsadsdsadsadsadsadsadsdsadsads")
                .build();

        List<String> errors = InventoryValidator.validate(inventoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The inventory code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateFutureDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            InventoryDto inventoryDto = InventoryDto.builder()
                    .invPosDto(PointofsaleDto.builder().build())
                    .invDate(sdf.parse("2023-02-04").toInstant())
                    .invComment("sdsdsadsadsadsadsadsad")
                    .invCode("sdsad")
                    .build();

            List<String> errors = InventoryValidator.validate(inventoryDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("The inventory date cannot be in the future"));
        }
        catch (Exception e){

        }
    }


}