package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SaleInvoiceCapsuleValidatorTest {

    @Test
    public void validate() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("dsdsdsdsd")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(10)
                .saleicapsNumbertochange(10)
                .saleicapsTotalcolis(10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("dsdsdsdsd")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(10)
                .saleicapsNumbertochange(10)
                .saleicapsTotalcolis(10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le paramètre à valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(null)
                .saleicapsPosDto(null)
                .saleicapsCode(null)
                .saleicapsInvoicingDate(null)
                .saleicapsUserbmDto(null)
                .saleicapsNumberchanged(null)
                .saleicapsNumbertochange(null)
                .saleicapsTotalcolis(null)
                .saleicapsDeliveryDate(null)
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(11, errors.size());
        assertTrue(errors.contains("The sale invoice code cannot be null"));
        assertTrue(errors.contains("The sale invoice code cannot be empty"));
        assertTrue(errors.contains("The sale invoice code cannot be blank"));
        assertTrue(errors.contains("The sale invoice number of capsule to change cannot be null"));
        assertTrue(errors.contains("The sale invoice number of capsule changed cannot be null"));
        assertTrue(errors.contains("The sale invoice total colis changed cannot be null"));
        assertTrue(errors.contains("The sale invoice delivery date changed cannot be null"));
        assertTrue(errors.contains("The point of sale concerned by the sale invoice cannot be null"));
        assertTrue(errors.contains("The client concerned by the sale invoice cannot be null"));
        assertTrue(errors.contains("The Userbm concerned by the sale invoice cannot be null"));
        assertTrue(errors.contains("The sale invoice invoicing date changed cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(10)
                .saleicapsNumbertochange(10)
                .saleicapsTotalcolis(10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The sale invoice code cannot be empty"));
        assertTrue(errors.contains("The sale invoice code cannot be blank"));
        assertTrue(errors.contains("The sale invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("                  ")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(10)
                .saleicapsNumbertochange(10)
                .saleicapsTotalcolis(10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The sale invoice code cannot be blank"));
    }

    @Test
    public void validateSizeValueLowerThan3() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("ab")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(10)
                .saleicapsNumbertochange(10)
                .saleicapsTotalcolis(10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The sale invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateSizeValueGreaterThan20() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("abbgtrefdsedfcvxsadert")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(10)
                .saleicapsNumbertochange(10)
                .saleicapsTotalcolis(10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The sale invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validatePositiveValue() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("abbgtrefd")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(-10)
                .saleicapsNumbertochange(-10)
                .saleicapsTotalcolis(-10)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The sale invoice number of capsule to change must be positive"));
        assertTrue(errors.contains("The sale invoice number of capsule changed must be positive or zero"));
        assertTrue(errors.contains("The sale invoice total colis changed must be positive or zero"));
    }

    @Test
    public void validateZeroValue() {
        SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                .saleicapsClientDto(ClientDto.builder().build())
                .saleicapsPosDto(PointofsaleDto.builder().build())
                .saleicapsCode("abbgtrefd")
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsUserbmDto(UserBMDto.builder().build())
                .saleicapsNumberchanged(0)
                .saleicapsNumbertochange(0)
                .saleicapsTotalcolis(0)
                .saleicapsDeliveryDate(new Date().toInstant())
                .build();

        List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The sale invoice number of capsule to change must be positive"));
        assertTrue(errors.contains("The sale invoice number of capsule changed must be positive"));
        assertTrue(errors.contains("The sale invoice total colis changed must be positive"));
    }

    @Test
    public void validateDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SaleInvoiceCapsuleDto sicapsDto = SaleInvoiceCapsuleDto.builder()
                    .saleicapsClientDto(ClientDto.builder().build())
                    .saleicapsPosDto(PointofsaleDto.builder().build())
                    .saleicapsCode("abbgtrefd")
                    .saleicapsInvoicingDate(sdf.parse("2023-01-23").toInstant())
                    .saleicapsUserbmDto(UserBMDto.builder().build())
                    .saleicapsNumberchanged(1)
                    .saleicapsNumbertochange(1)
                    .saleicapsTotalcolis(1)
                    .saleicapsDeliveryDate(sdf.parse("2023-01-22").toInstant())
                    .build();

            List<String> errors = SaleInvoiceCapsuleValidator.validate(sicapsDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--La date de livraison ne saurait être antérieure à la date de facturation--"));
        }catch (ParseException e) {
            e.printStackTrace();
        }

    }

}