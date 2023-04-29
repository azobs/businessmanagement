package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientCapsuleAccountValidatorTest {

    @Test
    public void validate() {
        ClientCapsuleAccountDto clientCapsuleAccountDtoToValidate = ClientCapsuleAccountDto.builder()
                .ccsaArticleDto(ArticleDto.builder().build())
                .ccsaClientDto(ClientDto.builder().build())
                .ccsaNumber(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ClientCapsuleAccountValidator.validate(clientCapsuleAccountDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        /*assertTrue(errors.contains("The quantity return must be positive or null"));*/
    }

    @Test
    public void validateNull() {
        ClientCapsuleAccountDto clientCapsuleAccountDtoToValidate = ClientCapsuleAccountDto.builder()
                .ccsaArticleDto(ArticleDto.builder().build())
                .ccsaClientDto(ClientDto.builder().build())
                .ccsaNumber(BigDecimal.valueOf(0))
                .build();
        List<String> errors = ClientCapsuleAccountValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le compte capsule client ne peut être null--"));
    }

    @Test
    public void validateNullValue() {
        ClientCapsuleAccountDto clientCapsuleAccountDtoToValidate = ClientCapsuleAccountDto.builder()
                .ccsaArticleDto(null)
                .ccsaClientDto(null)
                .ccsaNumber(null)
                .build();
        List<String> errors = ClientCapsuleAccountValidator.validate(clientCapsuleAccountDtoToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("--L'article associé à ce compte capsule client est null--"));
        assertTrue(errors.contains("--Le client associé à ce compte capsule client est null--"));
        assertTrue(errors.contains("--Le nombre de capsule dans le compte ne peuit etre null--"));
        assertTrue(errors.contains("The number of capsule in the account cannot be null"));
        assertTrue(errors.contains("The client associated with the account cannot be null"));
        assertTrue(errors.contains("The article concerned by the account cannot be null"));
    }
}