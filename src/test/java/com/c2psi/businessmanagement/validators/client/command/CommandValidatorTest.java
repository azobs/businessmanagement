package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.validators.client.client.ClientValidator;
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
                .cmdPosDto(PointofsaleDto.builder().build())
                .cmdState(CommandState.InEditing)
                .cmdStatus(CommandStatus.Cash)
                .cmdType(CommandType.Standard)
                .cmdUserbmDto(UserBMDto.builder().build())
                .cmdDate(new Date().toInstant())
                .cmdDeliveryDto(null)
                .cmdSaleicashDto(null)
                .cmdSicapsDto(null)
                .build();

        List<String> errors = CommandValidator.validate(commandDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }
}