package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosCapsuleAccountValidatorTest {

    @Test
    public void validate() {
        PosCapsuleAccountDto pcashToValidate = PosCapsuleAccountDto.builder()
                .pcsaArticleDto(ArticleDto.builder().build())
                .pcsaNumber(BigDecimal.valueOf(12))
                .pcsaPointofsaleDto(PointofsaleDto.builder().build())
                .build();
        List<String> errors = PosCapsuleAccountValidator.validate(pcashToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        PosCapsuleAccountDto pcashToValidate = PosCapsuleAccountDto.builder()
                .pcsaArticleDto(ArticleDto.builder().build())
                .pcsaNumber(BigDecimal.valueOf(12))
                .pcsaPointofsaleDto(PointofsaleDto.builder().build())
                .build();
        List<String> errors = PosCapsuleAccountValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullArticle() {
        PosCapsuleAccountDto pcashToValidate = PosCapsuleAccountDto.builder()
                .pcsaArticleDto(null)
                .pcsaNumber(BigDecimal.valueOf(12))
                .pcsaPointofsaleDto(PointofsaleDto.builder().build())
                .build();
        List<String> errors = PosCapsuleAccountValidator.validate(pcashToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The article that is concerned by the capsule account cannot be null"));
        assertTrue(errors.contains("--L'article qui doit etre associe au compte ne peut etre null--"));
    }

    @Test
    public void validateNullPointofsale() {
        PosCapsuleAccountDto pcashToValidate = PosCapsuleAccountDto.builder()
                .pcsaArticleDto(ArticleDto.builder().build())
                .pcsaNumber(BigDecimal.valueOf(12))
                .pcsaPointofsaleDto(null)
                .build();
        List<String> errors = PosCapsuleAccountValidator.validate(pcashToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The owner pointofsale of the account cannot be null"));
        assertTrue(errors.contains("--Le point de vente qui doit etre associe au compte ne peut etre null--"));
    }

}