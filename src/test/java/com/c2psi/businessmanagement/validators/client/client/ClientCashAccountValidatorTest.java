package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientCashAccountValidatorTest {

    @Test
    public void validate() {
        ClientCashAccountDto cltcashAcc = ClientCashAccountDto.builder()
                .ccaBalance(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ClientCashAccountValidator.validate(cltcashAcc);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        /*assertTrue(errors.contains("The email when is precised should be a valid one"));*/
    }

    @Test
    public void validateNull() {
        ClientCashAccountDto cltcashAcc = ClientCashAccountDto.builder()
                .ccaBalance(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ClientCashAccountValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le compte cash a valider ne saurait être null--"));
    }

    @Test
    public void validateNullValue() {
        ClientCashAccountDto cltcashAcc = ClientCashAccountDto.builder()
                .ccaBalance(null)
                .build();
        List<String> errors = ClientCashAccountValidator.validate(cltcashAcc);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--La balance d'un compte ne peut etre null--"));
        assertTrue(errors.contains("The account balance cannot be null"));
    }
}