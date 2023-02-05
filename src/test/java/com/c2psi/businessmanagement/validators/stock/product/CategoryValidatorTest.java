package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryValidatorTest {

    @Test
    public void validate() {
        CategoryDto categoryDto = CategoryDto.builder()
                .catCode("dsds")
                .catParentDto(null)
                .catDescription("sdsds")
                .catName("sdsdsd")
                .catPosDto(PointofsaleDto.builder().build())
                .catShortname("dsdsd")
                .build();

        List<String> errors = CategoryValidator.validate(categoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CategoryDto categoryDto = CategoryDto.builder()
                .catCode("dsds")
                .catParentDto(null)
                .catDescription("sdsds")
                .catName("sdsdsd")
                .catPosDto(PointofsaleDto.builder().build())
                .catShortname("dsdsd")
                .build();

        List<String> errors = CategoryValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        CategoryDto categoryDto = CategoryDto.builder()
                .catCode(null)
                .catParentDto(null)
                .catDescription(null)
                .catName(null)
                .catPosDto(null)
                .catShortname(null)
                .build();

        List<String> errors = CategoryValidator.validate(categoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(10, errors.size());
        assertTrue(errors.contains("The category name cannot be null"));
        assertTrue(errors.contains("The category name cannot be empty"));
        assertTrue(errors.contains("The category name cannot be blank"));
        assertTrue(errors.contains("The category code cannot be null"));
        assertTrue(errors.contains("The category code cannot be empty"));
        assertTrue(errors.contains("The category code cannot be blank"));
        assertTrue(errors.contains("The category shortname cannot be blank"));
        assertTrue(errors.contains("The category shortname cannot be empty"));
        assertTrue(errors.contains("--La description de la categorie ne peut etre vide--"));
        assertTrue(errors.contains("The pointofsale associated to the category cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        CategoryDto categoryDto = CategoryDto.builder()
                .catCode("")
                .catParentDto(null)
                .catDescription("")
                .catName("")
                .catPosDto(PointofsaleDto.builder().build())
                .catShortname("")
                .build();

        List<String> errors = CategoryValidator.validate(categoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(9, errors.size());
        assertTrue(errors.contains("The category name cannot be empty"));
        assertTrue(errors.contains("The category name cannot be blank"));
        assertTrue(errors.contains("The category code cannot be empty"));
        assertTrue(errors.contains("The category code cannot be blank"));
        assertTrue(errors.contains("The category shortname cannot be blank"));
        assertTrue(errors.contains("The category shortname cannot be empty"));
        assertTrue(errors.contains("The category shortname size must be between 3 and 10 characters"));
        assertTrue(errors.contains("The category name size must be between 3 and 20 characters"));
        assertTrue(errors.contains("The category code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        CategoryDto categoryDto = CategoryDto.builder()
                .catCode("   ")
                .catParentDto(null)
                .catDescription("   ")
                .catName("   ")
                .catPosDto(PointofsaleDto.builder().build())
                .catShortname("   ")
                .build();

        List<String> errors = CategoryValidator.validate(categoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The category name cannot be blank"));
        assertTrue(errors.contains("The category code cannot be blank"));
        assertTrue(errors.contains("The category shortname cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        CategoryDto categoryDto = CategoryDto.builder()
                .catCode("aa")
                .catParentDto(null)
                .catDescription("   ")
                .catName("vv")
                .catPosDto(PointofsaleDto.builder().build())
                .catShortname("cc")
                .build();

        List<String> errors = CategoryValidator.validate(categoryDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The category name size must be between 3 and 20 characters"));
        assertTrue(errors.contains("The category shortname size must be between 3 and 10 characters"));
        assertTrue(errors.contains("The category code size must be between 3 and 20 characters"));
    }

}