package com.c2psi.businessmanagement.validators.pos.pos;


import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnterpriseValidatorTest {

    @Test
    public void validateEnterprise() {
        EnterpriseDto entToValidate = EnterpriseDto.builder()
                .entAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entAcronym("ENT")
                .entAdminDto(UserBMDto.builder().build())
                .entName("enterprise")
                .entDescription("password")
                .entRegime("password")
                .entNiu("P121455")
                .entSocialreason("cscssds")
                .build();
        List<String> errors = EnterpriseValidator.validate(entToValidate);
        System.out.println("errors == "+errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateEnterpriseBadAddress() {
        EnterpriseDto entToValidate = EnterpriseDto.builder()
                .entAddressDto(AddressDto.builder()
                        .email("testsave@gmail@.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entAcronym("ENT")
                .entAdminDto(UserBMDto.builder().build())
                .entName("enterprise")
                .entDescription("password")
                .entRegime("password")
                .entNiu("P121455")
                .entSocialreason("cscssds")
                .build();
        List<String> errors = EnterpriseValidator.validate(entToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The email when is precised should be a valid one"));
    }

    @Test
    public void validateEnterpriseNullValue() {
        EnterpriseDto entToValidate = EnterpriseDto.builder()
                .entAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entAcronym("ENT")
                .entAdminDto(null)
                .entName(null)
                .entDescription("password")
                .entRegime("password")
                .entNiu("P121455")
                .entSocialreason("cscssds")
                .build();
        List<String> errors = EnterpriseValidator.validate(entToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("--Le user admin de l'entreprise ne peut etre null--"));
        assertTrue(errors.contains("The user admin of enterprise cannot be null"));
        assertTrue(errors.contains("The enterprise name of an enterprise cannot be null"));
        assertTrue(errors.contains("The enterprise name cannot be empty"));
        assertTrue(errors.contains("The entreprise name cannot be blank value"));
        assertTrue(errors.contains("--Le nom de l'entreprise ne peut etre vide--"));

    }

    @Test
    public void validateEnterpriseNull() {
        List<String> errors = EnterpriseValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateEnterpriseAddressNull() {
        EnterpriseDto entToValidate = EnterpriseDto.builder()
                .entAddressDto(null)
                .entAcronym("ENT")
                .entAdminDto(UserBMDto.builder().build())
                .entName("nulldsds")
                .entDescription("password")
                .entRegime("password")
                .entNiu("P121455")
                .entSocialreason("cscssds")
                .build();
        List<String> errors = EnterpriseValidator.validate(entToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--L'address du UserBM ne peut etre null--"));

    }

    @Test
    public void validateEnterpriseEmptyValue() {
        EnterpriseDto entToValidate = EnterpriseDto.builder()
                .entAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entAcronym("")
                .entAdminDto(UserBMDto.builder().build())
                .entName("")
                .entDescription("")
                .entRegime("")
                .entNiu("")
                .entSocialreason("")
                .build();
        List<String> errors = EnterpriseValidator.validate(entToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(15, errors.size());
        assertTrue(errors.contains("The regime of an enterprise cannot be empty"));
        assertTrue(errors.contains("The entreprise regime cannot be blank value"));
        assertTrue(errors.contains("The social reason of an enterprise cannot be empty"));
        assertTrue(errors.contains("The entreprise social reason cannot be blank value"));
        assertTrue(errors.contains("The enterprise description cannot be empty"));
        assertTrue(errors.contains("The entreprise description cannot be blank value"));
        assertTrue(errors.contains("The NIU of an enterprise cannot be empty"));
        assertTrue(errors.contains("The entreprise NIU cannot be blank value"));
        assertTrue(errors.contains("The enterprise name cannot be empty"));
        assertTrue(errors.contains("The entreprise name cannot be blank value"));
        assertTrue(errors.contains("The enterprise acronym cannot be empty"));
        assertTrue(errors.contains("The entreprise acronym cannot be blank value"));
        assertTrue(errors.contains("--Le regime de l'entreprise ne peut etre vide--"));
        assertTrue(errors.contains("--La raison sociale de l'entreprise ne peut etre vide--"));
        assertTrue(errors.contains("--Le nom de l'entreprise ne peut etre vide--"));
    }

    @Test
    public void validateEnterpriseBlankValue() {
        EnterpriseDto entToValidate = EnterpriseDto.builder()
                .entAddressDto(AddressDto.builder()
                        .email("testsave@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entAcronym("      ")
                .entAdminDto(UserBMDto.builder().build())
                .entName("     ")
                .entDescription("      ")
                .entRegime("     ")
                .entNiu("      ")
                .entSocialreason("      ")
                .build();
        List<String> errors = EnterpriseValidator.validate(entToValidate);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(6, errors.size());
        assertTrue(errors.contains("The entreprise regime cannot be blank value"));
        assertTrue(errors.contains("The entreprise social reason cannot be blank value"));
        assertTrue(errors.contains("The entreprise description cannot be blank value"));
        assertTrue(errors.contains("The entreprise NIU cannot be blank value"));
        assertTrue(errors.contains("The entreprise name cannot be blank value"));
        assertTrue(errors.contains("The entreprise acronym cannot be blank value"));
    }

}