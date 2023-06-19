package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientDamageOperationValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientDamageOperationDto cltDamOpToValidate = ClientDamageOperationDto.builder()
                .cltdoCltDamageAccountDto(ClientDamageAccountDto.builder().build())
                .cltdoNumberinmvt(BigDecimal.valueOf(10))
                .cltdoOperationDto(OperationDto.builder()
                        .opDate(sdf.parse("2022-12-22").toInstant())
                        .opDescription("object description")
                        .opObject("object test")
                        .opType(OperationType.Credit)
                        .build())
                .cltdoUserbmDto(UserBMDto.builder().build())
                .build();

            List<String> errors = ClientDamageOperationValidator.validate(cltDamOpToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(0, errors.size());
            //assertTrue(errors.contains("--Le parametre a valider ne doit pas etre null--"));
            /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNull() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientDamageOperationDto cltDamOpToValidate = ClientDamageOperationDto.builder()
                    .cltdoCltDamageAccountDto(ClientDamageAccountDto.builder().build())
                    .cltdoNumberinmvt(BigDecimal.valueOf(10))
                    .cltdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltdoUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = ClientDamageOperationValidator.validate(null);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--Le parametre à valider ne peut etre null--"));
            /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientDamageOperationDto cltDamOpToValidate = ClientDamageOperationDto.builder()
                    .cltdoCltDamageAccountDto(null)
                    .cltdoNumberinmvt(null)
                    .cltdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltdoUserbmDto(null)
                    .build();

            List<String> errors = ClientDamageOperationValidator.validate(cltDamOpToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(6, errors.size());
            assertTrue(errors.contains("--Le nombre d'article en mvt ne peut etre null--"));
            assertTrue(errors.contains("--Le compte qui sera affecte ne saurait etre null--"));
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
            assertTrue(errors.contains("The userbm associated cannot be null"));
            assertTrue(errors.contains("The client account affected cannot be null"));
            assertTrue(errors.contains("The number in mvt cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNegativeValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientDamageOperationDto cltDamOpToValidate = ClientDamageOperationDto.builder()
                    .cltdoCltDamageAccountDto(ClientDamageAccountDto.builder().build())
                    .cltdoNumberinmvt(BigDecimal.valueOf(-12))
                    .cltdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltdoUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = ClientDamageOperationValidator.validate(cltDamOpToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le nombre d'article en mouvement ne saurait être négatif ou null--"));
            assertTrue(errors.contains("The number in mvt must be positive"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateZeroValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientDamageOperationDto cltDamOpToValidate = ClientDamageOperationDto.builder()
                    .cltdoCltDamageAccountDto(ClientDamageAccountDto.builder().build())
                    .cltdoNumberinmvt(BigDecimal.valueOf(0))
                    .cltdoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltdoUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = ClientDamageOperationValidator.validate(cltDamOpToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le nombre d'article en mouvement ne saurait être négatif ou null--"));
            assertTrue(errors.contains("The number in mvt must be positive"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}