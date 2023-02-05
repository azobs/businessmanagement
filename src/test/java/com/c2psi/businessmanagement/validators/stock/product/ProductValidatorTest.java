package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductValidatorTest {

    @Test
    public void validate() {
        ProductDto productDto = ProductDto.builder()
                .prodCatDto(CategoryDto.builder().build())
                .prodDescription("  ")
                .prodAlias("ddsds")
                .prodPerishable(Boolean.TRUE)
                .prodName("dsdsds")
                .build();

        List<String> errors = ProductValidator.validate(productDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The packaging price must be positive or zero"));
    }

    @Test
    public void validateNull() {
        ProductDto productDto = ProductDto.builder()
                .prodCatDto(CategoryDto.builder().build())
                .prodDescription("  ")
                .prodAlias("ddsds")
                .prodPerishable(Boolean.TRUE)
                .prodName("dsdsds")
                .build();

        List<String> errors = ProductValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProductDto productDto = ProductDto.builder()
                .prodCatDto(null)
                .prodDescription(null)
                .prodAlias(null)
                .prodPerishable(null)
                .prodName(null)
                .build();

        List<String> errors = ProductValidator.validate(productDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(5, errors.size());
        assertTrue(errors.contains("The product name cannot be null"));
        assertTrue(errors.contains("The product name cannot be empty"));
        assertTrue(errors.contains("The product name cannot be blank"));
        assertTrue(errors.contains("The product must be perishable or not"));
        assertTrue(errors.contains("The category associated to the product cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        ProductDto productDto = ProductDto.builder()
                .prodCatDto(CategoryDto.builder().build())
                .prodDescription("")
                .prodAlias("")
                .prodPerishable(Boolean.TRUE)
                .prodName("")
                .build();

        List<String> errors = ProductValidator.validate(productDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The product name cannot be empty"));
        assertTrue(errors.contains("The product name cannot be blank"));
        assertTrue(errors.contains("The product name size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        ProductDto productDto = ProductDto.builder()
                .prodCatDto(CategoryDto.builder().build())
                .prodDescription("      ")
                .prodAlias("       ")
                .prodPerishable(Boolean.TRUE)
                .prodName("     ")
                .build();

        List<String> errors = ProductValidator.validate(productDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The product name cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        ProductDto productDto = ProductDto.builder()
                .prodCatDto(CategoryDto.builder().build())
                .prodDescription("      ")
                .prodAlias("sdsadsdsadsdsadasdsadsadsdsadsadsdsadsadsadsadsa")
                .prodPerishable(Boolean.TRUE)
                .prodName("xxzxzx")
                .build();

        List<String> errors = ProductValidator.validate(productDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The product alias size must at most 10 characters"));
    }


}