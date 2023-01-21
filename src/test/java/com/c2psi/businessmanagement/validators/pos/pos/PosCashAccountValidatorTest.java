package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosCashAccountValidatorTest {

    @Test
    public void validateNull() {
        List<String> errors = PosCapsuleOperationValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateBalanceNull() {
        PosCashAccountDto posCashAccountDtoToValidate = PosCashAccountDto.builder()
                .pcaBalance(null)
                .build();
        List<String> errors = PosCashAccountValidator.validate(posCashAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--La balance d'un compte ne peut etre null--"));
        assertTrue(errors.contains("The account balance cannot be null"));
    }

    @Test
    public void validate() {
        PosCashAccountDto posCashAccountDtoToValidate = PosCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(12))
                .build();
        List<String> errors = PosCashAccountValidator.validate(posCashAccountDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

}