package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosPackagingAccountValidatorTest {

    @Test
    public void validate() {
        PosPackagingAccountDto pospackaccToValidate = PosPackagingAccountDto.builder()
                .ppaNumber(BigDecimal.valueOf(10))
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleId(PointofsaleDto.builder().build().getId())
                .build();
        List<String> errors = PosPackagingAccountValidator.validate(pospackaccToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
            /*assertTrue(errors.contains("The pos capsule account cannot be null"));
            assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
    }

    @Test
    public void validateNull() {
        PosPackagingAccountDto pospackaccToValidate = PosPackagingAccountDto.builder()
                .ppaNumber(BigDecimal.valueOf(10))
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleId(PointofsaleDto.builder().build().getId())
                .build();
        List<String> errors = PosPackagingAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
            /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
    }

    @Test
    public void validateNullPackaging() {
        PosPackagingAccountDto pospackaccToValidate = PosPackagingAccountDto.builder()
                .ppaNumber(BigDecimal.valueOf(10))
                .ppaPackagingDto(null)
                .ppaPointofsaleId(PointofsaleDto.builder().build().getId())
                .build();
        List<String> errors = PosPackagingAccountValidator.validate(pospackaccToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le type d'emballage associe au compte d'emballage ne peut etre null--"));
        assertTrue(errors.contains("The packaging associate with the account cannot be null"));
    }

    @Test
    public void validateNullPointofsale() {
        PosPackagingAccountDto pospackaccToValidate = PosPackagingAccountDto.builder()
                .ppaNumber(BigDecimal.valueOf(10))
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleId(null)
                .build();
        List<String> errors = PosPackagingAccountValidator.validate(pospackaccToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le point de vente associe au compte d'emballage ne peut etre null--"));
        assertTrue(errors.contains("The pointofsale associate with the account cannot be null"));
    }

    @Test
    public void validateNullNumberofpackage() {
        PosPackagingAccountDto pospackaccToValidate = PosPackagingAccountDto.builder()
                .ppaNumber(null)
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleId(PointofsaleDto.builder().build().getId())
                .build();
        List<String> errors = PosPackagingAccountValidator.validate(pospackaccToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le nombre d'emballage dans le compte ne peut etre null--"));
        assertTrue(errors.contains("The number of package cannot be null"));
    }

}