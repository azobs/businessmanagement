package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleValidatorTest {

    @Test
    public void validate() {
        RoleDto roleDto1 = RoleDto.builder()
                .roleName(RoleType.Pdg)
                .roleAlias("PDG")
                .roleDescription("")
                .roleEntDto(EnterpriseDto.builder().build())
                .build();
        List<String> errors1 = RoleValidator.validate(roleDto1);
        System.out.println("errors1 are : "+errors1);
        assertNotNull(errors1);
        assertEquals(0, errors1.size());

        RoleDto roleDto2 = RoleDto.builder()
                //.roleName(RoleType.Pdg)
                .roleAlias("PDG")
                .roleDescription("")
                .roleEntDto(EnterpriseDto.builder().build())
                .build();
        List<String> errors2 = RoleValidator.validate(roleDto2);
        System.out.println("errors2 are : "+errors2);
        assertNotNull(errors2);
        assertEquals(2, errors2.size());
        assertTrue(errors2.contains("--Le nom du role doit etre precise--"));
        assertTrue(errors2.contains("The rolename cannot be null"));

        RoleDto roleDto3 = RoleDto.builder()
                .roleName(RoleType.Pdg)
                .roleAlias("  ")
                .roleDescription("   ")
                //.roleEntDto(EnterpriseDto.builder().build())
                .build();
        List<String> errors3 = RoleValidator.validate(roleDto3);
        System.out.println("errors3 are : "+errors3);
        assertNotNull(errors3);
        assertEquals(2, errors3.size());
        assertTrue(errors3.contains("Each role must belonging to an enterprise"));
        assertTrue(errors3.contains("--Chaque role doit appartenir a une entreprise--"));

        RoleDto roleDto4 = RoleDto.builder()
                .roleName(RoleType.Pdg)
                //.roleAlias("")
                .roleDescription("")
                .roleEntDto(EnterpriseDto.builder().build())
                .build();
        List<String> errors4 = RoleValidator.validate(roleDto4);
        System.out.println("errors4 are : "+errors4);
        assertNotNull(errors4);
        assertEquals(1, errors4.size());
        assertTrue(errors4.contains("The rolealias cannot be null"));

        RoleDto roleDto5 = RoleDto.builder()
                .roleName(RoleType.Pdg)
                .roleAlias("")
                .roleDescription("")
                .roleEntDto(EnterpriseDto.builder().build())
                .build();
        List<String> errors5 = RoleValidator.validate(roleDto5);
        System.out.println("errors5 are : "+errors5);
        assertNotNull(errors5);
        assertEquals(2, errors5.size());
        assertTrue(errors5.contains("--L'alias du role ne peut etre vide--"));
        assertTrue(errors5.contains("The rolealias size must be between 2 and 20 characters"));

        RoleDto roleDto6 = RoleDto.builder()
                .roleName(RoleType.Pdg)
                .roleAlias("PDG")
                .roleDescription("")
                //.roleEntDto(EnterpriseDto.builder().build())
                .build();
        List<String> errors6 = RoleValidator.validate(roleDto6);
        System.out.println("errors6 are : "+errors6);
        assertNotNull(errors6);
        assertEquals(2, errors6.size());
        assertTrue(errors6.contains("Each role must belonging to an enterprise"));
        assertTrue(errors6.contains("--Chaque role doit appartenir a une entreprise--"));

        List<String> errors7 = RoleValidator.validate(null);
        System.out.println("errors7 are : "+errors7);
        assertNotNull(errors7);
        assertEquals(1, errors7.size());
        assertTrue(errors7.contains("--Le parametre a valider ne peut etre null--"));
    }
}