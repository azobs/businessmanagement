package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryParentCategoryDto;
import com.c2psi.businessmanagement.validators.stock.price.CurrencyConversionValidator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryParentCategoryValidatorTest {
    @Test
    public void validate() {
        CategoryParentCategoryDto catPatCatDto = CategoryParentCategoryDto.builder()
                .parentCategoryDto(CategoryDto.builder().build())
                .childCategoryDto(CategoryDto.builder().build())
                .build();

        List<String> errors = CategoryParentCategoryValidator.validate(catPatCatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CategoryParentCategoryDto catPatCatDto = CategoryParentCategoryDto.builder()
                .parentCategoryDto(CategoryDto.builder().build())
                .childCategoryDto(CategoryDto.builder().build())
                .build();

        List<String> errors = CategoryParentCategoryValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        CategoryParentCategoryDto catPatCatDto = CategoryParentCategoryDto.builder()
                .parentCategoryDto(null)
                .childCategoryDto(null)
                .build();

        List<String> errors = CategoryParentCategoryValidator.validate(catPatCatDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The Child category of the relation cannot be null"));
        assertTrue(errors.contains("The Parent category of the relation cannot be null"));
    }
}