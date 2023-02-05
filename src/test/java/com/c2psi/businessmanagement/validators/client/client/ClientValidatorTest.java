package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientValidatorTest {

    @Test
    public void validate() {
        ClientDto clientDto = ClientDto.builder()
                .cltCaDto(ClientCashAccountDto.builder().build())
                .cltCni("fdfdfdfd")
                .cltName("dsdfdfdf")
                .cltOthername("dfdsfdfd")
                .cltPosDto(PointofsaleDto.builder().build())
                .cltAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .build();

        List<String> errors = ClientValidator.validate(clientDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        ClientDto clientDto = ClientDto.builder()
                .cltCaDto(ClientCashAccountDto.builder().build())
                .cltCni("fdfdfdfd")
                .cltName("dsdfdfdf")
                .cltOthername("dfdsfdfd")
                .cltPosDto(PointofsaleDto.builder().build())
                .cltAddressDto(AddressDto.builder()
                        .email("testsave@gmail@.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .build();

        List<String> errors = ClientValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre à valider ne peut etre null--"));
        /*assertTrue(errors.contains("The application date cannot be in the future"));*/
    }

    @Test
    public void validateNullValue() {
        ClientDto clientDto = ClientDto.builder()
                .cltCaDto(null)
                .cltCni("fdfdfdfd")
                .cltName(null)
                .cltOthername("dfdsfdfd")
                .cltPosDto(null)
                .cltAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .build();

        List<String> errors = ClientValidator.validate(clientDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(7, errors.size());
        assertTrue(errors.contains("The client name cannot be null"));
        assertTrue(errors.contains("The client name cannot be empty"));
        assertTrue(errors.contains("The client name cannot be blank value"));
        assertTrue(errors.contains("The cash account of the client cannot be null"));
        assertTrue(errors.contains("The client must be associated with a Pointofsale"));
        assertTrue(errors.contains("--Le nom du client ne peut etre null--"));
        assertTrue(errors.contains("--Un compte cash doit etre associe au client des l'enregistrement--"));
    }

    @Test
    public void validateEmptyValue() {
        ClientDto clientDto = ClientDto.builder()
                .cltCaDto(ClientCashAccountDto.builder().build())
                .cltCni("fdfdfdfd")
                .cltName("")
                .cltOthername("dfdsfdfd")
                .cltPosDto(PointofsaleDto.builder().build())
                .cltAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .build();

        List<String> errors = ClientValidator.validate(clientDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The client name cannot be empty"));
        assertTrue(errors.contains("The client name cannot be blank value"));
        assertTrue(errors.contains("--Le nom du client ne doit pas etre vide--"));


    }

    @Test
    public void validateBlankValue() {
        ClientDto clientDto = ClientDto.builder()
                .cltCaDto(ClientCashAccountDto.builder().build())
                .cltCni("fdfdfdfd")
                .cltName("   ")
                .cltOthername("dfdsfdfd")
                .cltPosDto(PointofsaleDto.builder().build())
                .cltAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .build();

        List<String> errors = ClientValidator.validate(clientDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The client name cannot be blank value"));
    }
}