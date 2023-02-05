package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupplyInvoiceDamageValidatorTest {

    @Test
    public void validate() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(PointofsaleDto.builder().build())
                .sidamProviderDto(ProviderDto.builder().build())
                .sidamUserbmDto(UserBMDto.builder().build())
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamComment("")
                .sidamCode("dsdsdsd")
                .sidamPicture("   ")
                .sidamTotalDamChange(Integer.valueOf(10))
                .sidamTotalDamToChange(Integer.valueOf(10))
                .sidamTotalcolis(Integer.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The point of sale associated cannot be null"));
    }

    @Test
    public void validateNull() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(PointofsaleDto.builder().build())
                .sidamProviderDto(ProviderDto.builder().build())
                .sidamUserbmDto(UserBMDto.builder().build())
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamComment("")
                .sidamCode("dsdsdsd")
                .sidamPicture("   ")
                .sidamTotalDamChange(Integer.valueOf(10))
                .sidamTotalDamToChange(Integer.valueOf(10))
                .sidamTotalcolis(Integer.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne saurait etre null--"));
    }

    @Test
    public void validateNullValue() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(null)
                .sidamProviderDto(null)
                .sidamUserbmDto(null)
                .sidamInvoicingDate(null)
                .sidamDeliveryDate(null)
                .sidamComment(null)
                .sidamCode(null)
                .sidamPicture(null)
                .sidamTotalDamChange(null)
                .sidamTotalDamToChange(null)
                .sidamTotalcolis(null)
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(11, errors.size());
        assertTrue(errors.contains("The pointofsale associated cannot be null"));
        assertTrue(errors.contains("The provider associated cannot be null"));
        assertTrue(errors.contains("The userbm associated cannot be null"));
        assertTrue(errors.contains("The invoicing date cannot be null"));
        assertTrue(errors.contains("The delivery date cannot be null"));
        assertTrue(errors.contains("The code cannot be null"));
        assertTrue(errors.contains("The code cannot be empty"));
        assertTrue(errors.contains("The code cannot be blank"));
        assertTrue(errors.contains("The total number of article changed cannot be null"));
        assertTrue(errors.contains("The total number of article to change cannot be null"));
        assertTrue(errors.contains("The total number of colis cannot be null"));
    }

    @Test
    public void validateEmptyValue() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(PointofsaleDto.builder().build())
                .sidamProviderDto(ProviderDto.builder().build())
                .sidamUserbmDto(UserBMDto.builder().build())
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamComment("")
                .sidamCode("")
                .sidamPicture("")
                .sidamTotalDamChange(Integer.valueOf(10))
                .sidamTotalDamToChange(Integer.valueOf(10))
                .sidamTotalcolis(Integer.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The code cannot be empty"));
        assertTrue(errors.contains("The code cannot be blank"));
        assertTrue(errors.contains("The code size must be between 3 and 20 characters"));
    }

    @Test
    public void validateBlankValue() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(PointofsaleDto.builder().build())
                .sidamProviderDto(ProviderDto.builder().build())
                .sidamUserbmDto(UserBMDto.builder().build())
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamComment("    ")
                .sidamCode("     ")
                .sidamPicture("      ")
                .sidamTotalDamChange(Integer.valueOf(10))
                .sidamTotalDamToChange(Integer.valueOf(10))
                .sidamTotalcolis(Integer.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The code cannot be blank"));
    }

    @Test
    public void validateSizeValue() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(PointofsaleDto.builder().build())
                .sidamProviderDto(ProviderDto.builder().build())
                .sidamUserbmDto(UserBMDto.builder().build())
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamComment("    ")
                .sidamCode("adsdsdasdsadsadsadsadsasdsadsadsadsadsadsa")
                .sidamPicture("      ")
                .sidamTotalDamChange(Integer.valueOf(10))
                .sidamTotalDamToChange(Integer.valueOf(10))
                .sidamTotalcolis(Integer.valueOf(10))
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("The code size must be between 3 and 20 characters"));
    }

    @Test
    public void validatePositiveValue() {
        SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                .sidamPosDto(PointofsaleDto.builder().build())
                .sidamProviderDto(ProviderDto.builder().build())
                .sidamUserbmDto(UserBMDto.builder().build())
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamComment("    ")
                .sidamCode("adsd")
                .sidamPicture("      ")
                .sidamTotalDamChange(Integer.valueOf(-10))
                .sidamTotalDamToChange(Integer.valueOf(-10))
                .sidamTotalcolis(Integer.valueOf(-10))
                .build();

        List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("The total number of colis must be positive"));
        assertTrue(errors.contains("The total number of article must be positive"));
        assertTrue(errors.contains("The total number of article changed must be positive"));
    }

    @Test
    public void validateDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                    .sidamPosDto(PointofsaleDto.builder().build())
                    .sidamProviderDto(ProviderDto.builder().build())
                    .sidamUserbmDto(UserBMDto.builder().build())
                    .sidamInvoicingDate(sdf.parse("2024-02-04").toInstant())
                    .sidamDeliveryDate(sdf.parse("2024-02-05").toInstant())
                    .sidamComment("    ")
                    .sidamCode("adsd")
                    .sidamPicture("      ")
                    .sidamTotalDamChange(Integer.valueOf(10))
                    .sidamTotalDamToChange(Integer.valueOf(10))
                    .sidamTotalcolis(Integer.valueOf(10))
                    .build();

            List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The delivery date cannot ne be in the future"));
            assertTrue(errors.contains("The invoicing date cannot be in the future"));
        }
        catch (Exception e){

        }
    }

    @Test
    public void validateInvoicingDateValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SupplyInvoiceDamageDto supplyInvoiceDamageDto = SupplyInvoiceDamageDto.builder()
                    .sidamPosDto(PointofsaleDto.builder().build())
                    .sidamProviderDto(ProviderDto.builder().build())
                    .sidamUserbmDto(UserBMDto.builder().build())
                    .sidamInvoicingDate(sdf.parse("2023-02-03").toInstant())
                    .sidamDeliveryDate(sdf.parse("2023-02-02").toInstant())
                    .sidamComment("    ")
                    .sidamCode("adsd")
                    .sidamPicture("      ")
                    .sidamTotalDamChange(Integer.valueOf(10))
                    .sidamTotalDamToChange(Integer.valueOf(10))
                    .sidamTotalcolis(Integer.valueOf(10))
                    .build();

            List<String> errors = SupplyInvoiceDamageValidator.validate(supplyInvoiceDamageDto);
            System.out.println("errors == " + errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--La date de facturation ne saurait etre ulterieure a la date de livraison--"));
        }
        catch (Exception e){

        }
    }




}