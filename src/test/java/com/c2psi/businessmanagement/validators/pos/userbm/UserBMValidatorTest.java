package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserBMValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260")
                    .bmDob(sdf.parse("1942-05-15"))
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            assertNotNull(errors);
            assertEquals(0, errors.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMNull() {
        List<String> errors = UserBMValidator.validate(null);
        System.out.println("errors == "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateUserBMWithNullAdress() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(null)
                    .bmCni("107235260")
                    .bmDob(sdf.parse("1942-05-15"))
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--L'address du UserBM ne peut etre null--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMAddress() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail@.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260")
                    .bmDob(sdf.parse("1942-05-15"))
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The email when is precised should be a valid one"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMNotBlank() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("            ")
                    .bmDob(sdf.parse("1942-05-15"))
                    .bmLogin("           ")
                    .bmName("          ")
                    .bmPassword("       ")
                    .bmRepassword("          ")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(6, errors.size());
            assertTrue(errors.contains("The user login cannot be blank value"));
            assertTrue(errors.contains("The user password cannot be blank value"));
            assertTrue(errors.contains("The user repassword cannot be blank value"));
            assertTrue(errors.contains("The user name cannot be blank value"));
            assertTrue(errors.contains("The user cni number if is precised cannot be an empty value"));
            assertTrue(errors.contains("--Le mot de passe doit être identique en tout point à sa confirmation--"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMNotEmpty() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("")
                    .bmDob(sdf.parse("1942-05-15"))
                    .bmLogin("")
                    .bmName("")
                    .bmPassword("")
                    .bmRepassword("")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(13, errors.size());
            assertTrue(errors.contains("The user login cannot be empty"));
            assertTrue(errors.contains("The user login cannot be blank value"));
            assertTrue(errors.contains("The user password cannot be empty"));
            assertTrue(errors.contains("The user password cannot be blank value"));
            assertTrue(errors.contains("The user repassword cannot be empty"));
            assertTrue(errors.contains("The user repassword cannot be blank value"));
            assertTrue(errors.contains("The user name cannot be empty"));
            assertTrue(errors.contains("The user name cannot be blank value"));
            assertTrue(errors.contains("--Le login du user ne peut etre vide--"));
            assertTrue(errors.contains("--Le Name du user ne peut etre vide--"));
            assertTrue(errors.contains("--Le password du user ne peut etre vide--"));
            assertTrue(errors.contains("The user login size must be at least 3 and at most 20"));
            assertTrue(errors.contains("--Le Cni quand il est precise ne peut etre vide--"));


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMNotNull() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni(null)
                    .bmDob(sdf.parse("1942-05-15"))
                    .bmLogin(null)
                    .bmName(null)
                    .bmPassword(null)
                    .bmRepassword(null)
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(15, errors.size());
            assertTrue(errors.contains("--Le login du user ne peut etre vide--"));
            assertTrue(errors.contains("--Le Name du user ne peut etre vide--"));
            assertTrue(errors.contains("--Le password du user ne peut etre vide--"));
            assertTrue(errors.contains("The user password cannot be empty"));
            assertTrue(errors.contains("The user login cannot be blank value"));
            assertTrue(errors.contains("The user name cannot be blank value"));
            assertTrue(errors.contains("The user password cannot be null"));
            assertTrue(errors.contains("The user repassword cannot be blank value"));
            assertTrue(errors.contains("The user login cannot be null"));
            assertTrue(errors.contains("The user login cannot be empty"));
            assertTrue(errors.contains("The user repassword cannot be null"));
            assertTrue(errors.contains("The user name cannot be null"));
            assertTrue(errors.contains("The user name cannot be empty"));
            assertTrue(errors.contains("The user password cannot be blank value"));
            assertTrue(errors.contains("The user repassword cannot be empty"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMDobInFuture() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260")
                    .bmDob(sdf.parse("2023-05-15"))
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--La date de naissance du user ne saurait etre ultérieure a la date courante--"));
            assertTrue(errors.contains("The user date of birth cannot be the current date or a date in the future"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateUserBMDobNull() {

            UserBMDto userBMToValidate = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260")
                    .bmDob(null)
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            List<String> errors = UserBMValidator.validate(userBMToValidate);
            System.out.println("errors == "+errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--La date de naissance du user ne saurait etre null--"));
            assertTrue(errors.contains("The date of birth of the user cannot be null"));
    }

}