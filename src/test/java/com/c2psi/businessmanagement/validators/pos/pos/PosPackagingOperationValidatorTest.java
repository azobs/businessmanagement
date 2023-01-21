package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosPackagingOperationValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosPackagingOperationDto pospackaccToValidate = PosPackagingOperationDto.builder()
                    .pospoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .pospoNumberinmvt(10)
                    .pospoPosPackagingAccountDto(PosPackagingAccountDto.builder().build())
                    .pospoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosPackagingOperationValidator.validate(pospackaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(0, errors.size());
            /*assertTrue(errors.contains("The pos capsule account cannot be null"));
            assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNull() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosPackagingOperationDto pospackaccToValidate = PosPackagingOperationDto.builder()
                    .pospoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .pospoNumberinmvt(10)
                    .pospoPosPackagingAccountDto(PosPackagingAccountDto.builder().build())
                    .pospoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosPackagingOperationValidator.validate(null);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
            /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNumberNull() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosPackagingOperationDto pospackaccToValidate = PosPackagingOperationDto.builder()
                    .pospoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .pospoNumberinmvt(null)
                    .pospoPosPackagingAccountDto(PosPackagingAccountDto.builder().build())
                    .pospoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosPackagingOperationValidator.validate(pospackaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The number in mvt cannot be null"));
            assertTrue(errors.contains("--Le nombre d'emballage dans l'operation ne peut etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNumberNegative() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosPackagingOperationDto pospackaccToValidate = PosPackagingOperationDto.builder()
                    .pospoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .pospoNumberinmvt(-10)
                    .pospoPosPackagingAccountDto(PosPackagingAccountDto.builder().build())
                    .pospoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosPackagingOperationValidator.validate(pospackaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The number in mvt must be positive"));
            assertTrue(errors.contains("--Le nombre d'emballage dans l'operation ne peut etre negatif--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullPackagingAccount() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosPackagingOperationDto pospackaccToValidate = PosPackagingOperationDto.builder()
                    .pospoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .pospoNumberinmvt(10)
                    .pospoPosPackagingAccountDto(null)
                    .pospoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosPackagingOperationValidator.validate(pospackaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The packaging account associated cannot be null"));
            assertTrue(errors.contains("--Le compte affecte par l'operation ne peut etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullUserBM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosPackagingOperationDto pospackaccToValidate = PosPackagingOperationDto.builder()
                    .pospoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .pospoNumberinmvt(10)
                    .pospoPosPackagingAccountDto(PosPackagingAccountDto.builder().build())
                    .pospoUserbmDto(null)
                    .build();
            List<String> errors = PosPackagingOperationValidator.validate(pospackaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The userbm associate with the operation cannot be null"));
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}