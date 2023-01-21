package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.apache.catalina.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientCashOperationValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientCashOperationDto cltCashOpToValidate = ClientCashOperationDto.builder()
                    .ccoAmountinmvt(BigDecimal.valueOf(121))
                    .ccoCltCashAccountDto(ClientCashAccountDto.builder().build())
                    .ccoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .ccoUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = ClientCashOperationValidator.validate(cltCashOpToValidate);
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
            ClientCashOperationDto cltCashOpToValidate = ClientCashOperationDto.builder()
                    .ccoAmountinmvt(BigDecimal.valueOf(121))
                    .ccoCltCashAccountDto(ClientCashAccountDto.builder().build())
                    .ccoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .ccoUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = ClientCashOperationValidator.validate(null);
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
            ClientCashOperationDto cltCashOpToValidate = ClientCashOperationDto.builder()
                    .ccoAmountinmvt(null)
                    .ccoCltCashAccountDto(null)
                    .ccoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .ccoUserbmDto(null)
                    .build();

            List<String> errors = ClientCashOperationValidator.validate(cltCashOpToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(6, errors.size());
            assertTrue(errors.contains("--Le montant en mouvement ne saurait etre null--"));
            assertTrue(errors.contains("--Le compte qui sera affecte ne saurait etre null--"));
            assertTrue(errors.contains("--L'utilisteur qui a effectue l'operation ne saurait etre null--"));
            assertTrue(errors.contains("The amount in mvt cannot be null"));
            assertTrue(errors.contains("The client associated with the operation cannot be null"));
            assertTrue(errors.contains("The user associated with the operation cannot be null"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNegativeValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ClientCashOperationDto cltCashOpToValidate = ClientCashOperationDto.builder()
                    .ccoAmountinmvt(BigDecimal.valueOf(-12))
                    .ccoCltCashAccountDto(ClientCashAccountDto.builder().build())
                    .ccoOperationDto(OperationDto.builder()
                            .opDate(sdf.parse("2022-12-22").toInstant())
                            .opDescription("object description")
                            .opObject("object test")
                            .opType(OperationType.Credit)
                            .build())
                    .ccoUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = ClientCashOperationValidator.validate(cltCashOpToValidate);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le montant en mouvement ne saurait être négatif--"));
            assertTrue(errors.contains("The amount in mvt must be positive"));


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



}