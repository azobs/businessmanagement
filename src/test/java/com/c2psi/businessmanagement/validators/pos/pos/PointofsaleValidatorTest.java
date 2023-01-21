package com.c2psi.businessmanagement.validators.pos.pos;


import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PointofsaleValidatorTest {

    @Test
    public void validate() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("fgfgfgf")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("dsfdsfs")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNullPointofsale() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("fgfgfgf")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("dsfdsfs")
                .build();
        List<String> errors = PointofsaleValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne doit pas etre null--"));
    }

    @Test
    public void validateNullAddressPointofsale() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("fgfgfgf")
                .posAddressDto(null)
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("dsfdsfs")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--L'address du Pointofsale ne peut etre null--"));
    }

    @Test
    public void validateBadAddressPointofsale() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("fgfgfgf")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail@.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("dsfdsfs")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The email when is precised should be a valid one"));
    }

    @Test
    public void validatePointofsaleMaxValue() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("dfddfdfdfdfdffdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfd")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("dfddfdfdfdfdffdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfd")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The pointofsale name cannot exceed 30 characters"));
        assertTrue(errors.contains("The pointofsale acronym cannot exceed 30 characters"));
    }

    @Test
    public void validatePointofsaleBlankValue() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("          ")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("           ")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The pointofsale name cannot be blank value"));
        assertTrue(errors.contains("The pointofsale acronym cannot be blank value"));
    }

    @Test
    public void validatePointofsaleEmptyValue() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The pointofsale name cannot be blank value"));
        assertTrue(errors.contains("The pointofsale acronym cannot be blank value"));
        assertTrue(errors.contains("--Le nom du point de vente ne peut etre vide--"));
        assertTrue(errors.contains("The pointofsale acronym cannot be empty value"));
        assertTrue(errors.contains("--L'acronym du point de vente ne peut etre vide--"));
        assertTrue(errors.contains("The pointofsale acronym cannot be empty value"));
    }

    @Test
    public void validatePointofsaleNullValue() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym(null)
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName(null)
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(8, errors.size());
        assertTrue(errors.contains("The pointofsale name cannot be blank value"));
        assertTrue(errors.contains("The pointofsale acronym cannot be blank value"));
        assertTrue(errors.contains("--Le nom du point de vente ne peut etre vide--"));
        assertTrue(errors.contains("The pointofsale acronym cannot be empty value"));
        assertTrue(errors.contains("--L'acronym du point de vente ne peut etre vide--"));
        assertTrue(errors.contains("The pointofsale acronym cannot be empty value"));
        assertTrue(errors.contains("The pointofsale acronym cannot be null"));
        assertTrue(errors.contains("The pointofsale name cannot be null"));
    }

    @Test
    public void validatePointofsaleCashAccountNull() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("null")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(null)
                .posDescription("fdfdfdf")
                .posEnterpriseDto(EnterpriseDto.builder().build())
                .posName("null")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le compte cash associe au point de vente ne peut etre null--"));
        assertTrue(errors.contains("The cash account of a pointofsale cannot be null"));
    }

    @Test
    public void validatePointofsaleEnterpriseNull() {
        PointofsaleDto posToValidate = PointofsaleDto.builder()
                .posAcronym("null")
                .posAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .posCashaccountDto(PosCashAccountDto.builder().build())
                .posDescription("fdfdfdf")
                .posEnterpriseDto(null)
                .posName("null")
                .build();
        List<String> errors = PointofsaleValidator.validate(posToValidate);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--L'entreprise associe au point de vente ne peut etre null--"));
        assertTrue(errors.contains("The enterprise owner of a pointofsale cannot be null"));
    }


}