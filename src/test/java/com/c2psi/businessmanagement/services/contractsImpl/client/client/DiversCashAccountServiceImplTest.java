package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
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
public class DiversCashAccountServiceImplTest {
    @Autowired
    DiversCashAccountServiceImpl diversCashAccountService;
    @Autowired
    DiversCashOperationServiceImpl diversCashOperationService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;

    @Test
    public void validate_SaveDiversCashAccount(){
        DiversCashAccountDto diversCashAccountDtoSaved = usedForTestForClient.saveDiversCashAccount(100,
                diversCashAccountService);
        assertNotNull(diversCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(100).compareTo(diversCashAccountDtoSaved.getDcaBalance())==0);
    }

    @Test
    public void validate_SaveCashOperation(){
        DiversCashAccountDto diversCashAccountDtoSaved = usedForTestForClient.saveDiversCashAccount(100,
                diversCashAccountService);
        assertNotNull(diversCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(100).compareTo(diversCashAccountDtoSaved.getDcaBalance())==0);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(diversCashAccountService.saveCashOperation(diversCashAccountDtoSaved.getId(), BigDecimal.valueOf(50),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        DiversCashAccountDto diversCashAccountDtoFound = diversCashAccountService.findDiversCashAccountById(
                diversCashAccountDtoSaved.getId()
        );
        assertNotNull(diversCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(50).compareTo(diversCashAccountDtoFound.getDcaBalance())==0);

        assertTrue(diversCashAccountService.saveCashOperation(diversCashAccountDtoSaved.getId(), BigDecimal.valueOf(45),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        DiversCashAccountDto diversCashAccountDtoFound1 = diversCashAccountService.findDiversCashAccountById(
                diversCashAccountDtoSaved.getId()
        );
        assertNotNull(diversCashAccountDtoFound1);
        assertTrue(BigDecimal.valueOf(95).compareTo(diversCashAccountDtoFound1.getDcaBalance())==0);

        //List<DiversCashOperationDto> findAllDiversCashOperation(Long dcopId)
        List<DiversCashOperationDto> diversCashOperationDtoListAll = diversCashOperationService.
                findAllDiversCashOperation(diversCashAccountDtoFound.getId());
        assertEquals(2, diversCashOperationDtoListAll.size());

        //Page<DiversCashOperationDto> findPageDiversCashOperation(Long dcopId, int pagenum, int pagesize)
        Page<DiversCashOperationDto> diversCashOperationDtoPageAll = diversCashOperationService.
                findPageDiversCashOperation(diversCashAccountDtoFound.getId(), 0, 2);
        assertEquals(1, diversCashOperationDtoPageAll.getTotalPages());

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

            //public List<DiversCashOperationDto> findAllDiversCashOperationBetween(Long dcopId, Instant startDate,
            //  Instant endDate)
            List<DiversCashOperationDto> diversCashOperationDtoList = diversCashOperationService
                    .findAllDiversCashOperationBetween(diversCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertEquals(2, diversCashOperationDtoList.size());

            /*
            public Page<DiversCashOperationDto> findPageDiversCashOperationBetween(Long dcopId, Instant startDate,
                                                                               Instant endDate, int pagenum,
                                                                               int pagesize)
             */
            Page<DiversCashOperationDto> diversCashOperationDtoPage = diversCashOperationService
                    .findPageDiversCashOperationBetween(diversCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(2, diversCashOperationDtoPage.getTotalPages());

            /*
            public List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccopId, Instant startDate,
                                                                              Instant endDate, OperationType opType)
             */
            List<DiversCashOperationDto> diversCashOperationDtoList1 = diversCashOperationService.
                    findAllDiversCashOperationofTypeBetween(diversCashAccountDtoFound.getId(), OperationType.Credit,
                            startDate.toInstant(),
                            endDate.toInstant());
            assertEquals(1, diversCashOperationDtoList1.size());

            /*
            public Page<DiversCashOperationDto> findPageDiversCashOperationBetween(Long dcopId, Instant startDate,
                                                                               Instant endDate, OperationType opType,
                                                                               int pagenum, int pagesize)
             */
            Page<DiversCashOperationDto> diversCashOperationDtoPage1 = diversCashOperationService.
                    findPageDiversCashOperationofTypeBetween(diversCashAccountDtoFound.getId(), OperationType.Credit,
                            startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(1, diversCashOperationDtoPage1.getTotalPages());

        }catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears "+e.getMessage());
            e.printStackTrace();
        }
    }


    @Test(expected = InvalidValueException.class)
    public void validate_SaveCashOperation_InvalidValue(){
        DiversCashAccountDto diversCashAccountDtoSaved = usedForTestForClient.saveDiversCashAccount(10,
                diversCashAccountService);
        assertNotNull(diversCashAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(10).compareTo(diversCashAccountDtoSaved.getDcaBalance())==0);

        assertTrue(diversCashAccountService.saveCashOperation(diversCashAccountDtoSaved.getId(), BigDecimal.valueOf(0),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        DiversCashAccountDto diversCashAccountDtoFound = diversCashAccountService.findDiversCashAccountById(
                diversCashAccountDtoSaved.getId()
        );
        assertNotNull(diversCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(diversCashAccountDtoFound.getDcaBalance())==0);
    }

}