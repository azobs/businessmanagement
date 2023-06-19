package com.c2psi.businessmanagement.validators.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoadingValidatorTest {

    @Test
    public void validate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode("dffdfdfd")
                    .loadComment("dfdfdfd")
                    .loadPosId(PointofsaleDto.builder().build().getId())
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(LoadingState.Edited)
                    .loadUserbmManagerDto(UserBMDto.builder().build())
                    .loadUserbmSalerDto(UserBMDto.builder().build())
                    .loadDate(sdf.parse("2021-05-15").toInstant())
                    .loadTotalamountexpected(BigDecimal.valueOf(1000))
                    .loadTotalamountpaid(BigDecimal.valueOf(900))
                    .build();

            List<String> errors = LoadingValidator.validate(loadingDtoToValidate);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(0, errors.size());
            /*assertTrue(errors.contains("--La quantité retourne d'un produit ne peut etre negative--"));
            assertTrue(errors.contains("The quantity return must be positive or null"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNull() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode("dffdfdfd")
                    .loadComment("dfdfdfd")
                    .loadPosId(PointofsaleDto.builder().build().getId())
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(LoadingState.Edited)
                    .loadUserbmManagerDto(UserBMDto.builder().build())
                    .loadUserbmSalerDto(UserBMDto.builder().build())
                    .loadDate(sdf.parse("2021-05-15").toInstant())
                    .loadTotalamountexpected(BigDecimal.valueOf(1000))
                    .loadTotalamountpaid(BigDecimal.valueOf(900))
                    .build();

            List<String> errors = LoadingValidator.validate(null);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(1, errors.size());
            assertTrue(errors.contains("--Le  parametre a valider ne saurait etre null--"));
            /*assertTrue(errors.contains("The quantity return must be positive or null"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode(null)
                    .loadComment("dfdfdfd")
                    .loadPosId(null)
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(null)
                    .loadUserbmManagerDto(null)
                    .loadUserbmSalerDto(null)
                    .loadDate(null)
                    .loadTotalamountexpected(BigDecimal.valueOf(1000))
                    .loadTotalamountpaid(BigDecimal.valueOf(900))
                    .build();

            List<String> errors = LoadingValidator.validate(loadingDtoToValidate);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(13, errors.size());
            assertTrue(errors.contains("The loading code cannot be null"));
            assertTrue(errors.contains("The loading code cannot be empty"));
            assertTrue(errors.contains("The loading code cannot be blank value"));
            assertTrue(errors.contains("--Le code de la commande ne peut etre null--"));
        assertTrue(errors.contains("The loading date cannot be null"));
        assertTrue(errors.contains("The loading state cannot be null"));
        assertTrue(errors.contains("--Le point de vente du chargement ne peut etre null--"));
        assertTrue(errors.contains("The pointofsale in which the loading has been done cannot be null"));
        assertTrue(errors.contains("--L'utilisateur qui enregistre le chargement ne peut etre null--"));
        assertTrue(errors.contains("--L'utilisateur qui va vendre le chargement ne peut etre null--"));
        assertTrue(errors.contains("The user who fill the loading cannot be null"));
        assertTrue(errors.contains("The user saler responsible of the loading cannot be null"));
        assertTrue(errors.contains("--La date de chargement ne saurait etre vide--"));



        /*} catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void validateNullPointofsale() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode("null")
                    .loadComment("dfdfdfd")
                    .loadPosId(null)
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(LoadingState.Edited)
                    .loadUserbmManagerDto(UserBMDto.builder().build())
                    .loadUserbmSalerDto(UserBMDto.builder().build())
                    .loadDate(sdf.parse("2021-05-15").toInstant())
                    .loadTotalamountexpected(BigDecimal.valueOf(1000))
                    .loadTotalamountpaid(BigDecimal.valueOf(900))
                    .build();

            List<String> errors = LoadingValidator.validate(loadingDtoToValidate);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le point de vente du chargement ne peut etre null--"));
            assertTrue(errors.contains("The pointofsale in which the loading has been done cannot be null"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullUserManagerAndSaler() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode("null")
                    .loadComment("dfdfdfd")
                    .loadPosId(PointofsaleDto.builder().build().getId())
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(LoadingState.Edited)
                    .loadUserbmManagerDto(null)
                    .loadUserbmSalerDto(null)
                    .loadDate(sdf.parse("2021-05-15").toInstant())
                    .loadTotalamountexpected(BigDecimal.valueOf(1000))
                    .loadTotalamountpaid(BigDecimal.valueOf(900))
                    .build();

            List<String> errors = LoadingValidator.validate(loadingDtoToValidate);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(4, errors.size());
            assertTrue(errors.contains("--L'utilisateur qui enregistre le chargement ne peut etre null--"));
            assertTrue(errors.contains("--L'utilisateur qui va vendre le chargement ne peut etre null--"));
            assertTrue(errors.contains("The user saler responsible of the loading cannot be null"));
            assertTrue(errors.contains("The user who fill the loading cannot be null"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullAmountExpected() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode("null")
                    .loadComment("dfdfdfd")
                    .loadPosId(PointofsaleDto.builder().build().getId())
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(LoadingState.Edited)
                    .loadUserbmManagerDto(UserBMDto.builder().build())
                    .loadUserbmSalerDto(UserBMDto.builder().build())
                    .loadDate(sdf.parse("2021-05-15").toInstant())
                    .loadTotalamountexpected(null)
                    .loadTotalamountpaid(null)
                    .build();

            List<String> errors = LoadingValidator.validate(loadingDtoToValidate);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("--Le montant attendu ne peut etre non null--"));
            assertTrue(errors.contains("The amount expected for a loading cannot be null"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNegativeAmount() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LoadingDto loadingDtoToValidate = LoadingDto.builder()
                    .loadCode("null")
                    .loadComment("dfdfdfd")
                    .loadPosId(PointofsaleDto.builder().build().getId())
                    .loadSalereport("fdfdfdfdfd")
                    //.loadState(LoadingState.Edited)
                    .loadUserbmManagerDto(UserBMDto.builder().build())
                    .loadUserbmSalerDto(UserBMDto.builder().build())
                    .loadDate(sdf.parse("2021-05-15").toInstant())
                    .loadTotalamountexpected(BigDecimal.valueOf(-1500))
                    .loadTotalamountpaid(BigDecimal.valueOf(-1000))
                    .build();

            List<String> errors = LoadingValidator.validate(loadingDtoToValidate);
            System.out.println("errors are : "+errors);
            assertNotNull(errors);
            assertEquals(4, errors.size());
            assertTrue(errors.contains("The total amount expected must be positive"));
            assertTrue(errors.contains("The total amount expected must be positive or null"));
            assertTrue(errors.contains("--Le montant attendu du chargement ne saurait etre negatif--"));
            assertTrue(errors.contains("--Le montant versé du chargement ne saurait etre negatif--"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



}