package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class ProviderCashAccountServiceImplTest {
    @Autowired
    ProviderCashAccountServiceImpl providerCashAccountService;
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
    }
}