package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PosCapsuleOperationValidatorTest {

    @Test
    public void validateNullArgument() {
        List<String> errors = PosCapsuleOperationValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullCapsuleAccount(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCapsuleOperationDto poscashaccToValidate = PosCapsuleOperationDto.builder()
                    .poscsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscsoNumberinmvt(BigDecimal.valueOf(12))
                    .poscsoPosCapsuleAccountDto(null)
                    .poscsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCapsuleOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The pos capsule account cannot be null"));
            assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullUserBM(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCapsuleOperationDto poscashaccToValidate = PosCapsuleOperationDto.builder()
                    .poscsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscsoNumberinmvt(BigDecimal.valueOf(12))
                    .poscsoPosCapsuleAccountDto(PosCapsuleAccountDto.builder().build())
                    .poscsoUserbmDto(null)
                    .build();
            List<String> errors = PosCapsuleOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The user that execute the operation cannot be null"));
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNumberInMvtNegative(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCapsuleOperationDto poscashaccToValidate = PosCapsuleOperationDto.builder()
                    .poscsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscsoNumberinmvt(BigDecimal.valueOf(-12))
                    .poscsoPosCapsuleAccountDto(PosCapsuleAccountDto.builder().build())
                    .poscsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCapsuleOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The number in mouvement must be positive"));
            assertTrue(errors.contains("--Le nombre de capsule en mouvement ne peut etre negatif--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullNumberInMvt(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCapsuleOperationDto poscashaccToValidate = PosCapsuleOperationDto.builder()
                    .poscsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscsoNumberinmvt(null)
                    .poscsoPosCapsuleAccountDto(PosCapsuleAccountDto.builder().build())
                    .poscsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCapsuleOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The number in mouvement cannot be null"));
            assertTrue(errors.contains("--Le nombre en mvt ne saurait etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PosCapsuleOperationDto poscashaccToValidate = PosCapsuleOperationDto.builder()
                    .poscsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .poscsoNumberinmvt(BigDecimal.valueOf(14))
                    .poscsoPosCapsuleAccountDto(PosCapsuleAccountDto.builder().build())
                    .poscsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = PosCapsuleOperationValidator.validate(poscashaccToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(0, errors.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}