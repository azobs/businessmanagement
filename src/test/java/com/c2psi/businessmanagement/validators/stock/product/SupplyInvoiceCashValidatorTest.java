package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupplyInvoiceCashValidatorTest {

    @Test
    public void validate() {
        SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                .sicashPosDto(PointofsaleDto.builder().build())
                .sicashInvoicingDate(new Date().toInstant())
                .sicashProviderDto(ProviderDto.builder().build())
                .sicashComment("")
                .sicashUserbmDto(UserBMDto.builder().build())
                .sicashDeliveryDate(new Date().toInstant())
                .sicashTotalcolis(Integer.valueOf(10))
                .sicashPicture("")
                .sicashCode("dsdsds")
                .sicashAmountpaid(BigDecimal.valueOf(1000))
                .sicashAmountexpected(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The product alias size must at most 10 characters"));
    }

    @Test
    public void validateNull() {
        SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                .sicashPosDto(PointofsaleDto.builder().build())
                .sicashInvoicingDate(new Date().toInstant())
                .sicashProviderDto(ProviderDto.builder().build())
                .sicashComment("")
                .sicashUserbmDto(UserBMDto.builder().build())
                .sicashDeliveryDate(new Date().toInstant())
                .sicashTotalcolis(Integer.valueOf(10))
                .sicashPicture("")
                .sicashCode("dsdsds")
                .sicashAmountpaid(BigDecimal.valueOf(1000))
                .sicashAmountexpected(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = SupplyInvoiceCashValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                .sicashPosDto(null)
                .sicashInvoicingDate(null)
                .sicashProviderDto(null)
                .sicashComment(null)
                .sicashUserbmDto(null)
                .sicashDeliveryDate(null)
                .sicashTotalcolis(null)
                .sicashPicture(null)
                .sicashCode(null)
                .sicashAmountpaid(null)
                .sicashAmountexpected(null)
                .build();

        List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(11, errors.size());
        assertTrue(errors.contains("The point of sale associated cannot be null"));
        assertTrue(errors.contains("The invoicing date cannot be null"));
        assertTrue(errors.contains("The provider associated cannot be null"));
        assertTrue(errors.contains("The userbm associated cannot be null"));
        assertTrue(errors.contains("The delivery date cannot be null"));
        assertTrue(errors.contains("The total number of colis cannot be null"));
        assertTrue(errors.contains("The supply invoice code cannot be null"));
        assertTrue(errors.contains("The supply invoice code cannot be empty"));
        assertTrue(errors.contains("The supply invoice code cannot be blank"));
        assertTrue(errors.contains("The amount paid cannot be null"));
        assertTrue(errors.contains("The amount expected cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                .sicashPosDto(PointofsaleDto.builder().build())
                .sicashInvoicingDate(new Date().toInstant())
                .sicashProviderDto(ProviderDto.builder().build())
                .sicashComment("")
                .sicashUserbmDto(UserBMDto.builder().build())
                .sicashDeliveryDate(new Date().toInstant())
                .sicashTotalcolis(Integer.valueOf(10))
                .sicashPicture("")
                .sicashCode("")
                .sicashAmountpaid(BigDecimal.valueOf(1000))
                .sicashAmountexpected(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The supply invoice code cannot be empty"));
        assertTrue(errors.contains("The supply invoice code cannot be blank"));
        assertTrue(errors.contains("The supply invoice code size must be between 3 and 20 characters"));
    }


    @Test
    public void validateBlankValue() {
        SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                .sicashPosDto(PointofsaleDto.builder().build())
                .sicashInvoicingDate(new Date().toInstant())
                .sicashProviderDto(ProviderDto.builder().build())
                .sicashComment("")
                .sicashUserbmDto(UserBMDto.builder().build())
                .sicashDeliveryDate(new Date().toInstant())
                .sicashTotalcolis(Integer.valueOf(10))
                .sicashPicture("")
                .sicashCode("      ")
                .sicashAmountpaid(BigDecimal.valueOf(1000))
                .sicashAmountexpected(BigDecimal.valueOf(1000))
                .build();

        List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The supply invoice code cannot be blank"));
    }

    @Test
    public void validatePositiveValue() {
        SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                .sicashPosDto(PointofsaleDto.builder().build())
                .sicashInvoicingDate(new Date().toInstant())
                .sicashProviderDto(ProviderDto.builder().build())
                .sicashComment("")
                .sicashUserbmDto(UserBMDto.builder().build())
                .sicashDeliveryDate(new Date().toInstant())
                .sicashTotalcolis(Integer.valueOf(0))
                .sicashPicture("")
                .sicashCode("ascd")
                .sicashAmountpaid(BigDecimal.valueOf(-1000))
                .sicashAmountexpected(BigDecimal.valueOf(0))
                .build();

        List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The total number of colis must be positive"));
        assertTrue(errors.contains("The amount paid must be positive or zero"));
        assertTrue(errors.contains("The amount expected must be positive"));
    }

    @Test
    public void validateDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                    .sicashPosDto(PointofsaleDto.builder().build())
                    .sicashInvoicingDate(sdf.parse("2024-02-03").toInstant())
                    .sicashProviderDto(ProviderDto.builder().build())
                    .sicashComment("")
                    .sicashUserbmDto(UserBMDto.builder().build())
                    .sicashDeliveryDate(sdf.parse("2024-02-03").toInstant())
                    .sicashTotalcolis(Integer.valueOf(10))
                    .sicashPicture("")
                    .sicashCode("ascd")
                    .sicashAmountpaid(BigDecimal.valueOf(1000))
                    .sicashAmountexpected(BigDecimal.valueOf(10))
                    .build();

            List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The delivery date cannot be in the future"));
            assertTrue(errors.contains("The invoicing date cannot be in the future"));
        }
        catch (Exception e){

        }
    }

    @Test
    public void validateDateInvoicingValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SupplyInvoiceCashDto supplyInvoiceCashDto = SupplyInvoiceCashDto.builder()
                    .sicashPosDto(PointofsaleDto.builder().build())
                    .sicashInvoicingDate(sdf.parse("2023-02-03").toInstant())
                    .sicashProviderDto(ProviderDto.builder().build())
                    .sicashComment("")
                    .sicashUserbmDto(UserBMDto.builder().build())
                    .sicashDeliveryDate(sdf.parse("2023-02-01").toInstant())
                    .sicashTotalcolis(Integer.valueOf(10))
                    .sicashPicture("")
                    .sicashCode("ascd")
                    .sicashAmountpaid(BigDecimal.valueOf(1000))
                    .sicashAmountexpected(BigDecimal.valueOf(10))
                    .build();

            List<String> errors = SupplyInvoiceCashValidator.validate(supplyInvoiceCashDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--La date de facturation ne saurait etre ulterieure a la date de livraison--"));
        }
        catch (Exception e){

        }
    }



}