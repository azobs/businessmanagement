package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class EnterpriseServiceImplTest {

    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;

    @Test
    public void saveEnterprise_Valid() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= InvalidEntityException.class)
    public void saveEnterprise_InvalidData() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(null)
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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= InvalidEntityException.class)
    public void saveEnterprise_AdminEnterpriseIsMissing() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsaveent@gmail.com")
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
            //UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            EnterpriseDto enterpriseDtoToSave = EnterpriseDto.builder()
                    .entAdminDto(userBMToSave)
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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= InvalidEntityException.class)
    public void saveEnterprise_AdminIsNotAdminEnterprise() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsaveent@gmail.com")
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);

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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= DuplicateEntityException.class)
    public void saveEnterprise_DuplicateEnterprise() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsaveent@gmail.com")
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

            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            EnterpriseDto enterpriseDtoToSave2 = EnterpriseDto.builder()
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

            EnterpriseDto enterpriseDtoSaved2 = enterpriseService.saveEnterprise(enterpriseDtoToSave2);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateEnterprise_Valid() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
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

            //On enregistre une entreprise qu'on va modifier par la suite
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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());

            //On fait la recherche de l'entreprise a modifier
            EnterpriseDto enterpriseDtoToUpdate = enterpriseService.findEnterpriseById(enterpriseDtoSaved.getId());
            //On realise les modifications
            enterpriseDtoToUpdate.getEntAddressDto().setEmail("nouvelemail@gmail.com");
            enterpriseDtoToUpdate.setEntName("Commerce General Famille Azobou");
            enterpriseDtoToUpdate.setEntNiu("PZR02154879");
            enterpriseDtoToUpdate.setEntAcronym("C3psi");
            //On lance donc la mise a jour
            EnterpriseDto enterpriseDtoUpdate = enterpriseService.updateEnterprise(enterpriseDtoToUpdate);
            assertNotNull(enterpriseDtoUpdate);
            assertNotNull(enterpriseDtoUpdate.getId());
            assertEquals(enterpriseDtoToUpdate.getEntNiu(), enterpriseDtoUpdate.getEntNiu());
            assertEquals(enterpriseDtoToUpdate.getEntAcronym(), enterpriseDtoUpdate.getEntAcronym());
            assertEquals(enterpriseDtoToUpdate.getEntName(), enterpriseDtoUpdate.getEntName());
            assertEquals(enterpriseDtoToUpdate.getEntAddressDto().getEmail(), enterpriseDtoUpdate.getEntAddressDto().getEmail());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= InvalidEntityException.class)
    public void updateEnterprise_EnterpriseNotValidBecauseOfNiu() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
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

            //On enregistre une entreprise qu'on va modifier par la suite
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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());

            //On fait la recherche de l'entreprise a modifier
            EnterpriseDto enterpriseDtoToUpdate = enterpriseService.findEnterpriseById(enterpriseDtoSaved.getId());
            //On realise les modifications
            enterpriseDtoToUpdate.getEntAddressDto().setEmail("nouvelemail@gmail.com");
            enterpriseDtoToUpdate.setEntName("Commerce General Famille Azobou");
            enterpriseDtoToUpdate.setEntNiu(null);
            enterpriseDtoToUpdate.setEntAcronym("C3psi");
            //On lance donc la mise a jour
            EnterpriseDto enterpriseDtoUpdate = enterpriseService.updateEnterprise(enterpriseDtoToUpdate);
            assertNotNull(enterpriseDtoUpdate);
            assertNotNull(enterpriseDtoUpdate.getId());
            assertEquals(enterpriseDtoToUpdate.getEntNiu(), enterpriseDtoUpdate.getEntNiu());
            assertEquals(enterpriseDtoToUpdate.getEntAcronym(), enterpriseDtoUpdate.getEntAcronym());
            assertEquals(enterpriseDtoToUpdate.getEntName(), enterpriseDtoUpdate.getEntName());
            assertEquals(enterpriseDtoToUpdate.getEntAddressDto().getEmail(), enterpriseDtoUpdate.getEntAddressDto().getEmail());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= EntityNotFoundException.class)
    public void updateEnterprise_EnterpriseNotFoundBecauseOfId() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
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

            //On enregistre une entreprise qu'on va modifier par la suite
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
            assertNotNull(enterpriseDtoSaved);
            assertNotNull(enterpriseDtoSaved.getId());

            //On fait la recherche de l'entreprise a modifier
            enterpriseDtoSaved.setId(Long.valueOf(4512879));
            EnterpriseDto enterpriseDtoToUpdate = enterpriseService.findEnterpriseById(enterpriseDtoSaved.getId());
            //On realise les modifications
            enterpriseDtoToUpdate.getEntAddressDto().setEmail("nouvelemail@gmail.com");
            enterpriseDtoToUpdate.setEntName("Commerce General Famille Azobou");
            enterpriseDtoToUpdate.setEntNiu("PZSdfdk458");
            enterpriseDtoToUpdate.setEntAcronym("C3psi");
            //On lance donc la mise a jour
            EnterpriseDto enterpriseDtoUpdate = enterpriseService.updateEnterprise(enterpriseDtoToUpdate);
            assertNotNull(enterpriseDtoUpdate);
            assertNotNull(enterpriseDtoUpdate.getId());
            assertEquals(enterpriseDtoToUpdate.getEntNiu(), enterpriseDtoUpdate.getEntNiu());
            assertEquals(enterpriseDtoToUpdate.getEntAcronym(), enterpriseDtoUpdate.getEntAcronym());
            assertEquals(enterpriseDtoToUpdate.getEntName(), enterpriseDtoUpdate.getEntName());
            assertEquals(enterpriseDtoToUpdate.getEntAddressDto().getEmail(), enterpriseDtoUpdate.getEntAddressDto().getEmail());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void updateEnterprise_EnterpriseDuplicateBecauseOfEmail() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
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

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On enregistre une 2eme entreprise qui va creer le conflit avec la premiere
            EnterpriseDto enterpriseDtoToSave2 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved)
                    .entAddressDto(AddressDto.builder()
                            .email("testsaveent_1@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .entNiu("P012546897_1")
                    .entAcronym("C2PSI")
                    .entDescription("vente de boisson")
                    .entName("Commerce general DJOUTSA_1")
                    .entRegime("Reel")
                    .entSocialreason("Commerce general")
                    .build();
            EnterpriseDto enterpriseDtoSaved2 = enterpriseService.saveEnterprise(enterpriseDtoToSave2);
            assertNotNull(enterpriseDtoSaved2);
            assertNotNull(enterpriseDtoSaved2.getId());

            //On fait la recherche de l'entreprise a modifier
            EnterpriseDto enterpriseDtoToUpdate = enterpriseService.findEnterpriseById(enterpriseDtoSaved1.getId());
            //On modifie enterpriseDtoSaved1 en lui donnant le meme email que enterpriseDtoSaved2
            enterpriseDtoToUpdate.getEntAddressDto().setEmail(enterpriseDtoSaved2.getEntAddressDto().getEmail());
            enterpriseDtoToUpdate.setEntName("Commerce General Famille Azobou");
            enterpriseDtoToUpdate.setEntNiu("PDG45874");
            enterpriseDtoToUpdate.setEntAcronym("C3psi");
            //On lance donc la mise a jour
            EnterpriseDto enterpriseDtoUpdate = enterpriseService.updateEnterprise(enterpriseDtoToUpdate);
            //L'exception doit donc normallement etre lance
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void updateEnterprise_EnterpriseDuplicateBecauseOfNiu() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
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

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On enregistre une 2eme entreprise qui va creer le conflit avec la premiere
            EnterpriseDto enterpriseDtoToSave2 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved)
                    .entAddressDto(AddressDto.builder()
                            .email("testsaveent_1@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .entNiu("P012546897_1")
                    .entAcronym("C2PSI")
                    .entDescription("vente de boisson")
                    .entName("Commerce general DJOUTSA_1")
                    .entRegime("Reel")
                    .entSocialreason("Commerce general")
                    .build();
            EnterpriseDto enterpriseDtoSaved2 = enterpriseService.saveEnterprise(enterpriseDtoToSave2);
            assertNotNull(enterpriseDtoSaved2);
            assertNotNull(enterpriseDtoSaved2.getId());

            //On fait la recherche de l'entreprise a modifier
            EnterpriseDto enterpriseDtoToUpdate = enterpriseService.findEnterpriseById(enterpriseDtoSaved1.getId());
            //On modifie enterpriseDtoSaved1 en lui donnant le meme NIU que enterpriseDtoSaved2
            enterpriseDtoToUpdate.getEntAddressDto().setEmail("nouvelemail@gmail.com");
            enterpriseDtoToUpdate.setEntName("Commerce General Famille Azobou");
            enterpriseDtoToUpdate.setEntNiu(enterpriseDtoSaved2.getEntNiu());
            enterpriseDtoToUpdate.setEntAcronym("C3psi");
            //On lance donc la mise a jour
            EnterpriseDto enterpriseDtoUpdate = enterpriseService.updateEnterprise(enterpriseDtoToUpdate);
            //L'exception doit donc normallement etre lance
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void updateEnterprise_EnterpriseDuplicateBecauseOfName() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
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

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On enregistre une 2eme entreprise qui va creer le conflit avec la premiere
            EnterpriseDto enterpriseDtoToSave2 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved)
                    .entAddressDto(AddressDto.builder()
                            .email("testsaveent_1@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .entNiu("P012546897_1")
                    .entAcronym("C2PSI")
                    .entDescription("vente de boisson")
                    .entName("Commerce general DJOUTSA_1")
                    .entRegime("Reel")
                    .entSocialreason("Commerce general")
                    .build();
            EnterpriseDto enterpriseDtoSaved2 = enterpriseService.saveEnterprise(enterpriseDtoToSave2);
            assertNotNull(enterpriseDtoSaved2);
            assertNotNull(enterpriseDtoSaved2.getId());

            //On fait la recherche de l'entreprise a modifier
            EnterpriseDto enterpriseDtoToUpdate = enterpriseService.findEnterpriseById(enterpriseDtoSaved1.getId());
            //On modifie enterpriseDtoSaved1 en lui donnant le meme nom que enterpriseDtoSaved2
            enterpriseDtoToUpdate.getEntAddressDto().setEmail("nouvelemail@gmail.com");
            enterpriseDtoToUpdate.setEntName(enterpriseDtoSaved2.getEntName());
            enterpriseDtoToUpdate.setEntNiu("PG4578121");
            enterpriseDtoToUpdate.setEntAcronym("C3psi");
            //On lance donc la mise a jour
            EnterpriseDto enterpriseDtoUpdate = enterpriseService.updateEnterprise(enterpriseDtoToUpdate);
            //L'exception doit donc normallement etre lance
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void setAdminEnterprise_Valid() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On prepare et enregistre un autre AdminEnterprise car l'un sera utilise pour mettre l'autre a jour
            UserBMDto userBMToSave2 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave2@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260_2")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin2")
                    .bmName("admin2")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave2);
            assertNotNull(userBMSaved2);
            assertNotNull(userBMSaved2.getId());


            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc mettre a jour l'admin de l'entreprise
            EnterpriseDto enterpriseDtoSetAdmin = enterpriseService.setAdminEnterprise(enterpriseDtoSaved1.getId(),
                    userBMSaved2.getId());
            assertNotNull(enterpriseDtoSetAdmin);
            assertEquals(userBMSaved2, enterpriseDtoSetAdmin.getEntAdminDto());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullArgumentException.class)
    public void setAdminEnterprise_EnterpriseNull() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On prepare et enregistre un autre AdminEnterprise car l'un sera utilise pour mettre l'autre a jour
            UserBMDto userBMToSave2 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave2@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260_2")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin2")
                    .bmName("admin2")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave2);
            assertNotNull(userBMSaved2);
            assertNotNull(userBMSaved2.getId());

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc mettre a jour l'admin de l'entreprise
            EnterpriseDto enterpriseDtoSetAdmin = enterpriseService.setAdminEnterprise(null,
                    userBMSaved2.getId());
            //L'exception devra etre lance
            /*assertNotNull(enterpriseDtoSetAdmin);
            assertEquals(userBMSaved2, enterpriseDtoSetAdmin.getEntAdminDto());*/

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void setAdminEnterprise_WrongEnterpriseID() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On prepare et enregistre un autre AdminEnterprise car l'un sera utilise pour mettre l'autre a jour
            UserBMDto userBMToSave2 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave2@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260_2")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin2")
                    .bmName("admin2")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave2);
            assertNotNull(userBMSaved2);
            assertNotNull(userBMSaved2.getId());


            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc mettre a jour l'admin de l'entreprise
            EnterpriseDto enterpriseDtoSetAdmin = enterpriseService.setAdminEnterprise(enterpriseDtoSaved1.getId()+1045,
                    userBMSaved2.getId());
            assertNotNull(enterpriseDtoSetAdmin);
            assertEquals(userBMSaved2, enterpriseDtoSetAdmin.getEntAdminDto());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void setAdminEnterprise_WrongUserBMID() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On prepare et enregistre un autre AdminEnterprise car l'un sera utilise pour mettre l'autre a jour
            UserBMDto userBMToSave2 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave2@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260_2")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin2")
                    .bmName("admin2")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave2);
            assertNotNull(userBMSaved2);
            assertNotNull(userBMSaved2.getId());


            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc mettre a jour l'admin de l'entreprise
            EnterpriseDto enterpriseDtoSetAdmin = enterpriseService.setAdminEnterprise(enterpriseDtoSaved1.getId(),
                    userBMSaved2.getId()+1452);
            assertNotNull(enterpriseDtoSetAdmin);
            assertEquals(userBMSaved2, enterpriseDtoSetAdmin.getEntAdminDto());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void setAdminEnterprise_WrongTypeOfUserBM() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On prepare et enregistre un autre AdminEnterprise car l'un sera utilise pour mettre l'autre a jour
            UserBMDto userBMToSave2 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave2@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260_2")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin2")
                    .bmName("admin2")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave2);
            assertNotNull(userBMSaved2);
            assertNotNull(userBMSaved2.getId());


            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc mettre a jour l'admin de l'entreprise
            EnterpriseDto enterpriseDtoSetAdmin = enterpriseService.setAdminEnterprise(enterpriseDtoSaved1.getId(),
                    userBMSaved2.getId());
            assertNotNull(enterpriseDtoSetAdmin);
            assertEquals(userBMSaved2, enterpriseDtoSetAdmin.getEntAdminDto());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteEnterpriseByName_Valid() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc rechercher et supprimer l'entreprise
            Boolean delete = enterpriseService.deleteEnterpriseByName(enterpriseDtoSaved1.getEntName());
            assertTrue(delete);
            //Comme on a deja supprimer si on supprime encore le meme enregistrement on doit avoir false
            assertFalse(enterpriseService.deleteEnterpriseByName(enterpriseDtoSaved1.getEntName()));

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullArgumentException.class)
    public void deleteEnterpriseByName_NullArgument() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc rechercher et supprimer l'entreprise
            Boolean delete = enterpriseService.deleteEnterpriseByName(null);
            //L'exception devrait etre lance

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteEnterpriseByNiu_Valid() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc rechercher et supprimer l'entreprise
            Boolean delete = enterpriseService.deleteEnterpriseByNiu(enterpriseDtoSaved1.getEntNiu());
            assertTrue(delete);
            //Comme on a deja supprimer si on supprime encore le meme enregistrement on doit avoir false
            assertFalse(enterpriseService.deleteEnterpriseByNiu(enterpriseDtoSaved1.getEntNiu()));

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullArgumentException.class)
    public void deleteEnterpriseByNiu_NullArgument() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc rechercher et supprimer l'entreprise
            Boolean delete = enterpriseService.deleteEnterpriseByNiu(null);
            //L'exception devra etre lance

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteEnterpriseById_Valid() {
        assertNotNull(userBMService);
        assertNotNull(enterpriseService);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On prepare et enregistre un adminEnterprise
            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("107235260_1")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved1 = userBMService.saveUserBM(userBMToSave1);
            assertNotNull(userBMSaved1);
            assertNotNull(userBMSaved1.getId());

            //On enregistre une 1ere entreprise qu'on va modifier par la suite
            EnterpriseDto enterpriseDtoToSave1 = EnterpriseDto.builder()
                    .entAdminDto(userBMSaved1)
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
            EnterpriseDto enterpriseDtoSaved1 = enterpriseService.saveEnterprise(enterpriseDtoToSave1);
            assertNotNull(enterpriseDtoSaved1);
            assertNotNull(enterpriseDtoSaved1.getId());

            //On va donc rechercher et supprimer l'entreprise
            Boolean delete = enterpriseService.deleteEnterpriseById(enterpriseDtoSaved1.getId());
            assertTrue(delete);
            //Comme on a deja supprimer si on supprime encore le meme enregistrement on doit avoir false
            assertFalse(enterpriseService.deleteEnterpriseById(enterpriseDtoSaved1.getId()));

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllEnterprise() {
    }

    @Test
    public void findAllPosofEnterprise() {
    }

    @Test
    public void getTurnover() {
    }

    @Test
    public void findAllEmployeofEnterprise() {
    }

    @Test
    public void findAllProviderofEnterprise() {
    }

    @Test
    public void getTotalCash() {
    }

    @Test
    public void getNumberofDamage() {
    }

    @Test
    public void testGetNumberofDamage() {
    }

    @Test
    public void getNumberofCapsule() {
    }

    @Test
    public void testGetNumberofCapsule() {
    }

    @Test
    public void getNumberofPackaging() {
    }

    @Test
    public void testGetNumberofPackaging() {
    }
}