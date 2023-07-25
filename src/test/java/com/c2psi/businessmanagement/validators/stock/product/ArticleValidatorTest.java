package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import org.junit.Test;

import java.math.BigDecimal;
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("gggg")
                .artDescription(null)
//                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(0))
//                .artName("dsdsd")
//                .artQuantityinstock(BigDecimal.valueOf(0))
//                .artShortname("dsdsds")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(5))
                .artUnitDto(UnitDto.builder().build())
                .artThreshold(BigDecimal.valueOf(0).doubleValue())
                .artName("dsdsd")
                .artQuantityinstock(BigDecimal.valueOf(0).doubleValue())
                .artShortname("dsdsds")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(5).doubleValue())
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("gggg")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(0))
//                .artName("dsdsd")
//                .artQuantityinstock(BigDecimal.valueOf(0))
//                .artShortname("dsdsds")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(5))
                .artThreshold(BigDecimal.valueOf(0).doubleValue())
                .artName("dsdsd")
                .artQuantityinstock(BigDecimal.valueOf(0).doubleValue())
                .artShortname("dsdsds")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(5).doubleValue())
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
                .artPosId(null)
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("")
                .artDescription("")
                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(0))
//                .artName("")
//                .artQuantityinstock(BigDecimal.valueOf(0))
//                .artShortname("")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(5))
                .artThreshold(BigDecimal.valueOf(0).doubleValue())
                .artName("")
                .artQuantityinstock(BigDecimal.valueOf(0).doubleValue())
                .artShortname("")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(5).doubleValue())
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("    ")
                .artDescription("          ")
                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(0))
//                .artName("    ")
//                .artQuantityinstock(BigDecimal.valueOf(0))
//                .artShortname("    ")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(5))
                .artThreshold(BigDecimal.valueOf(0).doubleValue())
                .artName("    ")
                .artQuantityinstock(BigDecimal.valueOf(0).doubleValue())
                .artShortname("    ")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(5).doubleValue())
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("sdssdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsds")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(0))
//                .artName("aq")
//                .artQuantityinstock(BigDecimal.valueOf(0))
//                .artShortname("asasqwqwqweeretrtrytytuyjhjhjnvbcvcvcvchjhjhjh")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(5))
                .artThreshold(BigDecimal.valueOf(0).doubleValue())
                .artName("aq")
                .artQuantityinstock(BigDecimal.valueOf(0).doubleValue())
                .artShortname("asasqwqwqweeretrtrytytuyjhjhjnvbcvcvcvchjhjhjh")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(5).doubleValue())
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("sdssdsds")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(-1))
//                .artName("aqxsx")
//                .artQuantityinstock(BigDecimal.valueOf(-2))
//                .artShortname("asasqwq")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(5))
                .artThreshold(BigDecimal.valueOf(-1).doubleValue())
                .artName("aqxsx")
                .artQuantityinstock(BigDecimal.valueOf(-2).doubleValue())
                .artShortname("asasqwq")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(5).doubleValue())
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
                .artPosId(PointofsaleDto.builder().build().getId())
                .artCode("sdssdsds")
                .artDescription(null)
                .artUnitDto(UnitDto.builder().build())
//                .artThreshold(BigDecimal.valueOf(10))
//                .artName("aqxsx")
//                .artQuantityinstock(BigDecimal.valueOf(10))
//                .artShortname("asasqwq")
//                .artLowLimitWholesale(BigDecimal.valueOf(10))
//                .artLowLimitSemiWholesale(BigDecimal.valueOf(15))
                .artThreshold(BigDecimal.valueOf(10).doubleValue())
                .artName("aqxsx")
                .artQuantityinstock(BigDecimal.valueOf(10).doubleValue())
                .artShortname("asasqwq")
                .artLowLimitWholesale(BigDecimal.valueOf(10).doubleValue())
                .artLowLimitSemiWholesale(BigDecimal.valueOf(15).doubleValue())
                .build();

        List<String> errors = ArticleValidator.validate(articleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--La limite de vente en semi gros ne saurait etre supperieur a la limite de vente en gros--"));
    }


}