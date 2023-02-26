package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class RoleServiceImplTest {

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;


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


    @Test
    public void saveRole_Valid() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);

        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved1 = roleService.saveRole(roleDtoToSaved1);
        assertNotNull(roleDtoSaved1);
        assertNotNull(roleDtoSaved1.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void saveRole_EnterpriseNull() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);

        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(null)
                .build();
        RoleDto roleDtoSaved1 = roleService.saveRole(roleDtoToSaved1);
            //L'exception devra etre lance pendant l'execution de la ligne ci-dessus
    }

    @Test(expected = DuplicateEntityException.class)
    public void saveRole_RoleTypeDuplicated() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);

        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved1 = roleService.saveRole(roleDtoToSaved1);
        assertNotNull(roleDtoSaved1);

        RoleDto roleDtoToSaved2 = RoleDto.builder()
                .roleDescription("ccxcxcxdfsdfsdfd")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved2 = roleService.saveRole(roleDtoToSaved2);
        //L'exception devra etre lance
    }

    @Test
    public void deleteRoleById_Valid() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);

        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved1 = roleService.saveRole(roleDtoToSaved1);
        assertNotNull(roleDtoSaved1);
        assertNotNull(roleDtoSaved1.getId());

        //Maintenant on doit delete ce role pour verifier que tout se passe bien quand on le fait
        Boolean b = roleService.deleteRoleById(roleDtoSaved1.getId());
        assertNotNull(b);
        assertTrue(b);
    }

    @Test
    public void deleteRoleByRoleName_Valid() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);

        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved1 = roleService.saveRole(roleDtoToSaved1);
        assertNotNull(roleDtoSaved1);
        assertNotNull(roleDtoSaved1.getId());

        //Maintenant on doit delete ce role pour verifier que tout se passe bien quand on le fait
        Boolean b = roleService.deleteRoleByRolename(roleDtoSaved1.getRoleName(), roleDtoSaved1.getRoleEntDto().getId());
        assertNotNull(b);
        assertTrue(b);
    }

    @Test
    public void findAllRoleOfEnterprise_Valid() {
        assertNotNull(roleService);
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);

        UserBMDto userBMSaved = saveUserBM();
        assertNotNull(userBMSaved);
        EnterpriseDto enterpriseDtoSaved = saveEnterprise(userBMSaved);
        assertNotNull(enterpriseDtoSaved);

        RoleDto roleDtoToSaved1 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Deliver)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved1 = roleService.saveRole(roleDtoToSaved1);
        assertNotNull(roleDtoSaved1);
        assertNotNull(roleDtoSaved1.getId());

        RoleDto roleDtoToSaved2 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Dg)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved2 = roleService.saveRole(roleDtoToSaved2);
        assertNotNull(roleDtoSaved2);
        assertNotNull(roleDtoSaved2.getId());

        RoleDto roleDtoToSaved3 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Manager)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved3 = roleService.saveRole(roleDtoToSaved3);
        assertNotNull(roleDtoSaved3);
        assertNotNull(roleDtoSaved3.getId());

        RoleDto roleDtoToSaved4 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Pdg)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved4 = roleService.saveRole(roleDtoToSaved4);
        assertNotNull(roleDtoSaved4);
        assertNotNull(roleDtoSaved4.getId());

        RoleDto roleDtoToSaved5 = RoleDto.builder()
                .roleDescription("ccxcxcx")
                .roleAlias("vendeur")
                .roleName(RoleType.Saler)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        RoleDto roleDtoSaved5 = roleService.saveRole(roleDtoToSaved5);
        assertNotNull(roleDtoSaved5);
        assertNotNull(roleDtoSaved5.getId());

        /*
            Maintenant on doit faire la liste de tous les roles de l'entreprise enregistre et comparer
            au nombre qu'on a enregistre
        */
        List<RoleDto> roleDtoList = roleService.findAllRoleOfEnterprise(enterpriseDtoSaved.getId());
        assertNotNull(roleDtoList);
        assertEquals(5, roleDtoList.size());

    }






























}