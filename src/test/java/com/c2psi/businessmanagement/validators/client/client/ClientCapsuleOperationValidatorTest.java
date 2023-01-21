package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.validators.pos.pos.PosCapsuleOperationValidator;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientCapsuleOperationValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientCapsuleOperationDto cltCapsOpDtoToValidate = ClientCapsuleOperationDto.builder()
                    .cltcsoCltCapsuleAccountDto(ClientCapsuleAccountDto.builder().build())
                    .cltcsoNumberinmvt(12)
                    .cltcsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltcsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = ClientCapsuleOperationValidator.validate(cltCapsOpDtoToValidate);
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
            ClientCapsuleOperationDto cltCapsOpDtoToValidate = ClientCapsuleOperationDto.builder()
                    .cltcsoCltCapsuleAccountDto(ClientCapsuleAccountDto.builder().build())
                    .cltcsoNumberinmvt(12)
                    .cltcsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltcsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = ClientCapsuleOperationValidator.validate(null);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--le parametre à valider ne peut etre null--"));
            /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientCapsuleOperationDto cltCapsOpDtoToValidate = ClientCapsuleOperationDto.builder()
                    .cltcsoCltCapsuleAccountDto(null)
                    .cltcsoNumberinmvt(null)
                    .cltcsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltcsoUserbmDto(null)
                    .build();
            List<String> errors = ClientCapsuleOperationValidator.validate(cltCapsOpDtoToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(6, errors.size());
            assertTrue(errors.contains("--Le nombre de capsule en mouvement ne saurait etre null--"));
            assertTrue(errors.contains("--Le compte qui sera affecte ne saurait etre null--"));
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
            assertTrue(errors.contains("The number in mvt in the operation cannot be null"));
            assertTrue(errors.contains("The client concerned by the operation cannot be null"));
            assertTrue(errors.contains("The user who make the operation cannot be null"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNegativeValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientCapsuleOperationDto cltCapsOpDtoToValidate = ClientCapsuleOperationDto.builder()
                    .cltcsoCltCapsuleAccountDto(ClientCapsuleAccountDto.builder().build())
                    .cltcsoNumberinmvt(-12)
                    .cltcsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltcsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = ClientCapsuleOperationValidator.validate(cltCapsOpDtoToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le nombre de capsule en mouvement ne saurait être négatif--"));
            assertTrue(errors.contains("The number in mvt in the operation must be positive"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateZeroValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientCapsuleOperationDto cltCapsOpDtoToValidate = ClientCapsuleOperationDto.builder()
                    .cltcsoCltCapsuleAccountDto(ClientCapsuleAccountDto.builder().build())
                    .cltcsoNumberinmvt(0)
                    .cltcsoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .cltcsoUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = ClientCapsuleOperationValidator.validate(cltCapsOpDtoToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le nombre de capsule en mouvement ne saurait être négatif--"));
            assertTrue(errors.contains("The number in mvt in the operation must be positive"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }







}