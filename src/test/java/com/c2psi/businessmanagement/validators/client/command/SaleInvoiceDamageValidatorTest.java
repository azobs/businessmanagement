package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SaleInvoiceDamageValidatorTest {

    @Test
    public void validate() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(ClientDto.builder().build())
                .saleidamCode("dsdsdsds")
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamPosDto(PointofsaleDto.builder().build())
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamTotalcolis(BigDecimal.valueOf(10))
                .saleidamNumbertochange(BigDecimal.valueOf(1))
                .saleidamNumberchanged(BigDecimal.valueOf(1))
                .saleidamTotalcolis(BigDecimal.valueOf(1))
                .saleidamUserbmDto(UserBMDto.builder().build())
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(ClientDto.builder().build())
                .saleidamCode("dsdsdsds")
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamPosDto(PointofsaleDto.builder().build())
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamTotalcolis(BigDecimal.valueOf(10))
                .saleidamNumbertochange(BigDecimal.valueOf(1))
                .saleidamNumberchanged(BigDecimal.valueOf(1))
                .saleidamTotalcolis(BigDecimal.valueOf(1))
                .saleidamUserbmDto(UserBMDto.builder().build())
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(null)
                .saleidamCode(null)
                .saleidamInvoicingDate(null)
                .saleidamPosDto(null)
                .saleidamDeliveryDate(null)
                .saleidamTotalcolis(null)
                .saleidamNumbertochange(null)
                .saleidamNumberchanged(null)
                .saleidamTotalcolis(null)
                .saleidamUserbmDto(null)
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(11, errors.size());
        assertTrue(errors.contains("The sale invoice code cannot be null"));
        assertTrue(errors.contains("The sale invoice code cannot be empty"));
        assertTrue(errors.contains("The sale invoice code cannot be blank"));
        assertTrue(errors.contains("The sale invoice number of damage to change cannot be null"));
        assertTrue(errors.contains("The sale invoice number of damage change cannot be null"));
        assertTrue(errors.contains("The sale invoice number of colis cannot be null"));
        assertTrue(errors.contains("The sale invoice delivery date changed cannot be null"));
        assertTrue(errors.contains("The sale invoice delivery date changed cannot be null"));
        assertTrue(errors.contains("The point of sale concerned by the sale invoice cannot be null"));
        assertTrue(errors.contains("The client concerned by the sale invoice cannot be null"));
        assertTrue(errors.contains("The Userbm concerned by the sale invoice cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(ClientDto.builder().build())
                .saleidamCode("")
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamPosDto(PointofsaleDto.builder().build())
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamTotalcolis(BigDecimal.valueOf(10))
                .saleidamNumbertochange(BigDecimal.valueOf(1))
                .saleidamNumberchanged(BigDecimal.valueOf(1))
                .saleidamTotalcolis(BigDecimal.valueOf(1))
                .saleidamUserbmDto(UserBMDto.builder().build())
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The sale invoice code cannot be empty"));
        assertTrue(errors.contains("The sale invoice code cannot be blank"));
        assertTrue(errors.contains("The sale invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(ClientDto.builder().build())
                .saleidamCode(" ")
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamPosDto(PointofsaleDto.builder().build())
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamTotalcolis(BigDecimal.valueOf(10))
                .saleidamNumbertochange(BigDecimal.valueOf(1))
                .saleidamNumberchanged(BigDecimal.valueOf(1))
                .saleidamTotalcolis(BigDecimal.valueOf(1))
                .saleidamUserbmDto(UserBMDto.builder().build())
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The sale invoice code cannot be blank"));
        assertTrue(errors.contains("The sale invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateSizeValue() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(ClientDto.builder().build())
                .saleidamCode(" ccxcxcxcxcxcxcxcxcxcxcxcxcxcxxcx")
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamPosDto(PointofsaleDto.builder().build())
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamTotalcolis(BigDecimal.valueOf(10))
                .saleidamNumbertochange(BigDecimal.valueOf(1))
                .saleidamNumberchanged(BigDecimal.valueOf(1))
                .saleidamTotalcolis(BigDecimal.valueOf(1))
                .saleidamUserbmDto(UserBMDto.builder().build())
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The sale invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validatePositiveValue() {
        SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                .saleidamClientDto(ClientDto.builder().build())
                .saleidamCode(" ccfdf")
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamPosDto(PointofsaleDto.builder().build())
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamTotalcolis(BigDecimal.valueOf(0))
                .saleidamNumbertochange(BigDecimal.valueOf(0))
                .saleidamNumberchanged(BigDecimal.valueOf(0))
                .saleidamUserbmDto(UserBMDto.builder().build())
                .build();

        List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The sale invoice number of damage to change must be positive"));
        assertTrue(errors.contains("The sale invoice number of damage change must be positive"));
        assertTrue(errors.contains("The sale invoice number of colis must be positive"));
    }

    @Test
    public void validateDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SaleInvoiceDamageDto saleInvoiceDamageDto = SaleInvoiceDamageDto.builder()
                    .saleidamClientDto(ClientDto.builder().build())
                    .saleidamCode(" ccfdf")
                    .saleidamInvoicingDate(sdf.parse("2023-1-22").toInstant())
                    .saleidamPosDto(PointofsaleDto.builder().build())
                    .saleidamDeliveryDate(sdf.parse("2023-1-21").toInstant())
                    .saleidamTotalcolis(BigDecimal.valueOf(1))
                    .saleidamNumbertochange(BigDecimal.valueOf(1))
                    .saleidamNumberchanged(BigDecimal.valueOf(1))
                    .saleidamUserbmDto(UserBMDto.builder().build())
                    .build();

            List<String> errors = SaleInvoiceDamageValidator.validate(saleInvoiceDamageDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--La date de livraison ne saurait être antérieure à la date de facturation--"));
        }
        catch (Exception e){

        }
    }


    }