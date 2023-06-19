package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupplyInvoiceCapsuleValidatorTest {

    @Test
    public void validate() {
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                .sicapsPosId(PointofsaleDto.builder().build().getId())
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsProviderDto(ProviderDto.builder().build())
                .sicapsComment("")
                .sicapsUserbmDto(UserBMDto.builder().build())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsTotalcolis(BigDecimal.valueOf(10))
                .sicapsPicture("")
                .sicapsCode("dsdsds")
                .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The product alias size must at most 10 characters"));
    }

    @Test
    public void validateNull() {
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                .sicapsPosId(PointofsaleDto.builder().build().getId())
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsProviderDto(ProviderDto.builder().build())
                .sicapsComment("")
                .sicapsUserbmDto(UserBMDto.builder().build())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsTotalcolis(BigDecimal.valueOf(10))
                .sicapsPicture("")
                .sicapsCode("dsdsds")
                .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceCapsuleValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                .sicapsPosId(null)
                .sicapsInvoicingDate(null)
                .sicapsProviderDto(null)
                .sicapsComment(null)
                .sicapsUserbmDto(null)
                .sicapsDeliveryDate(null)
                .sicapsTotalcolis(null)
                .sicapsPicture(null)
                .sicapsCode(null)
                .sicapsTotalCapsChange(null)
                .sicapsTotalCapsToChange(null)
                .build();

        List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(11, errors.size());
        assertTrue(errors.contains("The point of sale associated cannot be null"));
        assertTrue(errors.contains("The invoicing date cannot be null"));
        assertTrue(errors.contains("The provider associated cannot be null"));
        assertTrue(errors.contains("The userbm associated cannot be null"));
        assertTrue(errors.contains("The delivery date cannot be null"));
        assertTrue(errors.contains("The total colis delivery cannot be null"));
        assertTrue(errors.contains("The supply invoice code cannot be null"));
        assertTrue(errors.contains("The supply invoice code cannot be empty"));
        assertTrue(errors.contains("The supply invoice code cannot be blank"));
        assertTrue(errors.contains("The total capsule changed cannot be null"));
        assertTrue(errors.contains("The total capsule to change cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                .sicapsPosId(PointofsaleDto.builder().build().getId())
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsProviderDto(ProviderDto.builder().build())
                .sicapsComment("")
                .sicapsUserbmDto(UserBMDto.builder().build())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsTotalcolis(BigDecimal.valueOf(10))
                .sicapsPicture("")
                .sicapsCode("")
                .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The supply invoice code cannot be empty"));
        assertTrue(errors.contains("The supply invoice code cannot be blank"));
        assertTrue(errors.contains("The supply invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                .sicapsPosId(PointofsaleDto.builder().build().getId())
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsProviderDto(ProviderDto.builder().build())
                .sicapsComment("    ")
                .sicapsUserbmDto(UserBMDto.builder().build())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsTotalcolis(BigDecimal.valueOf(10))
                .sicapsPicture("    ")
                .sicapsCode("    ")
                .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The supply invoice code cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                .sicapsPosId(PointofsaleDto.builder().build().getId())
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsProviderDto(ProviderDto.builder().build())
                .sicapsComment("    ")
                .sicapsUserbmDto(UserBMDto.builder().build())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsTotalcolis(BigDecimal.valueOf(10))
                .sicapsPicture("    ")
                .sicapsCode("ab")
                .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The supply invoice code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                    .sicapsPosId(PointofsaleDto.builder().build().getId())
                    .sicapsInvoicingDate(sdf.parse("2024-02-01").toInstant())
                    .sicapsProviderDto(ProviderDto.builder().build())
                    .sicapsComment("    ")
                    .sicapsUserbmDto(UserBMDto.builder().build())
                    .sicapsDeliveryDate(sdf.parse("2024-02-02").toInstant())
                    .sicapsTotalcolis(BigDecimal.valueOf(10))
                    .sicapsPicture("    ")
                    .sicapsCode("abc")
                    .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                    .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                    .build();

            List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
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
            SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDto = SupplyInvoiceCapsuleDto.builder()
                    .sicapsPosId(PointofsaleDto.builder().build().getId())
                    .sicapsInvoicingDate(sdf.parse("2023-02-01").toInstant())
                    .sicapsProviderDto(ProviderDto.builder().build())
                    .sicapsComment("    ")
                    .sicapsUserbmDto(UserBMDto.builder().build())
                    .sicapsDeliveryDate(sdf.parse("2023-01-31").toInstant())
                    .sicapsTotalcolis(BigDecimal.valueOf(10))
                    .sicapsPicture("    ")
                    .sicapsCode("abc")
                    .sicapsTotalCapsChange(BigDecimal.valueOf(10))
                    .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                    .build();

            List<String> errors = SupplyInvoiceCapsuleValidator.validate(supplyInvoiceCapsuleDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--La date de facturation ne saurait etre ulterieure a la date de livraison--"));
        }
        catch (Exception e){

        }
    }

}