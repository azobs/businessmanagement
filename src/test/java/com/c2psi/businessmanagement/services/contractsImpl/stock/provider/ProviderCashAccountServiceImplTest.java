package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidValueException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
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
public class ProviderCashAccountServiceImplTest {
    @Autowired
    ProviderCashAccountServiceImpl providerCashAccountService;
    @Autowired
    ProviderCashOperationServiceImpl providerCashOperationService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;

    @Test
    public void validate_SaveProviderCashAccount(){
        ProviderCashAccountDto providerCashAccountDtoSaved = usedForTestForProvider.saveProviderCashAccount(0,
                providerCashAccountService);
        assertNotNull(providerCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(4552).compareTo(providerCashAccountDtoSaved.getPcaBalance())==0);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveProviderCashAccount_Invalid(){
        ProviderCashAccountDto providerCashAccountDtoSaved = usedForTestForProvider.saveProviderCashAccount_Invalid(0,
                providerCashAccountService);
        assertNotNull(providerCashAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(4552).compareTo(providerCashAccountDtoSaved.getPcaBalance())==0);
    }

    @Test
    public void validate_SaveCashOperation(){
        ProviderCashAccountDto providerCashAccountDtoSaved = usedForTestForProvider.saveProviderCashAccount(0,
                providerCashAccountService);
        assertNotNull(providerCashAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(4552).compareTo(providerCashAccountDtoSaved.getPcaBalance())==0);

        assertTrue(providerCashAccountService.saveCashOperation(providerCashAccountDtoSaved.getId(), BigDecimal.valueOf(4552),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCashAccountDto providerCashAccountDtoFound = providerCashAccountService.findProviderCashAccountById(
                providerCashAccountDtoSaved.getId()
        );
        assertNotNull(providerCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCashAccountDtoFound.getPcaBalance())==0);

        assertTrue(providerCashAccountService.saveCashOperation(providerCashAccountDtoSaved.getId(), BigDecimal.valueOf(45),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCashAccountDto providerCashAccountDtoFound1 = providerCashAccountService.findProviderCashAccountById(
                providerCashAccountDtoSaved.getId()
        );
        assertNotNull(providerCashAccountDtoFound1);
        assertTrue(BigDecimal.valueOf(45).compareTo(providerCashAccountDtoFound1.getPcaBalance())==0);

        //List<ProviderCashOperationDto> findAllProviderCashOperation(Long procopId)
        List<ProviderCashOperationDto> providerCashOperationDtoListAll = providerCashOperationService.
                findAllProviderCashOperation(providerCashAccountDtoFound.getId());
        assertEquals(2, providerCashOperationDtoListAll.size());

        //Page<ProviderCashOperationDto> findPageProviderCashOperation(Long procopId, int pagenum, int pagesize)
        Page<ProviderCashOperationDto> providerCashOperationDtoPageAll = providerCashOperationService.
                findPageProviderCashOperation(providerCashAccountDtoFound.getId(), 0, 2);
        assertEquals(1, providerCashOperationDtoPageAll.getTotalPages());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date startDate1 = new Date();
            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 3);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            //public List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Long procopId, Instant startDate,
            //  Instant endDate)
            List<ProviderCashOperationDto> providerCashOperationDtoList = providerCashOperationService
                    .findAllProviderCashOperationBetween(providerCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant());
            //System.err.println("providerCashOperationDtoList ="+providerCashOperationDtoList);
            assertEquals(2, providerCashOperationDtoList.size());

            /*
            public Page<ProviderCashOperationDto> findPageProviderCashOperationBetween(Long procopId, Instant startDate,
                                                                               Instant endDate, int pagenum,
                                                                               int pagesize)
             */
            Page<ProviderCashOperationDto> providerCashOperationDtoPage = providerCashOperationService
                    .findPageProviderCashOperationBetween(providerCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(2, providerCashOperationDtoPage.getTotalPages());

            /*
            public List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Long procopId, Instant startDate,
                                                                              Instant endDate, OperationType opType)
             */
            List<ProviderCashOperationDto> providerCashOperationDtoList1 = providerCashOperationService.
                    findAllProviderCashOperationBetween(providerCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), OperationType.Credit);
            assertEquals(1, providerCashOperationDtoList1.size());

            /*
            public Page<ProviderCashOperationDto> findPageProviderCashOperationBetween(Long procopId, Instant startDate,
                                                                               Instant endDate, OperationType opType,
                                                                               int pagenum, int pagesize)
             */
            Page<ProviderCashOperationDto> providerCashOperationDtoPage1 = providerCashOperationService.
                    findPageProviderCashOperationBetween(providerCashAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), OperationType.Credit, 0, 1);
            assertEquals(1, providerCashOperationDtoPage1.getTotalPages());



        }catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears "+e.getMessage());
            e.printStackTrace();
        }

    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveCashOperation_InvalidValue(){
        ProviderCashAccountDto providerCashAccountDtoSaved = usedForTestForProvider.saveProviderCashAccount(0,
                providerCashAccountService);
        assertNotNull(providerCashAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(4552).compareTo(providerCashAccountDtoSaved.getPcaBalance())==0);

        assertTrue(providerCashAccountService.saveCashOperation(providerCashAccountDtoSaved.getId(), BigDecimal.valueOf(0),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCashAccountDto providerCashAccountDtoFound = providerCashAccountService.findProviderCashAccountById(
                providerCashAccountDtoSaved.getId()
        );
        assertNotNull(providerCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCashAccountDtoFound.getPcaBalance())==0);
    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveCashOperation_OperationType(){
        ProviderCashAccountDto providerCashAccountDtoSaved = usedForTestForProvider.saveProviderCashAccount(0,
                providerCashAccountService);
        assertNotNull(providerCashAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(4552).compareTo(providerCashAccountDtoSaved.getPcaBalance())==0);

        assertTrue(providerCashAccountService.saveCashOperation(providerCashAccountDtoSaved.getId(), BigDecimal.valueOf(4552),
                OperationType.Change, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCashAccountDto providerCashAccountDtoFound = providerCashAccountService.findProviderCashAccountById(
                providerCashAccountDtoSaved.getId()
        );
        assertNotNull(providerCashAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCashAccountDtoFound.getPcaBalance())==0);
    }


}