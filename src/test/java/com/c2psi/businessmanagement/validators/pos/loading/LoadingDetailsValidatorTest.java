package com.c2psi.businessmanagement.validators.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoadingDetailsValidatorTest {

    @Test
    public void validate() {
        LoadingDetailsDto loadingDetailsDtoToValidate = LoadingDetailsDto.builder()
                .ldXArticleDto(ArticleDto.builder().build())
                .ldQuantityreturn(BigDecimal.valueOf(12))
                .ldQuantitytaken(BigDecimal.valueOf(14))
                .build();
        List<String> errors = LoadingDetailsValidator.validate(loadingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The email when is precised should be a valid one"));
    }

    @Test
    public void validateNull() {
        LoadingDetailsDto loadingDetailsDtoToValidate = LoadingDetailsDto.builder()
                .ldXArticleDto(ArticleDto.builder().build())
                .ldQuantityreturn(BigDecimal.valueOf(12))
                .ldQuantitytaken(BigDecimal.valueOf(14))
                .build();
        List<String> errors = LoadingDetailsValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le  parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullArticle() {
        LoadingDetailsDto loadingDetailsDtoToValidate = LoadingDetailsDto.builder()
                .ldXArticleDto(null)
                .ldQuantityreturn(BigDecimal.valueOf(12))
                .ldQuantitytaken(BigDecimal.valueOf(14))
                .build();
        List<String> errors = LoadingDetailsValidator.validate(loadingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The article associate with the details loading cannot be null"));
        assertTrue(errors.contains("--L'article lie a ce détails de chargement ne peut etre null--"));
    }

    @Test
    public void validateNullQuantity() {
        LoadingDetailsDto loadingDetailsDtoToValidate = LoadingDetailsDto.builder()
                .ldXArticleDto(ArticleDto.builder().build())
                .ldQuantityreturn(null)
                .ldQuantitytaken(null)
                .build();
        List<String> errors = LoadingDetailsValidator.validate(loadingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("--La quantite emporte ou retourne ne peut etre null--"));
        assertTrue(errors.contains("The quantity taken cannot be null"));
        assertTrue(errors.contains("The quantity return cannot be null"));
    }

    @Test
    public void validateNegativeorzeroQuantityTaken() {
        LoadingDetailsDto loadingDetailsDtoToValidate = LoadingDetailsDto.builder()
                .ldXArticleDto(ArticleDto.builder().build())
                .ldQuantityreturn(BigDecimal.valueOf(12))
                .ldQuantitytaken(BigDecimal.valueOf(0))
                .build();
        List<String> errors = LoadingDetailsValidator.validate(loadingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--La quantité emporté d'un article dans le chargement doit etre positive--"));
        assertTrue(errors.contains("The quantity taken must be positive"));
    }

    @Test
    public void validateNegativeQuantityReturn() {
        LoadingDetailsDto loadingDetailsDtoToValidate = LoadingDetailsDto.builder()
                .ldXArticleDto(ArticleDto.builder().build())
                .ldQuantityreturn(BigDecimal.valueOf(-12))
                .ldQuantitytaken(BigDecimal.valueOf(14))
                .build();
        List<String> errors = LoadingDetailsValidator.validate(loadingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--La quantité retourne d'un produit ne peut etre negative--"));
        assertTrue(errors.contains("The quantity return must be positive or null"));
    }

}