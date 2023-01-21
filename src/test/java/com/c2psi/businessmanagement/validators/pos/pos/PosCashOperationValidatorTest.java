package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosCashOperationValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCashOperationDto poscashaccToValidate = PosCashOperationDto.builder()
                    .poscoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscoAmountinmvt(BigDecimal.valueOf(1000))
                    .poscoPosCashAccountDto(PosCashAccountDto.builder().build())
                    .poscoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCashOperationValidator.validate(poscashaccToValidate);
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
            PosCashOperationDto poscashaccToValidate = PosCashOperationDto.builder()
                    .poscoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscoAmountinmvt(BigDecimal.valueOf(1000))
                    .poscoPosCashAccountDto(PosCashAccountDto.builder().build())
                    .poscoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCashOperationValidator.validate(null);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--Le parametre a valider ne doit pas etre null--"));
            /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNegativeAmountInMvt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCashOperationDto poscashaccToValidate = PosCashOperationDto.builder()
                    .poscoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscoAmountinmvt(BigDecimal.valueOf(-1000))
                    .poscoPosCashAccountDto(PosCashAccountDto.builder().build())
                    .poscoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCashOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le montant en mouvement ne doit pas etre negatif--"));
            assertTrue(errors.contains("The amount in mvt cannot must be positive"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullAmountInMvt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCashOperationDto poscashaccToValidate = PosCashOperationDto.builder()
                    .poscoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscoAmountinmvt(null)
                    .poscoPosCashAccountDto(PosCashAccountDto.builder().build())
                    .poscoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCashOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le montant associe a l'operation ne peut etre null--"));
            assertTrue(errors.contains("The amount in mvt cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullCashAccount() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCashOperationDto poscashaccToValidate = PosCashOperationDto.builder()
                    .poscoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscoAmountinmvt(BigDecimal.valueOf(100))
                    .poscoPosCashAccountDto(null)
                    .poscoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCashOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le compte cash associe ne peut etre null--"));
            assertTrue(errors.contains("The cash account associate with the operation cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullUserBM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCashOperationDto poscashaccToValidate = PosCashOperationDto.builder()
                    .poscoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscoAmountinmvt(BigDecimal.valueOf(100))
                    .poscoPosCashAccountDto(PosCashAccountDto.builder().build())
                    .poscoUserbmDto(null)
                    .build();
            List<String> errors = PosCashOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
            assertTrue(errors.contains("The user associate with the operation cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}