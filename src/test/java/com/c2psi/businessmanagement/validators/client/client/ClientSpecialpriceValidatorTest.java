package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientSpecialpriceValidatorTest {

    @Test
    public void validate() {
        ClientSpecialpriceDto clientSpecialpriceDto = ClientSpecialpriceDto.builder()
                .cltSpApplieddate(new Date().toInstant())
                .cltSpClientDto(ClientDto.builder().build())
                .cltSpPDto(SpecialPriceDto.builder().build())
                .build();

        List<String> errors = ClientSpecialpriceValidator.validate(clientSpecialpriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("--Le parametre a valider ne doit pas etre null--"));
        /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
    }

    @Test
    public void validateNull() {
        ClientSpecialpriceDto clientSpecialpriceDto = ClientSpecialpriceDto.builder()
                .cltSpApplieddate(new Date().toInstant())
                .cltSpClientDto(ClientDto.builder().build())
                .cltSpPDto(SpecialPriceDto.builder().build())
                .build();

        List<String> errors = ClientSpecialpriceValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre à valider ne peut etre null--"));
        /*assertTrue(errors.contains("--Le compte capsule associe ne peut etre null--"));*/
    }

    @Test
    public void validateNullValue() {
        ClientSpecialpriceDto clientSpecialpriceDto = ClientSpecialpriceDto.builder()
                .cltSpApplieddate(null)
                .cltSpClientDto(null)
                .cltSpPDto(null)
                .build();

        List<String> errors = ClientSpecialpriceValidator.validate(clientSpecialpriceDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The application date of special price to a client cannot be null"));
        assertTrue(errors.contains("The client associated with the special price cannot be null"));
        assertTrue(errors.contains("The special price associated cannot be null"));

    }

    @Test
    public void validateDateInFuture() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            ClientSpecialpriceDto clientSpecialpriceDto = ClientSpecialpriceDto.builder()
                    .cltSpApplieddate(sdf.parse("2023-12-22 00:00:00").toInstant())
                    .cltSpClientDto(ClientDto.builder().build())
                    .cltSpPDto(SpecialPriceDto.builder().build())
                    .build();

            List<String> errors = ClientSpecialpriceValidator.validate(clientSpecialpriceDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("The application date cannot be in the future"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

}