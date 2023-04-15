package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosDamageOperationValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosDamageOperationDto posdamaccToValidate = PosDamageOperationDto.builder()
                    .posdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .posdoNumberinmvt(BigDecimal.valueOf(10))
                    .posdoPosDamageAccountDto(PosDamageAccountDto.builder().build())
                    .posdoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosDamageOperationValidator.validate(posdamaccToValidate);
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
            PosDamageOperationDto posdamaccToValidate = PosDamageOperationDto.builder()
                    .posdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .posdoNumberinmvt(BigDecimal.valueOf(10))
                    .posdoPosDamageAccountDto(PosDamageAccountDto.builder().build())
                    .posdoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosDamageOperationValidator.validate(null);
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
    public void validateNullNumberInMvt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosDamageOperationDto posdamaccToValidate = PosDamageOperationDto.builder()
                    .posdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .posdoNumberinmvt(null)
                    .posdoPosDamageAccountDto(PosDamageAccountDto.builder().build())
                    .posdoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosDamageOperationValidator.validate(posdamaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le nombre en mvt ne peut etre null--"));
            assertTrue(errors.contains("The number in mvt cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNegativeNumberInMvt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosDamageOperationDto posdamaccToValidate = PosDamageOperationDto.builder()
                    .posdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .posdoNumberinmvt(BigDecimal.valueOf(-12))
                    .posdoPosDamageAccountDto(PosDamageAccountDto.builder().build())
                    .posdoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosDamageOperationValidator.validate(posdamaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le nombre en mouvement ne peut etre negatif--"));
            assertTrue(errors.contains("The number in mvt must be positive"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullDamageAccount() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosDamageOperationDto posdamaccToValidate = PosDamageOperationDto.builder()
                    .posdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .posdoNumberinmvt(BigDecimal.valueOf(12))
                    .posdoPosDamageAccountDto(null)
                    .posdoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosDamageOperationValidator.validate(posdamaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le compte affecte par l'operation ne peut etre null--"));
            assertTrue(errors.contains("The damage account cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullUserBM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosDamageOperationDto posdamaccToValidate = PosDamageOperationDto.builder()
                    .posdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .posdoNumberinmvt(BigDecimal.valueOf(12))
                    .posdoPosDamageAccountDto(PosDamageAccountDto.builder().build())
                    .posdoUserbmDto(null)
                    .build();
            List<String> errors = PosDamageOperationValidator.validate(posdamaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
            assertTrue(errors.contains("The userbm associate with the operation cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}