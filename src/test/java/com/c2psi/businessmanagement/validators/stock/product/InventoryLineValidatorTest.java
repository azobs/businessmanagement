package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InventoryLineValidatorTest {

    @Test
    public void validate() {
        InventoryLineDto inventoryLineDto = InventoryLineDto.builder()
                .invlineArtDto(ArticleDto.builder().build())
                .invlineInvDto(InventoryDto.builder().build())
                .invlineLogicqteinstock(BigDecimal.valueOf(0.5))
                .invlineRealqteinstock(BigDecimal.valueOf(0.5))
                .invlineComment("   ")
                .build();

        List<String> errors = InventoryLineValidator.validate(inventoryLineDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        InventoryLineDto inventoryLineDto = InventoryLineDto.builder()
                .invlineArtDto(ArticleDto.builder().build())
                .invlineInvDto(InventoryDto.builder().build())
                .invlineLogicqteinstock(BigDecimal.valueOf(0.5))
                .invlineRealqteinstock(BigDecimal.valueOf(0.5))
                .invlineComment("   ")
                .build();

        List<String> errors = InventoryLineValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        InventoryLineDto inventoryLineDto = InventoryLineDto.builder()
                .invlineArtDto(null)
                .invlineInvDto(null)
                .invlineLogicqteinstock(null)
                .invlineRealqteinstock(null)
                .invlineComment("   ")
                .build();

        List<String> errors = InventoryLineValidator.validate(inventoryLineDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The inventory real quantity in stock for an article cannot be null"));
        assertTrue(errors.contains("The inventory logic quantity in stock for an article cannot be null"));
        assertTrue(errors.contains("The associated inventory for the inventory line cannot be null"));
        assertTrue(errors.contains("The associated article for the inventory line cannot be null"));
    }

    @Test
    public void validatePositiveOrZeroValue() {
        InventoryLineDto inventoryLineDto = InventoryLineDto.builder()
                .invlineArtDto(ArticleDto.builder().build())
                .invlineInvDto(InventoryDto.builder().build())
                .invlineLogicqteinstock(BigDecimal.valueOf(-0.5))
                .invlineRealqteinstock(BigDecimal.valueOf(-0.5))
                .invlineComment("   ")
                .build();

        List<String> errors = InventoryLineValidator.validate(inventoryLineDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The inventory real quantity in stock for an article must be positive or zero"));
        assertTrue(errors.contains("The inventory logic quantity in stock for an article must be positive or zero"));
    }


}