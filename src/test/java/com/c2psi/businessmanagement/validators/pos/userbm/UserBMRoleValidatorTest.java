package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserBMRoleValidatorTest {

    @Test
    public void validateNull() {
        List<String> errors = UserBMRoleValidator.validate(null);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateRoleNull() {
        UserBMRoleDto userBMRoleDto = UserBMRoleDto.builder()
                .userbmroleAttributionDate(new Date().toInstant())//Instant.now();
                .userbmroleRoleDto(null)
                .userbmroleUserbmDto(UserBMDto.builder().build())
                .build();
        List<String> errors = UserBMRoleValidator.validate(userBMRoleDto);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("--Le role associe a un userBm ne peut etre null--"));
        assertTrue(errors.contains("The role to be attributed cannot be null"));
    }

    @Test
    public void validateUserBMNull() {
        UserBMRoleDto userBMRoleDto = UserBMRoleDto.builder()
                .userbmroleAttributionDate(new Date().toInstant())//Instant.now();
                .userbmroleRoleDto(RoleDto.builder().build())
                .userbmroleUserbmDto(null)
                .build();
        List<String> errors = UserBMRoleValidator.validate(userBMRoleDto);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The userbm to be attributed cannot be null"));
        assertTrue(errors.contains("--L'utilisateur auquel le role a ete associe ne peut etre null--"));
    }

    @Test
    public void validateAttributionDateNull() {
        UserBMRoleDto userBMRoleDto = UserBMRoleDto.builder()
                .userbmroleAttributionDate(null)//Instant.now();
                .userbmroleRoleDto(RoleDto.builder().build())
                .userbmroleUserbmDto(UserBMDto.builder().build())
                .build();
        List<String> errors = UserBMRoleValidator.validate(userBMRoleDto);
        System.out.println("errors are : "+errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("Attribution date cannot be null"));
        assertTrue(errors.contains("--La date d'attribution du role au userbm ne peut etre null--"));
    }

    @Test
    public void validateAttributionDateIsNotValid() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            UserBMRoleDto userBMRoleDto = UserBMRoleDto.builder()
                    .userbmroleAttributionDate(sdf.parse("2023-12-23").toInstant())//Instant.now();
                    .userbmroleRoleDto(RoleDto.builder().build())
                    .userbmroleUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = UserBMRoleValidator.validate(userBMRoleDto);
            System.out.println("errors are : " + errors);
            assertNotNull(errors);
            assertEquals(2, errors.size());
            assertTrue(errors.contains("The attribution date cannot be in the future"));
            assertTrue(errors.contains("--La date d'attribution du role ne peut etre ulterieure a la date courante--"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateAttributionDateIsValid() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            UserBMRoleDto userBMRoleDto = UserBMRoleDto.builder()
                    .userbmroleAttributionDate(sdf.parse("2022-12-23").toInstant())//Instant.now();
                    .userbmroleRoleDto(RoleDto.builder().build())
                    .userbmroleUserbmDto(UserBMDto.builder().build())
                    .build();
            List<String> errors = UserBMRoleValidator.validate(userBMRoleDto);
            System.out.println("errors are : " + errors);
            assertNotNull(errors);
            assertEquals(0, errors.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}