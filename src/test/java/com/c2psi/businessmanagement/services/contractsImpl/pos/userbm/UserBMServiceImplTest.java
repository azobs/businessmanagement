package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;
import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
/****
 * Pour indiquer a spring la classe mere qui contient les classes a tester
 * Avec cette annotation spring va scanner tous les sous packages du package principale
 * du projet et va donc injecter tout le necessaire pour le test soit réalise
 * @SpringBootTest(classes = BusinessManagementApplication.class)
 *
 */
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class UserBMServiceImplTest {

    @Autowired
    UserBMServiceImpl userBMService;

    /***
     * @Test pour que la methode soit une classe de test valide et ainsi eviter l'exception
     * "NoRunnable Method"
     */
    @Test
    public void shouldSaveUserBMSuccessfully(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMSaved.getBmLogin(), userBMToSave.getBmLogin());
            assertEquals(userBMSaved.getBmCni(), userBMToSave.getBmCni());
            assertEquals(userBMSaved.getBmName(), userBMToSave.getBmName());
            assertEquals(userBMSaved.getBmSurname(), userBMToSave.getBmSurname());
            assertEquals(userBMSaved.getBmDob(), userBMToSave.getBmDob());
            assertEquals(userBMSaved.getBmAddressDto().getEmail(), userBMToSave.getBmAddressDto().getEmail());
            assertEquals(UserBMState.Activated, userBMSaved.getBmState());
            assertEquals(UserBMType.AdminBM, userBMSaved.getBmUsertype());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected= InvalidEntityException.class)
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMIsNull(){
        userBMService.saveUserBM(null);
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldThrowInvalidEntityExceptionIfPasswordIsDifferentThanRepassword(){
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
                    .bmPassword("password4444")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldThrowInvalidEntityExceptionIfUserBMAddressIsNull(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(null)
                    .bmCni("107235261")
                    .bmDob(sdf.parse("15-05-1941"))
                    .bmLogin("useradmin1")
                    .bmName("admin1")
                    .bmPassword("password1")
                    .bmRepassword("password1")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user1")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMNumtel1IsNull(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave2@gmail")
                            .localisation("")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235262")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin2")
                    .bmName("admin2")
                    .bmPassword("password2")
                    .bmRepassword("password2")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user2")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMNumtel1IsEmpty(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave3@gmail")
                            .localisation("")
                            .numtel1("")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235263")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin3")
                    .bmName("admin3")
                    .bmPassword("password3")
                    .bmRepassword("password3")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user3")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMNumtel2IsNull(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave4@gmail")
                            .localisation("")
                            .numtel1("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235264")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin4")
                    .bmName("admin4")
                    .bmPassword("password4")
                    .bmRepassword("password4")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user4")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMNumtel2IsEmpty(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave5@gmail")
                            .localisation("")
                            .numtel1("695093228")
                            .numtel2("")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235265")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin5")
                    .bmName("admin5")
                    .bmPassword("password5")
                    .bmRepassword("password5")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user5")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldThrowInvalidEntityExceptionIfUserBMEmailIsNotValid(){
        /***
         * Email valide doit avoir
         * Une chaine de caractere avant le signe @
         * Une seule fois le signe @
         * Et au moins un caractere ou une chaine de caractere apres le signe @
         * Dans tous les autres cas l'email est invalide
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave@gmail@")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235266")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin6")
                    .bmName("admin6")
                    .bmPassword("password6")
                    .bmRepassword("password6")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user6")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldSaveUserBMThrowDuplicateEntityExceptionIfCniIsUsed(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On enregistre un userbm avec un cninumber
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave7@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235267")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin7")
                    .bmName("admin7")
                    .bmPassword("password7")
                    .bmRepassword("password7")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user7")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);

            /*
            Puis on va essayer d'enregistrer un autre avec le meme cni number et se rassurer que
            l'exception s'est lance dans la methode saveUserBM
             */
            userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave7@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235267")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin77")
                    .bmName("admin77")
                    .bmPassword("password77")
                    .bmRepassword("password77")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user7")
                    .build();
            userBMSaved = userBMService.saveUserBM(userBMToSave);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldSaveUserBMThrowDuplicateEntityExceptionIfLoginIsUsed(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On enregistre un userbm avec un cninumber
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave8@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235268")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin8")
                    .bmName("admin8")
                    .bmPassword("password8")
                    .bmRepassword("password8")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user8")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);

            /*
            Puis on va essayer d'enregistrer un autre avec le meme cni number et se rassurer que
            l'exception s'est lance dans la methode saveUserBM
             */
            userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave8@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("1072352688")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin8")
                    .bmName("admin88")
                    .bmPassword("password8")
                    .bmRepassword("password8")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user8")
                    .build();
            userBMSaved = userBMService.saveUserBM(userBMToSave);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldSaveUserBMThrowDuplicateEntityExceptionIfFullnameIsUsed(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            //On enregistre un userbm avec un cninumber
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave9@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235269")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin9")
                    .bmName("admin9")
                    .bmPassword("password9")
                    .bmRepassword("password9")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user9")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);

            /*
            Puis on va essayer d'enregistrer un autre avec le meme cni number et se rassurer que
            l'exception s'est lance dans la methode saveUserBM
             */
            userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave99@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("1072352699")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin9")
                    .bmName("admin9")
                    .bmPassword("password9")
                    .bmRepassword("password9")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user9")
                    .build();
            userBMSaved = userBMService.saveUserBM(userBMToSave);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMTypeIsNotAdminOrAdminEntAndPosIsNull(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsave10@gmail")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("1072352100")
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin("useradmin10")
                    .bmName("admin10")
                    .bmPassword("password10")
                    .bmRepassword("password10")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.Employe)
                    .bmSurname("user6")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSaveUserBMThrowInvalidEntityExceptionIfUserBMTypeIsNotAdminOrAdminEntAndPosIsNotNull(){
        //Ici le code doit marcher. Il ne doit lancer aucune exception et le userbm doit etre enregistre
    }

    @Test(expected = NullArgumentException.class)
    public void shouldUpdateUserBMThrowNullEntityExceptionIfIdIsNull(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            /*
             On a enregistre un UserBM. On peut donc le modifier en settant son id a null pour voir si
             le InvalidEntityException est lance
            */
            userBMSaved.setId(null);
            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldUpdateUserBMThrowEntityNotFoundExceptionIfIdDoesNotIdentifyAUserBM(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            /*
             On a enregistre un UserBM. On va donc essayer de modifier un userBM dont l'id n'existe pas et
             verifier si l'exception EntityNotFoundException est lance
            */
            userBMSaved.setId(Long.parseLong(String.valueOf(14587)));
            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldUpdateUserBMThrowDuplicateEntityExceptionIfNewCniIsUsed(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            UserBMDto userBMToSave1 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsaveuserBMToSave1@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260")
                    .bmDob(sdf.parse("15-05-1945"))
                    .bmLogin("useradmin5")
                    .bmName("admin5")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave1);
            /**
             * On va essayer de modifier le userToSaved2 en lui donnant le meme Cni que le userToSaved1
             * et verifier qu'une exception de type DuplicateEntityException
             */
            userBMSaved2.setBmCni(userBMSaved.getBmCni());

            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldUpdateUserBMThrowDuplicateEntityExceptionIfNewEmailIsUsed(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            UserBMDto userBMToSave1 = UserBMDto.builder()
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
                    .bmCni("10723526054")
                    .bmDob(sdf.parse("15-05-1945"))
                    .bmLogin("useradmin5")
                    .bmName("admin5")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave1);
            /**
             * On va essayer de modifier le userToSaved2 en lui donnant le meme Email que le userToSaved1
             * et verifier qu'une exception de type DuplicateEntityException
             */
            userBMSaved2.setBmCni(userBMSaved.getBmCni());

            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldUpdateUserBMThrowDuplicateEntityExceptionIfNewLoginIsUsed(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsaveone@gmail.com")
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
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            UserBMDto userBMToSave1 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsavetwo@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("10723526054")
                    .bmDob(sdf.parse("15-05-1945"))
                    .bmLogin("useradmin")
                    .bmName("admin5")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave1);
            /**
             * On va essayer de modifier le userToSaved2 en lui donnant le meme Login que le userToSaved1
             * et verifier qu'une exception de type DuplicateEntityException
             */
            userBMSaved2.setBmCni(userBMSaved.getBmCni());

            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldUpdateUserBMThrowDuplicateEntityExceptionIfNewFullnameIsUsed(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsaveone@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("107235260")
                    .bmDob(sdf.parse("15-05-1945"))
                    .bmLogin("useradmin")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            UserBMDto userBMToSave1 = UserBMDto.builder()
                    .bmAddressDto(AddressDto.builder()
                            .email("testsavetwo@gmail.com")
                            .localisation("")
                            .numtel1("678470262")
                            .numtel2("695093228")
                            .numtel3("676170067")
                            .pays("Cameroun")
                            .quartier("Foret bar")
                            .ville("Douala")
                            .build())
                    .bmCni("10723526054")
                    .bmDob(sdf.parse("15-05-1945"))
                    .bmLogin("useradminsdsds")
                    .bmName("admin")
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved2 = userBMService.saveUserBM(userBMToSave1);
            /**
             * On va essayer de modifier le userToSaved2 en lui donnant le meme Fullname que le userToSaved1
             * et verifier qu'une exception de type DuplicateEntityException
             */
            userBMSaved2.setBmCni(userBMSaved.getBmCni());

            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUpdateUserBMSuccessfully(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);
            assertEquals(userBMToSave.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMToSave.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMToSave.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMToSave.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMToSave.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMToSave.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMSaved.getBmState(), UserBMState.Activated);
            assertEquals(userBMSaved.getBmUsertype(), UserBMType.AdminBM);

            /*
            Essai d'une mise a jour qui devrait réussir
             */
            userBMSaved.setBmCni("124857695");
            userBMSaved.getBmAddressDto().setNumtel1("658974521");
            userBMSaved.setBmName("chocolatier");

            UserBMDto userBMUpdated = userBMService.updateUserBM(userBMSaved);

            assertNotNull(userBMUpdated);
            assertEquals(userBMUpdated.getBmLogin(), userBMSaved.getBmLogin());
            assertEquals(userBMUpdated.getBmCni(), userBMSaved.getBmCni());
            assertEquals(userBMUpdated.getBmName(), userBMSaved.getBmName());
            assertEquals(userBMUpdated.getBmSurname(), userBMSaved.getBmSurname());
            assertEquals(userBMUpdated.getBmDob(), userBMSaved.getBmDob());
            assertEquals(userBMUpdated.getBmAddressDto().getEmail(), userBMSaved.getBmAddressDto().getEmail());
            assertEquals(userBMUpdated.getBmAddressDto().getNumtel1(), userBMSaved.getBmAddressDto().getNumtel1());
            assertEquals(userBMUpdated.getBmState(), UserBMState.Activated);
            assertEquals(userBMUpdated.getBmUsertype(), UserBMType.AdminBM);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldFindUserBMByLoginThrowEntityNotFoundException(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);

            /***
             * Maintenant on va faire la recherche d'un userbm quyi n'existe pas et verifier que
             * l'exception EntityNotFoundException est lance
             */
            UserBMDto userBMDtoFound = userBMService.findUserBMByLogin("login inexistant");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFindUserBMByLoginSuccessfully(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);

            /***
             * Maintenant on va faire la recherche d'un userbm quyi n'existe pas et verifier que
             * l'exception EntityNotFoundException est lance
             */
            UserBMDto userBMDtoFound = userBMService.findUserBMByLogin("useradmin");

            assertEquals(userBMSaved.getBmLogin(), userBMDtoFound.getBmLogin());
            assertEquals(userBMSaved.getBmDob(), userBMDtoFound.getBmDob());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldFindUserBMByEmailThrowEntityNotFoundException(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);

            /***
             * Maintenant on va faire la recherche d'un userbm quyi n'existe pas et verifier que
             * l'exception EntityNotFoundException est lance
             */
            UserBMDto userBMDtoFound = userBMService.findUserBMByEmail("emailinexistant@gmail.com");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFindUserBMByEmailSuccessfully(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);

            /***
             * Maintenant on va faire la recherche d'un userbm quyi n'existe pas et verifier que
             * l'exception EntityNotFoundException est lance
             */
            UserBMDto userBMDtoFound = userBMService.findUserBMByEmail("testsave@gmail.com");

            assertEquals(userBMSaved.getBmLogin(), userBMDtoFound.getBmLogin());
            assertEquals(userBMSaved.getBmDob(), userBMDtoFound.getBmDob());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldFindUserBMByCniThrowEntityNotFoundException(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);

            /***
             * Maintenant on va faire la recherche d'un userbm quyi n'existe pas et verifier que
             * l'exception EntityNotFoundException est lance
             */
            UserBMDto userBMDtoFound = userBMService.findUserBMByCni("1072352604444111111uu");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFindUserBMByCniSuccessfully(){
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
                    .bmUsertype(UserBMType.AdminBM)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            assertNotNull(userBMSaved);

            /***
             * Maintenant on va faire la recherche d'un userbm quyi n'existe pas et verifier que
             * l'exception EntityNotFoundException est lance
             */
            UserBMDto userBMDtoFound = userBMService.findUserBMByCni("107235260");

            assertEquals(userBMSaved.getBmLogin(), userBMDtoFound.getBmLogin());
            assertEquals(userBMSaved.getBmDob(), userBMDtoFound.getBmDob());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}