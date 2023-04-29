package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientPackagingAccountValidatorTest {

    @Test
    public void validate() {
        ClientPackagingAccountDto clientPackagingAccountDto = ClientPackagingAccountDto.builder()
                .cpaClientDto(ClientDto.builder().build())
                .cpaNumber(BigDecimal.valueOf(10))
                .cpaPackagingDto(PackagingDto.builder().build())
                .build();

        List<String> errors = ClientPackagingAccountValidator.validate(clientPackagingAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        /*assertTrue(errors.contains("--Le montant en mouvement ne saurait être négatif--"));
        assertTrue(errors.contains("The amount in mvt must be positive"));*/
    }

    @Test
    public void validateNull() {
        ClientPackagingAccountDto clientPackagingAccountDto = ClientPackagingAccountDto.builder()
                .cpaClientDto(ClientDto.builder().build())
                .cpaNumber(BigDecimal.valueOf(10))
                .cpaPackagingDto(PackagingDto.builder().build())
                .build();

        List<String> errors = ClientPackagingAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le compte packaging client ne peut être null--"));
        /*assertTrue(errors.contains("The amount in mvt must be positive"));*/
    }

    @Test
    public void validateNullValue() {
        ClientPackagingAccountDto clientPackagingAccountDto = ClientPackagingAccountDto.builder()
                .cpaClientDto(null)
                .cpaNumber(null)
                .cpaPackagingDto(null)
                .build();

        List<String> errors = ClientPackagingAccountValidator.validate(clientPackagingAccountDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The number of packaging in the account cannot be null"));
        assertTrue(errors.contains("The client associated with the account cannot be null"));
        assertTrue(errors.contains("The packaging type associated with the account cannot be null"));
        assertTrue(errors.contains("--Le packaging associé à ce compte packaging client est null--"));
        assertTrue(errors.contains("--Le client associé à ce compte packaging client est null--"));
        assertTrue(errors.contains("--Le nombre de packaging dans le compte ne peut etre null--"));
        /*assertTrue(errors.contains("The amount in mvt must be positive"));*/
    }
    
}