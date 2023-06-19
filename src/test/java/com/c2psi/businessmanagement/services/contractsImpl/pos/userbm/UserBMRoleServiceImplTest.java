package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class UserBMRoleServiceImplTest {

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    UserBMRoleServiceImpl userBMRoleService;


    public UserBMDto saveUserBM(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
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
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            return userBMSaved;
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public UserBMDto saveUserBM1(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave1@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("1072352601")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            return userBMSaved;
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public EnterpriseDto saveEnterprise(UserBMDto userBMSaved){
        EnterpriseDto enterpriseDtoToSave = EnterpriseDto.builder()
                .entAdminDto(userBMSaved)
                .entAddressDto(AddressDto.builder()
                        .email("testsaveent@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entNiu("P012546897")
                .entAcronym("C2PSI")
                .entDescription("vente de boisson")
                .entName("Commerce general DJOUTSA")
                .entRegime("Reel")
                .entSocialreason("Commerce general")
                .build();
        EnterpriseDto enterpriseDtoSaved = enterpriseService.saveEnterprise(enterpriseDtoToSave);
        return enterpriseDtoSaved;
    }

    public RoleDto saveRole(EnterpriseDto enterpriseDtoSaved){
        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved = roleService.saveRole(roleDtoToSaved1);
        return roleDtoSaved;
    }

    public RoleDto saveRole1(EnterpriseDto enterpriseDtoSaved){
        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("dg")
                .roleName(RoleType.Dg)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved = roleService.saveRole(roleDtoToSaved1);
        return roleDtoSaved;
    }

    public RoleDto saveRole2(EnterpriseDto enterpriseDtoSaved){
        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("manager")
                .roleName(RoleType.Manager)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved = roleService.saveRole(roleDtoToSaved1);
        return roleDtoSaved;
    }

    public RoleDto saveRole3(EnterpriseDto enterpriseDtoSaved){
        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("pdg")
                .roleName(RoleType.Pdg)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved = roleService.saveRole(roleDtoToSaved1);
        return roleDtoSaved;
    }


    @Test
    public void saveUserBMRole_Valid() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void saveUserBMRole_NotValid(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(null)
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
    }

    @Test
    public void  findUserBMRoleById_Valid(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleById(userBMRoleDtoSaved.getId());
        assertNotNull(userBMRoleDtoFound);
        assertEquals(userBMRoleDtoSaved.getId(), userBMRoleDtoFound.getId());
    }

    @Test(expected = NullArgumentException.class)
    public void  findUserBMRoleById_NullArgument(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleById(null);
        //The exception will be launched normally
    }

    @Test(expected = EntityNotFoundException.class)
    public void  findUserBMRoleById_NotFound(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleById(userBMRoleDtoSaved.getId() + 1202);
        //The exception will be launched normally
    }

    @Test
    public void  findUserBMRoleByUserBMandRole_Valid(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleByUserBMandRole(userBMSaved, roleDtoSaved);
        assertNotNull(userBMRoleDtoFound);
        assertEquals(userBMRoleDtoSaved.getId(), userBMRoleDtoFound.getId());
    }

    @Test(expected = NullArgumentException.class)
    public void  findUserBMRoleByUserBMandRole_NullArgument(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleByUserBMandRole(null, null);
        //L'exception devra etre lance
    }

    @Test(expected = EntityNotFoundException.class)
    public void  findUserBMRoleByUserBMandRole_EntityNotFound(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved1 = saveUserBM();
        assertNotNull(userBMSaved1);
        UserBMDto userBMSaved2 = saveUserBM1();
        assertNotNull(userBMSaved2);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved1);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved1)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleByUserBMandRole(userBMSaved2, roleDtoSaved);
        //L'exception devra etre lance
    }

    @Test
    public void deleteUserBMRoleById(){
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        assertNotNull(userBMRoleService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);
        RoleDto roleDtoSaved = saveRole(enterpriseDtoSaved);
        assertNotNull(roleDtoSaved);

        UserBMRoleDto userBMRoleDtoToSaved = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();
        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSaved);
        assertNotNull(userBMRoleDtoSaved);
        System.out.println("id = "+userBMRoleDtoSaved.getId());
        Boolean b = userBMRoleService.deleteUserBMRoleById(userBMRoleDtoSaved.getId());
        assertNotNull(b);
        assertEquals(true, b.booleanValue());

    }
























}