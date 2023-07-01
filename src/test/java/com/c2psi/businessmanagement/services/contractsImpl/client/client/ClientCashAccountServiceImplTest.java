package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidValueException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class ClientCashAccountServiceImplTest {

    @Autowired
    ClientCashAccountServiceImpl clientCashAccountService;
    @Autowired
    ClientCashOperationServiceImpl clientCashOperationService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;

    @Test
    public void validate_SaveClientCashAccount(){
        ClientCashAccountDto clientCashAccountDtoSaved = usedForTestForClient.saveClientCashAccount(100,
                clientCashAccountService);
        assertNotNull(clientCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(100).compareTo(clientCashAccountDtoSaved.getCcaBalance())==0);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClientCashAccount_Invalid(){
        ClientCashAccountDto clientCashAccountDtoSaved = usedForTestForClient.saveClientCashAccount_Invalid(0,
                clientCashAccountService);
        assertNotNull(clientCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(100).compareTo(clientCashAccountDtoSaved.getCcaBalance())==0);
    }

    @Test
    public void validate_SaveCashOperation(){
        ClientCashAccountDto clientCashAccountDtoSaved = usedForTestForClient.saveClientCashAccount(100,
                clientCashAccountService);
        assertNotNull(clientCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(100).compareTo(clientCashAccountDtoSaved.getCcaBalance())==0);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(clientCashAccountService.saveCashOperation(clientCashAccountDtoSaved.getId(), BigDecimal.valueOf(50),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientCashAccountDto clientCashAccountDtoFound = clientCashAccountService.findClientCashAccountById(
                clientCashAccountDtoSaved.getId()
        );
        assertNotNull(clientCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(50).compareTo(clientCashAccountDtoFound.getCcaBalance())==0);

        assertTrue(clientCashAccountService.saveCashOperation(clientCashAccountDtoSaved.getId(), BigDecimal.valueOf(45),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientCashAccountDto clientCashAccountDtoFound1 = clientCashAccountService.findClientCashAccountById(
                clientCashAccountDtoSaved.getId()
        );
        assertNotNull(clientCashAccountDtoFound1);
        assertTrue(BigDecimal.valueOf(95).compareTo(clientCashAccountDtoFound1.getCcaBalance())==0);

        //List<ClientCashOperationDto> findAllClientCashOperation(Long ccopId)
        List<ClientCashOperationDto> clientCashOperationDtoListAll = clientCashOperationService.
                findAllClientCashOperation(clientCashAccountDtoFound.getId());
        assertEquals(2, clientCashOperationDtoListAll.size());

        //Page<ClientCashOperationDto> findPageClientCashOperation(Long ccopId, int pagenum, int pagesize)
        Page<ClientCashOperationDto> clientCashOperationDtoPageAll = clientCashOperationService.
                findPageClientCashOperation(clientCashAccountDtoFound.getId(), 0, 2);
        assertEquals(1, clientCashOperationDtoPageAll.getTotalPages());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date startDate1 = new Date();
            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 3);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            //public List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccopId, Instant startDate,
            //  Instant endDate)
            List<ClientCashOperationDto> clientCashOperationDtoList = clientCashOperationService
                    .findAllClientCashOperationBetween(clientCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertEquals(2, clientCashOperationDtoList.size());

            /*
            public Page<ClientCashOperationDto> findPageClientCashOperationBetween(Long ccopId, Instant startDate,
                                                                               Instant endDate, int pagenum,
                                                                               int pagesize)
             */
            Page<ClientCashOperationDto> clientCashOperationDtoPage = clientCashOperationService
                    .findPageClientCashOperationBetween(clientCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(2, clientCashOperationDtoPage.getTotalPages());

            /*
            public List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccopId, Instant startDate,
                                                                              Instant endDate, OperationType opType)
             */
            List<ClientCashOperationDto> clientCashOperationDtoList1 = clientCashOperationService.
                    findAllClientCashOperationofTypeBetween(clientCashAccountDtoFound.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant());
            assertEquals(1, clientCashOperationDtoList1.size());

            /*
            public Page<ClientCashOperationDto> findPageClientCashOperationBetween(Long ccopId, Instant startDate,
                                                                               Instant endDate, OperationType opType,
                                                                               int pagenum, int pagesize)
             */
            Page<ClientCashOperationDto> clientCashOperationDtoPage1 = clientCashOperationService.
                    findPageClientCashOperationofTypeBetween(clientCashAccountDtoFound.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant(), 0, 1);
            assertEquals(1, clientCashOperationDtoPage1.getTotalPages());

        }catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears "+e.getMessage());
            e.printStackTrace();
        }
    }


    @Test(expected = InvalidValueException.class)
    public void validate_SaveCashOperation_InvalidValue(){
        ClientCashAccountDto clientCashAccountDtoSaved = usedForTestForClient.saveClientCashAccount(10,
                clientCashAccountService);
        assertNotNull(clientCashAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(10).compareTo(clientCashAccountDtoSaved.getCcaBalance())==0);

        assertTrue(clientCashAccountService.saveCashOperation(clientCashAccountDtoSaved.getId(), BigDecimal.valueOf(0),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientCashAccountDto clientCashAccountDtoFound = clientCashAccountService.findClientCashAccountById(
                clientCashAccountDtoSaved.getId()
        );
        assertNotNull(clientCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(clientCashAccountDtoFound.getCcaBalance())==0);
    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveCashOperation_OperationType(){
        ClientCashAccountDto clientCashAccountDtoSaved = usedForTestForClient.saveClientCashAccount(0,
                clientCashAccountService);
        assertNotNull(clientCashAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(0).compareTo(clientCashAccountDtoSaved.getCcaBalance())==0);

        assertTrue(clientCashAccountService.saveCashOperation(clientCashAccountDtoSaved.getId(), BigDecimal.valueOf(0),
                OperationType.Change, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientCashAccountDto clientCashAccountDtoFound = clientCashAccountService.findClientCashAccountById(
                clientCashAccountDtoSaved.getId()
        );
        assertNotNull(clientCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(clientCashAccountDtoFound.getCcaBalance())==0);
    }



}