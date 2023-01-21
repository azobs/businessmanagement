package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.PosPackagingAccount;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosPackagingAccountValidatorTest {

    @Test
    public void validate() {
        PosPackagingAccountDto pospackaccToValidate = PosPackagingAccountDto.builder()
                .ppaNumber(10)
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleDto(PointofsaleDto.builder().build())
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
                .ppaNumber(10)
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleDto(PointofsaleDto.builder().build())
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
                .ppaNumber(10)
                .ppaPackagingDto(null)
                .ppaPointofsaleDto(PointofsaleDto.builder().build())
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
                .ppaNumber(10)
                .ppaPackagingDto(PackagingDto.builder().build())
                .ppaPointofsaleDto(null)
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
                .ppaPointofsaleDto(PointofsaleDto.builder().build())
                .build();
        List<String> errors = PosPackagingAccountValidator.validate(pospackaccToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le nombre d'emballage dans le compte ne peut etre null--"));
        assertTrue(errors.contains("The number of package cannot be null"));
    }

}