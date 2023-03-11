package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class PosCashAccountServiceImplTest {
    @Autowired
    PosCashAccountServiceImpl posCashAccountService;
    @Autowired
    PosCashOperationServiceImpl posCashOperationService;

    @Autowired
    UserBMServiceImpl userBMService;

    public PosCashAccountDto savePosCashAccount(Double amount){
        PosCashAccountDto posCashAccountDtoToSaved = PosCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(amount.doubleValue()))
                .build();
        assertNull(posCashAccountDtoToSaved.getId());
        PosCashAccountDto posCashAccountDtoSaved = posCashAccountService.savePosCashAccount(posCashAccountDtoToSaved);
        return posCashAccountDtoSaved;
    }

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

    @Test
    public void savePosCashAccount_Valid(){
        assertNotNull(posCashAccountService);
        PosCashAccountDto posCashAccountDtoSaved = savePosCashAccount(Double.valueOf(0.00));
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());
    }

    @Test
    public void saveCashOperation_Valid(){
        assertNotNull(posCashAccountService);
        assertNotNull(posCashOperationService);
        assertNotNull(userBMService);
        PosCashAccountDto posCashAccountDtoSaved = savePosCashAccount(Double.valueOf(0.00));
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());
        //Ajoutons le userbm qui va effectuer l'operation
        UserBMDto userBMDtoSaved = saveUserBM();
        assertNotNull(userBMDtoSaved);

        Boolean b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), BigDecimal.valueOf(100.00),
                OperationType.Credit,userBMDtoSaved.getId(), "Initialisation du compte","description de l'operation");
        assertNotNull(b);
        assertTrue(b);

        BigDecimal solde = posCashAccountService.findPosCashAccountById(posCashAccountDtoSaved.getId()).getPcaBalance();
        assertEquals(0, solde.compareTo(BigDecimal.valueOf(100.00)));

        Boolean b1 = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), BigDecimal.valueOf(50.00),
                OperationType.Withdrawal,userBMDtoSaved.getId(), "debit du compte","description de l'operation");
        assertNotNull(b1);
        assertTrue(b1);

        solde = posCashAccountService.findPosCashAccountById(posCashAccountDtoSaved.getId()).getPcaBalance();
        assertEquals(0, solde.compareTo(BigDecimal.valueOf(50.00)));

    }

    @Test(expected = NullArgumentException.class)
    public void saveCashOperation_NullArgument(){
        assertNotNull(posCashAccountService);
        assertNotNull(posCashOperationService);
        assertNotNull(userBMService);
        PosCashAccountDto posCashAccountDtoSaved = savePosCashAccount(Double.valueOf(0.00));
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());
        //Ajoutons le userbm qui va effectuer l'operation
        UserBMDto userBMDtoSaved = saveUserBM();
        assertNotNull(userBMDtoSaved);

        Boolean b = posCashAccountService.saveCashOperation(null, BigDecimal.valueOf(100.00),
                OperationType.Credit,userBMDtoSaved.getId(), "Initialisation du compte","description de l'operation");
        //The line above is supposed to launch the exception expected
    }

    @Test(expected = EntityNotFoundException.class)
    public void saveCashOperation_UserBMNotFound(){
        assertNotNull(posCashAccountService);
        assertNotNull(posCashOperationService);
        assertNotNull(userBMService);
        PosCashAccountDto posCashAccountDtoSaved = savePosCashAccount(Double.valueOf(0.00));
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());
        //Ajoutons le userbm qui va effectuer l'operation
        UserBMDto userBMDtoSaved = saveUserBM();
        assertNotNull(userBMDtoSaved);

        Boolean b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), BigDecimal.valueOf(100.00),
                OperationType.Credit,userBMDtoSaved.getId()+20, "Initialisation du compte","description de l'operation");
        //The line above is supposed to launch the exception expected
    }

    @Test(expected = EntityNotFoundException.class)
    public void saveCashOperation_pcaNotFound(){
        assertNotNull(posCashAccountService);
        assertNotNull(posCashOperationService);
        assertNotNull(userBMService);
        PosCashAccountDto posCashAccountDtoSaved = savePosCashAccount(Double.valueOf(0.00));
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());
        //Ajoutons le userbm qui va effectuer l'operation
        UserBMDto userBMDtoSaved = saveUserBM();
        assertNotNull(userBMDtoSaved);

        Boolean b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId()+20, BigDecimal.valueOf(100.00),
                OperationType.Credit,userBMDtoSaved.getId(), "Initialisation du compte","description de l'operation");
        //The line above is supposed to launch the exception expected
    }

    @Test
    public void deletePosCashAccountById_Valid(){
        assertNotNull(posCashAccountService);
        PosCashAccountDto posCashAccountDtoSaved = savePosCashAccount(Double.valueOf(0.00));
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());
        assertTrue(posCashAccountService.deletePosCashAccountById(posCashAccountDtoSaved.getId()));
    }




}