package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderValidatorTest {

    @Test
    public void validate() {
        ProviderDto providerDto = ProviderDto.builder()
                .providerCaDto(ProviderCashAccountDto.builder().build())
                .providerPosDto(PointofsaleDto.builder().build())
                .providerAddressDto(AddressDto.builder().build())
                .providerName("dsdfds")
                .providerDescription("")
                .providerAcronym("fgfg")
                .build();

        List<String> errors = ProviderValidator.validate(providerDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
        //assertTrue(errors.contains("The number in mvt in the damage operation must be positive"));
    }

    @Test
    public void validateNull() {
        ProviderDto providerDto = ProviderDto.builder()
                .providerCaDto(ProviderCashAccountDto.builder().build())
                .providerPosDto(PointofsaleDto.builder().build())
                .providerAddressDto(AddressDto.builder().build())
                .providerName("dsdfds")
                .providerDescription("")
                .providerAcronym("fgfg")
                .build();

        List<String> errors = ProviderValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        ProviderDto providerDto = ProviderDto.builder()
                .providerCaDto(null)
                .providerPosDto(null)
                .providerAddressDto(null)
                .providerName(null)
                .providerDescription(null)
                .providerAcronym(null)
                .build();

        List<String> errors = ProviderValidator.validate(providerDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(9, errors.size());
        assertTrue(errors.contains("The pointofsale assoiciated with the provider cannot be null"));
        assertTrue(errors.contains("The provider cash account cannot be null"));
        assertTrue(errors.contains("The address of provider cannot be null"));
        assertTrue(errors.contains("The provider name cannot be null"));
        assertTrue(errors.contains("The provider name cannot be empty"));
        assertTrue(errors.contains("The provider name cannot be blank"));
        assertTrue(errors.contains("The provider acronym cannot be null"));
        assertTrue(errors.contains("The provider acronym cannot be empty"));
        assertTrue(errors.contains("The provider acronym cannot be blank"));
    }

    @Test
    public void validateEmptyValue() {
        ProviderDto providerDto = ProviderDto.builder()
                .providerCaDto(ProviderCashAccountDto.builder().build())
                .providerPosDto(PointofsaleDto.builder().build())
                .providerAddressDto(AddressDto.builder().build())
                .providerName("")
                .providerDescription("")
                .providerAcronym("")
                .build();

        List<String> errors = ProviderValidator.validate(providerDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The provider name cannot be empty"));
        assertTrue(errors.contains("The provider name cannot be blank"));
        assertTrue(errors.contains("The provider name size must be between 3 and 20 characters"));
        assertTrue(errors.contains("The provider acronym cannot be empty"));
        assertTrue(errors.contains("The provider acronym cannot be blank"));
        assertTrue(errors.contains("The provider acronym size must be between 3 and 10 characters"));
    }

    @Test
    public void validateBlankValue() {
        ProviderDto providerDto = ProviderDto.builder()
                .providerCaDto(ProviderCashAccountDto.builder().build())
                .providerPosDto(PointofsaleDto.builder().build())
                .providerAddressDto(AddressDto.builder().build())
                .providerName("     ")
                .providerDescription("       ")
                .providerAcronym("       ")
                .build();

        List<String> errors = ProviderValidator.validate(providerDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The provider name cannot be blank"));
        assertTrue(errors.contains("The provider acronym cannot be blank"));
    }
}