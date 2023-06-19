package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OperationValidatorTest {

    @Test
    public void validateOperation() {
        OperationDto optToValidate = OperationDto.builder()
                .opDate(new Date().toInstant())
                .opDescription("dsdfdsfddsfd")
                .opObject("fdfdfdfd")
                .opType(OperationType.Credit)
                .build();
        List<String> errors = OperationValidator.validate(optToValidate);
        System.out.println("errors == "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateOperationNull() {
        OperationDto optToValidate = OperationDto.builder()
                .opDate(new Date().toInstant())
                .opDescription("dsdfdsfddsfd")
                .opObject("fdfdfdfd")
                .opType(OperationType.Credit)
                .build();
        List<String> errors = OperationValidator.validate(null);
        System.out.println("errors == "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
    }

    @Test
    public void validateOperationNullDate() {
        OperationDto optToValidate = OperationDto.builder()
                .opDate(null)
                .opDescription("dsdfdsfddsfd")
                .opObject("fdfdfdfd")
                .opType(OperationType.Credit)
                .build();
        List<String> errors = OperationValidator.validate(optToValidate);
        System.out.println("errors == "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--La date de l'operation ne saurait etre null--"));
        assertTrue(errors.contains("The operation date cannot be null"));
    }

    @Test
    public void validateOperationDateAfter() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            OperationDto optToValidate = OperationDto.builder()
                    .opDate(sdf.parse("2023-05-15").toInstant())
                    .opDescription("dsdfdsfddsfd")
                    .opObject("fdfdfdfd")
                    .opType(OperationType.Credit)
                    .build();
            List<String> errors = OperationValidator.validate(optToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--La date de l'operation ne saurait etre ultérieure a la date courante--"));
            assertTrue(errors.contains("The operation date cannot be in the future"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateOperationBlankValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            OperationDto optToValidate = OperationDto.builder()
                    .opDate(sdf.parse("2021-05-15").toInstant())
                    .opDescription("dsdfdsfddsfd")
                    .opObject("    ")
                    .opType(OperationType.Credit)
                    .build();
            List<String> errors = OperationValidator.validate(optToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("The object of an operation cannot be blank value"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateOperationEmptyValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            OperationDto optToValidate = OperationDto.builder()
                    .opDate(sdf.parse("2021-05-15").toInstant())
                    .opDescription("dsdfdsfddsfd")
                    .opObject("")
                    .opType(OperationType.Credit)
                    .build();
            List<String> errors = OperationValidator.validate(optToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(3, errors.size());
            assertTrue(errors.contains("The object of an operation cannot be blank value"));
            assertTrue(errors.contains("--L'objet de l'operation ne peut etre vide--"));
            assertTrue(errors.contains("The object of an operation cannot be empty value"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateOperationNullValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            OperationDto optToValidate = OperationDto.builder()
                    .opDate(sdf.parse("2021-05-15").toInstant())
                    .opDescription("dsdfdsfddsfd")
                    .opObject(null)
                    .opType(OperationType.Credit)
                    .build();
            List<String> errors = OperationValidator.validate(optToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(4, errors.size());
            assertTrue(errors.contains("The object of an operation cannot be null"));
            assertTrue(errors.contains("--L'objet de l'operation ne peut etre vide--"));
            assertTrue(errors.contains("The object of an operation cannot be empty value"));
            assertTrue(errors.contains("The object of an operation cannot be blank value"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}