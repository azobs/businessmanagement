package com.c2psi.businessmanagement.validators.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PackingDetailsValidatorTest {

    @Test
    public void validate() {
        PackingDetailsDto packingDetailsDtoToValidate = PackingDetailsDto.builder()
                .pdLoadingDto(LoadingDto.builder().build())
                .pdNumberofpackageused(BigDecimal.valueOf(55))
                .pdNumberofpackagereturn(BigDecimal.valueOf(50))
                .pdPackagingDto(PackagingDto.builder().build())
                .build();
        List<String> errors = PackingDetailsValidator.validate(packingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        /*assertTrue(errors.contains("--La quantité retourne d'un produit ne peut etre negative--"));
        assertTrue(errors.contains("The quantity return must be positive or null"));*/
    }

    @Test
    public void validateNull() {
        PackingDetailsDto packingDetailsDtoToValidate = PackingDetailsDto.builder()
                .pdLoadingDto(LoadingDto.builder().build())
                .pdNumberofpackageused(BigDecimal.valueOf(55))
                .pdNumberofpackagereturn(BigDecimal.valueOf(50))
                .pdPackagingDto(PackagingDto.builder().build())
                .build();
        List<String> errors = PackingDetailsValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
        /*assertTrue(errors.contains("The quantity return must be positive or null"));*/
    }

    @Test
    public void validateNullValue() {
        PackingDetailsDto packingDetailsDtoToValidate = PackingDetailsDto.builder()
                .pdLoadingDto(null)
                .pdNumberofpackageused(null)
                .pdNumberofpackagereturn(null)
                .pdPackagingDto(null)
                .build();
        List<String> errors = PackingDetailsValidator.validate(packingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(5, errors.size());
        assertTrue(errors.contains("The number of package used cannot be null"));
        assertTrue(errors.contains("The packaging associated cannot be null"));
        assertTrue(errors.contains("The loading associated cannot be null"));
        assertTrue(errors.contains("--Le type d'emballage lie a ce details de chargement ne peut etre null--"));
        assertTrue(errors.contains("--Le nombre d'emballage utilise ne peut etre null--"));
    }

    @Test
    public void validateNegativeValue() {
        PackingDetailsDto packingDetailsDtoToValidate = PackingDetailsDto.builder()
                .pdLoadingDto(LoadingDto.builder().build())
                .pdNumberofpackageused(BigDecimal.valueOf(-10))
                .pdNumberofpackagereturn(BigDecimal.valueOf(-12))
                .pdPackagingDto(PackagingDto.builder().build())
                .build();
        List<String> errors = PackingDetailsValidator.validate(packingDetailsDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("--Le nombre d'emballage retourné pour un type d'emballage ne peut etre negatif--"));
        assertTrue(errors.contains("--Le nombre d'emballage utilise pour un type d'emballage ne peut etre negatif--"));
        assertTrue(errors.contains("The number of package used must be positive"));
        assertTrue(errors.contains("The number of package return must be positive or null"));
    }


}