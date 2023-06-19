package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SaleInvoiceCashValidatorTest {

    @Test
    public void validate() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(1000))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("cscxcx")
                .saleicashAmountreimbourse(BigDecimal.valueOf(200))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(10))
                .saleicashAmountexpected(BigDecimal.valueOf(800))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(1000))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("cscxcx")
                .saleicashAmountreimbourse(BigDecimal.valueOf(200))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(10))
                .saleicashAmountexpected(BigDecimal.valueOf(800))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(null)
                .saleicashPosId(null)
                .saleicashAmountpaid(null)
                .saleicashInvoicingDate(null)
                .saleicashUserbmDto(null)
                .saleicashCode(null)
                .saleicashAmountreimbourse(null)
                .saleicashDeliveryDate(null)
                .saleicashTotalcolis(null)
                .saleicashAmountexpected(null)
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(11, errors.size());
        assertTrue(errors.contains("The sale invoice cash code cannot be null"));
        assertTrue(errors.contains("The sale invoice cash code cannot be empty"));
        assertTrue(errors.contains("The sale invoice cash code cannot be blank"));
        assertTrue(errors.contains("The expected amount of a sale invoice cash cannot be null"));
        assertTrue(errors.contains("The amount paid of a sale invoice cash cannot be null"));
        assertTrue(errors.contains("The amount reimbourse of a sale invoice cash cannot be null"));
        assertTrue(errors.contains("The total colis of a sale invoice cash cannot be null"));
        assertTrue(errors.contains("The invoicing date of a sale invoice cash cannot be null"));
        assertTrue(errors.contains("The point of sale concerned by the sale invoice cash cannot be null"));
        assertTrue(errors.contains("The client concerned by the sale invoice cash cannot be null"));
        assertTrue(errors.contains("The userbm concerned by the sale invoice cash cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(1000))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("")
                .saleicashAmountreimbourse(BigDecimal.valueOf(200))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(10))
                .saleicashAmountexpected(BigDecimal.valueOf(800))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The sale invoice cash code cannot be empty"));
        assertTrue(errors.contains("The sale invoice cash code cannot be blank"));
        assertTrue(errors.contains("The sale invoice cash code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(1000))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("  ")
                .saleicashAmountreimbourse(BigDecimal.valueOf(200))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(10))
                .saleicashAmountexpected(BigDecimal.valueOf(800))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The sale invoice cash code cannot be blank"));
        assertTrue(errors.contains("The sale invoice cash code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateSizeLowerThan3Value() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(1000))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("ab")
                .saleicashAmountreimbourse(BigDecimal.valueOf(200))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(10))
                .saleicashAmountexpected(BigDecimal.valueOf(800))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The sale invoice cash code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateSizeGreaterThan20Value() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(1000))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("ababababababababababab")
                .saleicashAmountreimbourse(BigDecimal.valueOf(200))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(10))
                .saleicashAmountexpected(BigDecimal.valueOf(800))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The sale invoice cash code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateSizePositiveOrZeroValue() {
        SaleInvoiceCashDto sicDto = SaleInvoiceCashDto.builder()
                .saleicashClientDto(ClientDto.builder().build())
                .saleicashPosId(PointofsaleDto.builder().build().getId())
                .saleicashAmountpaid(BigDecimal.valueOf(-1))
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashUserbmDto(UserBMDto.builder().build())
                .saleicashCode("abababa")
                .saleicashAmountreimbourse(BigDecimal.valueOf(0))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashTotalcolis(BigDecimal.valueOf(-1))
                .saleicashAmountexpected(BigDecimal.valueOf(0))
                .build();

        List<String> errors = SaleInvoiceCashValidator.validate(sicDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The expected amount of a sale invoice cash must be positive"));
        assertTrue(errors.contains("The amount paid of a sale invoice cash must be positive or zero"));
        assertTrue(errors.contains("The amount reimbourse of a sale invoice cash must be positive or zero"));
        assertTrue(errors.contains("The total colis of a sale invoice cash must be positive"));
    }

}