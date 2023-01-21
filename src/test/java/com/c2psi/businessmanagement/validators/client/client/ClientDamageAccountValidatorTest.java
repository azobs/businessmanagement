package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientDamageAccountValidatorTest {

    @Test
    public void validate() {
        ClientDamageAccountDto cltDamAccDtoToValidate = ClientDamageAccountDto.builder()
                .cdaArticleDto(ArticleDto.builder().build())
                .cdaClientDto(ClientDto.builder().build())
                .cdaNumber(15)
                .build();

        List<String> errors = ClientDamageAccountValidator.validate(cltDamAccDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        /*assertTrue(errors.contains("--Le montant en mouvement ne saurait être négatif--"));
        assertTrue(errors.contains("The amount in mvt must be positive"));*/
    }

    @Test
    public void validateNull() {
        ClientDamageAccountDto cltDamAccDtoToValidate = ClientDamageAccountDto.builder()
                .cdaArticleDto(ArticleDto.builder().build())
                .cdaClientDto(ClientDto.builder().build())
                .cdaNumber(15)
                .build();

        List<String> errors = ClientDamageAccountValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le compte damage (avarie) ne peut être null--"));
        /*assertTrue(errors.contains("The amount in mvt must be positive"));*/
    }

    @Test
    public void validateNullValue() {
        ClientDamageAccountDto cltDamAccDtoToValidate = ClientDamageAccountDto.builder()
                .cdaArticleDto(null)
                .cdaClientDto(null)
                .cdaNumber(null)
                .build();

        List<String> errors = ClientDamageAccountValidator.validate(cltDamAccDtoToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("--L'article associe au compte d'avarie ne peut etre null--"));
        assertTrue(errors.contains("--Le client associe au compte d'avarie ne peut etre null--"));
        assertTrue(errors.contains("--Le nombre d'avarie dans le compte ne peut etre null--"));
        assertTrue(errors.contains("The article concerned by the account cannot be null"));
        assertTrue(errors.contains("The client associated with the account cannot be null"));
        assertTrue(errors.contains("The number in the account cannot be null"));
    }





}