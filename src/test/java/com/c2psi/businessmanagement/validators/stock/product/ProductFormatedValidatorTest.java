package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductFormatedValidatorTest {

    @Test
    public void validate() {
        ProductFormatedDto productFormatedDto = ProductFormatedDto.builder()
                .pfFormatDto(FormatDto.builder().build())
                .pfProductDto(ProductDto.builder().build())
                .pfPicture("")
                .build();

        List<String> errors = ProductFormatedValidator.validate(productFormatedDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        ProductFormatedDto productFormatedDto = ProductFormatedDto.builder()
                .pfFormatDto(FormatDto.builder().build())
                .pfProductDto(ProductDto.builder().build())
                .pfPicture("")
                .build();

        List<String> errors = ProductFormatedValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProductFormatedDto productFormatedDto = ProductFormatedDto.builder()
                .pfFormatDto(null)
                .pfProductDto(null)
                .pfPicture(null)
                .build();

        List<String> errors = ProductFormatedValidator.validate(productFormatedDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The product associated cannot be null"));
        assertTrue(errors.contains("The format associated cannot be null"));
    }
}