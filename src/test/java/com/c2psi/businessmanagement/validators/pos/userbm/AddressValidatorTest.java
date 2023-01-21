package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressValidatorTest {

    @Test
    public void validate() {
        /**
         * Valid Address with all field attribute precised
         */
        AddressDto addressDto1 = AddressDto.builder()
                .email("validemail@gmail.com")
                .localisation("fdsfd")
                .numtel1("678470262")
                .numtel2("695093228")
                .numtel3("fdfdsf")
                .pays("fdfds")
                .quartier("fdsfds")
                .ville("fdfds")
                .build();
        List<String> errors1 = AddressValidator.validate(addressDto1);
        System.out.println("errors1 are : "+errors1);
        assertNotNull(errors1);
        assertEquals(0, errors1.size());
        /***
         * Valid address with only the necessary field attribute precised
         */
        AddressDto addressDto2 = AddressDto.builder()
                .email("validemail@gmail.com")
                //.localisation("fdsfd") because it is not compulsory
                .numtel1("678470262")
                .numtel2("695093228")
                //.numtel3("fdfdsf") because it is not compulsory
                //.pays("fdfds")
                //.quartier("fdsfds")
                //.ville("fdfds")
                .build();
        List<String> errors2 = AddressValidator.validate(addressDto2);
        System.out.println("errors2 are : "+errors2);
        assertNotNull(errors2);
        assertEquals(0, errors2.size());
        /***
         * Valid address with only the necessary field attribute precised
         */
        AddressDto addressDto3 = AddressDto.builder()
                .email("validemail@gmail")
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("678470262")
                .numtel2("695093228")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors3 = AddressValidator.validate(addressDto3);
        System.out.println("errors3 are : "+errors3);
        assertNotNull(errors3);
        assertEquals(0, errors3.size());
        /***
         * Valid address with:
         * null email
         */
        AddressDto addressDto4 = AddressDto.builder()
                //.email("validemail@gmail")
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("678470262")
                .numtel2("695093228")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors4 = AddressValidator.validate(addressDto4);
        System.out.println("errors4 are : "+errors4);
        assertNotNull(errors4);
        assertEquals(0, errors4.size());

        /***
         * Valid address with:
         * empty email, empty numtel1, empty numtel2
         * means we are waiting for 6 errors (the annotation violoted and our custom validation)
         */
        AddressDto addressDto5 = AddressDto.builder()
                //.email("")//because email can be null value
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("")
                .numtel2("")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors5 = AddressValidator.validate(addressDto5);
        System.out.println("errors5 are : "+errors5);
        assertNotNull(errors5);
        assertEquals(6, errors5.size());
        assertTrue(errors5.contains("The phone number 1 cannot be empty value"));
        assertTrue(errors5.contains("The phone number 1 cannot be blank value"));
        assertTrue(errors5.contains("The phone number 2 cannot be blank value"));
        assertTrue(errors5.contains("The phone number 2 cannot be empty value"));
        assertTrue(errors5.contains("--Le premier numero de telephone ne peut etre vide--"));
        assertTrue(errors5.contains("--Le second numero de telephone ne peut etre vide--"));

        /***
         * Valid address with:
         * null numtel1, null numtel2
         * means we are waiting for 6 errors (the annotation violoted and our custom validation)
         */
        AddressDto addressDto6 = AddressDto.builder()
                //.email("") //because email can be null without any problem
                .localisation("") //because it is not compulsory and can be empty value
                //.numtel1("")
                //.numtel2("")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors6 = AddressValidator.validate(addressDto6);
        System.out.println("errors6 are : "+errors6);
        assertNotNull(errors6);
        assertEquals(8, errors6.size());
        assertTrue(errors6.contains("The phone number 2 cannot be null"));
        assertTrue(errors6.contains("The phone number 2 cannot be empty value"));
        assertTrue(errors6.contains("The phone number 2 cannot be blank value"));
        assertTrue(errors6.contains("The phone number 1 cannot be null"));
        assertTrue(errors6.contains("The phone number 1 cannot be empty value"));
        assertTrue(errors6.contains("The phone number 1 cannot be blank value"));
        assertTrue(errors6.contains("--Le premier numero de telephone ne peut etre vide--"));
        assertTrue(errors6.contains("--Le second numero de telephone ne peut etre vide--"));

        /***
         * Valid address with:
         * blank numtel1, blank numtel2
         * means we are waiting for 6 errors (the annotation violoted and our custom validation)
         */
        AddressDto addressDto7 = AddressDto.builder()
                //.email("     ")
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("       ")
                .numtel2("          ")
                .numtel3("    ") //because it is not compulsory and can be blank value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors7 = AddressValidator.validate(addressDto7);
        System.out.println("errors7 are : "+errors7);
        assertNotNull(errors7);
        assertEquals(2, errors7.size());
        assertTrue(errors7.contains("The phone number 1 cannot be blank value"));
        assertTrue(errors7.contains("The phone number 2 cannot be blank value"));


        /***
         * Address with
         * bad email
         */
        AddressDto addressDto8 = AddressDto.builder()
                .email("validemail@gmail@ggg")
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("678470262")
                .numtel2("695093228")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors8 = AddressValidator.validate(addressDto8);
        System.out.println("errors8 are : "+errors8);
        assertNotNull(errors8);
        assertEquals(1, errors8.size());
        assertTrue(errors8.contains("The email when is precised should be a valid one"));

        /***
         * Address with
         * bad email
         */
        AddressDto addressDto9 = AddressDto.builder()
                .email("validemail")
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("678470262")
                .numtel2("695093228")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors9 = AddressValidator.validate(addressDto9);
        System.out.println("errors9 are : "+errors9);
        assertNotNull(errors9);
        assertEquals(1, errors9.size());
        assertTrue(errors9.contains("The email when is precised should be a valid one"));

        /***
         * Address with
         * bad email
         */
        AddressDto addressDto10 = AddressDto.builder()
                .email("validemail.com")
                .localisation("") //because it is not compulsory and can be empty value
                .numtel1("678470262")
                .numtel2("695093228")
                .numtel3("") //because it is not compulsory and can be empty value
                .pays("")
                .quartier("")
                .ville("")
                .build();
        List<String> errors10 = AddressValidator.validate(addressDto10);
        System.out.println("errors10 are : "+errors10);
        assertNotNull(errors10);
        assertEquals(1, errors10.size());
        assertTrue(errors10.contains("The email when is precised should be a valid one"));

        List<String> errors11 = AddressValidator.validate(null);
        System.out.println("errors11 are : "+errors11);
        assertNotNull(errors11);
        assertEquals(1, errors11.size());
        assertTrue(errors11.contains("--Le parametre a valider ne saurait etre null--"));

    }
}