package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import com.c2psi.businessmanagement.validators.stock.price.BasePriceValidator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleValidatorTest {

    @Test
    public void validate() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("gggg")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(0))
                .artName("dsdsd")
                .artQuantityinstock(Integer.valueOf(0))
                .artShortname("dsdsds")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(5))
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("gggg")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(0))
                .artName("dsdsd")
                .artQuantityinstock(Integer.valueOf(0))
                .artShortname("dsdsds")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(5))
                .build();

        List<String> errors = ArticleValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(null)
                .artPfDto(null)
                .artPosDto(null)
                .artCode(null)
                .artDescription(null)
                .artUnitDto(null)
                .artThreshold(null)
                .artName(null)
                .artQuantityinstock(null)
                .artShortname(null)
                .artLowLimitWholesale(null)
                .artLowLimitSemiWholesale(null)
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(16, errors.size());
        assertTrue(errors.contains("The article code cannot be null"));
        assertTrue(errors.contains("The article code cannot be empty"));
        assertTrue(errors.contains("The article code cannot be blank"));
        assertTrue(errors.contains("The article name cannot be null"));
        assertTrue(errors.contains("The article name cannot be empty"));
        assertTrue(errors.contains("The article name cannot be blank"));
        assertTrue(errors.contains("The article shortname cannot be empty"));
        assertTrue(errors.contains("The article shortname cannot be blank"));
        assertTrue(errors.contains("The threshold value cannot be null"));
        assertTrue(errors.contains("The low limit value to sell in whole value cannot be null"));
        assertTrue(errors.contains("The low limit value to sell in semi whole value cannot be null"));
        assertTrue(errors.contains("The current quantity in stock cannot be null"));
        assertTrue(errors.contains("The formated product associated cannot be null"));
        assertTrue(errors.contains("The unit associated cannot be null"));
        assertTrue(errors.contains("The base price associated cannot be null"));
        assertTrue(errors.contains("The point of sale associated cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("")
                .artDescription("")
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(0))
                .artName("")
                .artQuantityinstock(Integer.valueOf(0))
                .artShortname("")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(5))
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(10, errors.size());
        assertTrue(errors.contains("The article code cannot be empty"));
        assertTrue(errors.contains("The article code cannot be blank"));
        assertTrue(errors.contains("The article code size must be between 3 and 10 characters"));
        assertTrue(errors.contains("The article name cannot be empty"));
        assertTrue(errors.contains("The article name cannot be blank"));
        assertTrue(errors.contains("The article name size must be between 3 and 20 characters"));
        assertTrue(errors.contains("The article shortname cannot be empty"));
        assertTrue(errors.contains("The article shortname cannot be blank"));
        assertTrue(errors.contains("The article shortname size must be between 3 and 10 characters"));
        assertTrue(errors.contains("--La description de l'article ne doit pas etre vide--"));
    }

    @Test
    public void validateBlankValue() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("    ")
                .artDescription("          ")
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(0))
                .artName("    ")
                .artQuantityinstock(Integer.valueOf(0))
                .artShortname("    ")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(5))
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The article code cannot be blank"));
        assertTrue(errors.contains("The article name cannot be blank"));
        assertTrue(errors.contains("The article shortname cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("sdssdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsds")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(0))
                .artName("aq")
                .artQuantityinstock(Integer.valueOf(0))
                .artShortname("asasqwqwqweeretrtrytytuyjhjhjnvbcvcvcvchjhjhjh")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(5))
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The article code size must be between 3 and 10 characters"));
        assertTrue(errors.contains("The article name size must be between 3 and 20 characters"));
        assertTrue(errors.contains("The article shortname size must be between 3 and 10 characters"));
    }

    @Test
    public void validatePositiveValue() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("sdssdsds")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(-1))
                .artName("aqxsx")
                .artQuantityinstock(Integer.valueOf(-2))
                .artShortname("asasqwq")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(5))
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The threshold value must be positive or zero"));
        assertTrue(errors.contains("The current quantity in stock must be positive or zero"));
    }

    @Test
    public void validateLimitCheckValue() {
        ArticleDto articleDto = ArticleDto.builder()
                .artBpDto(BasePriceDto.builder().build())
                .artPfDto(ProductFormatedDto.builder().build())
                .artPosDto(PointofsaleDto.builder().build())
                .artCode("sdssdsds")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(Integer.valueOf(10))
                .artName("aqxsx")
                .artQuantityinstock(Integer.valueOf(10))
                .artShortname("asasqwq")
                .artLowLimitWholesale(Integer.valueOf(10))
                .artLowLimitSemiWholesale(Integer.valueOf(15))
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--La limite de vente en semi gros ne saurait etre supperieur a la limite de vente en gros--"));
    }


}