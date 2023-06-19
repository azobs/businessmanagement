package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandValidatorTest {

    @Test
    public void validate() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(ClientDto.builder().build())
                .cmdCode("fdfdfd")
                .cmdComment("fdfdfd")
                .cmdPosId(PointofsaleDto.builder().build().getId())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(ClientDto.builder().build())
                .cmdCode("fdfdfd")
                .cmdComment("fdfdfd")
                .cmdPosId(PointofsaleDto.builder().build().getId())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le  parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(null)
                .cmdCode(null)
                .cmdComment("fdfdfd")
                .cmdPosId(null)
                .cmdState(null)
                .cmdStatus(null)
                .cmdType(null)
                .cmdUserbmDto(null)
                .cmdDate(null)
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(15, errors.size());
        assertTrue(errors.contains("The command code cannot be empty"));
        assertTrue(errors.contains("The command code cannot be blank value"));
    }

    @Test
    public void validateEmptyValue() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(ClientDto.builder().build())
                .cmdCode("")
                .cmdComment("fdfdfd")
                .cmdPosId(PointofsaleDto.builder().build().getId())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The command code cannot be empty"));
        assertTrue(errors.contains("The command code cannot be blank value"));
        assertTrue(errors.contains("The command code size must be between 3 and 20 characters"));
        assertTrue(errors.contains("--le code de la commande ne peut etre vide--"));

    }

    @Test
    public void validateBlankValue() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(ClientDto.builder().build())
                .cmdCode("      ")
                .cmdComment("fdfdfd")
                .cmdPosId(PointofsaleDto.builder().build().getId())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The command code cannot be blank value"));

    }

    @Test
    public void validateSizeValueatleast3() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(ClientDto.builder().build())
                .cmdCode("ss")
                .cmdComment("fdfdfd")
                .cmdPosId(PointofsaleDto.builder().build().getId())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The command code size must be between 3 and 20 characters"));

    }

    @Test
    public void validateSizeValueatmost20() {
        CommandDto commandDto = CommandDto.builder()
                .cmdClientDto(ClientDto.builder().build())
                .cmdCode("sssdsdeweasedfrtghyuj")
                .cmdComment("fdfdfd")
                .cmdPosId(PointofsaleDto.builder().build().getId())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSaleicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The command code size must be between 3 and 20 characters"));

    }



}