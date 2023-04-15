package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosDamageAccountValidatorTest {

    @Test
    public void validate() {
        PosDamageAccountDto posDamageAccountDtoToValidate = PosDamageAccountDto.builder()
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(BigDecimal.valueOf(12))
                .pdaPointofsaleDto(PointofsaleDto.builder().build())
                .build();

        List<String> errors = PosDamageAccountValidator.validate(posDamageAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        PosDamageAccountDto posDamageAccountDtoToValidate = PosDamageAccountDto.builder()
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(BigDecimal.valueOf(12))
                .pdaPointofsaleDto(PointofsaleDto.builder().build())
                .build();

        List<String> errors = PosDamageAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullArticle() {
        PosDamageAccountDto posDamageAccountDtoToValidate = PosDamageAccountDto.builder()
                .pdaArticleDto(null)
                .pdaNumber(BigDecimal.valueOf(12))
                .pdaPointofsaleDto(PointofsaleDto.builder().build())
                .build();

        List<String> errors = PosDamageAccountValidator.validate(posDamageAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--L'article associe au compte avarie ne peut etre null--"));
        assertTrue(errors.contains("The article associate with the account cannot be null"));
    }

    @Test
    public void validateNullPointofsale() {
        PosDamageAccountDto posDamageAccountDtoToValidate = PosDamageAccountDto.builder()
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(BigDecimal.valueOf(12))
                .pdaPointofsaleDto(null)
                .build();

        List<String> errors = PosDamageAccountValidator.validate(posDamageAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le point de vente associe au compte ne peut etre null--"));
        assertTrue(errors.contains("The pointofsale owner of the account cannot be null"));
    }

    @Test
    public void validateNumberNegative() {
        PosDamageAccountDto posDamageAccountDtoToValidate = PosDamageAccountDto.builder()
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(BigDecimal.valueOf(-12))
                .pdaPointofsaleDto(PointofsaleDto.builder().build())
                .build();

        List<String> errors = PosDamageAccountValidator.validate(posDamageAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le nombre d'article avarié ne peut être negatif--"));
        assertTrue(errors.contains("The number of damage product must be positive"));
    }

    @Test
    public void validateNullNumber() {
        PosDamageAccountDto posDamageAccountDtoToValidate = PosDamageAccountDto.builder()
                .pdaArticleDto(ArticleDto.builder().build())
                .pdaNumber(null)
                .pdaPointofsaleDto(PointofsaleDto.builder().build())
                .build();

        List<String> errors = PosDamageAccountValidator.validate(posDamageAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The number of damage product cannot be null"));
        assertTrue(errors.contains("--Le nombre d'article avarié ne peut etre null--"));
    }


}