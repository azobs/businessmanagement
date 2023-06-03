package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BackInValidatorTest {

    @Test
    public void validate() {
        BackInDto backInDto = BackInDto.builder()
                .biDate(new Date().toInstant())
                .biCommandDto(CommandDto.builder().build())
                .biUserbmDto(UserBMDto.builder().build())
                .biComment(null)
                .build();

        List<String> errors = BackInValidator.validate(backInDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        BackInDto backInDto = BackInDto.builder()
                .biDate(new Date().toInstant())
                .biCommandDto(CommandDto.builder().build())
                .biUserbmDto(UserBMDto.builder().build())
                .biComment(null)
                .build();

        List<String> errors = BackInValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le  parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        BackInDto backInDto = BackInDto.builder()
                .biDate(null)
                .biCommandDto(null)
                .biUserbmDto(null)
                .biComment(null)
                .build();

        List<String> errors = BackInValidator.validate(backInDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The backin code cannot be null"));
        assertTrue(errors.contains("The backin date cannot be null"));
        assertTrue(errors.contains("The backin command cannot be null"));
        assertTrue(errors.contains("The backin userbm cannot be null"));
        assertTrue(errors.contains("The backin code cannot be empty"));
        assertTrue(errors.contains("The backin code cannot be blank value"));
    }

    @Test
    public void validateEmptyValue() {
        BackInDto backInDto = BackInDto.builder()
                .biDate(new Date().toInstant())
                .biCommandDto(CommandDto.builder().build())
                .biUserbmDto(UserBMDto.builder().build())
                .biComment(null)
                .build();

        List<String> errors = BackInValidator.validate(backInDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The backin code cannot be empty"));
        assertTrue(errors.contains("The backin code cannot be blank value"));
        assertTrue(errors.contains("The backin code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        BackInDto backInDto = BackInDto.builder()
                .biDate(new Date().toInstant())
                .biCommandDto(CommandDto.builder().build())
                .biUserbmDto(UserBMDto.builder().build())
                .biComment(null)
                .build();

        List<String> errors = BackInValidator.validate(backInDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The backin code cannot be blank value"));
    }

    @Test
    public void validateSizeValueBetween3and20() {
        BackInDto backInDto = BackInDto.builder()
                .biDate(new Date().toInstant())
                .biCommandDto(CommandDto.builder().build())
                .biUserbmDto(UserBMDto.builder().build())
                .biComment(null)
                .build();

        List<String> errors = BackInValidator.validate(backInDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The backin code size must be between 3 and 20 characters"));
    }

}