package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.*;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.ProviderServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.UsedForTestForProvider;
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
public class PosPackagingServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    PosPackagingAccountServiceImpl posPackagingAccountService;
    @Autowired
    PosPackagingOperationServiceImpl posPackagingOperationService;
    @Autowired
    PackagingServiceImpl packagingService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;

    @Test
    public void validate_savePosPackagingAccount(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosPackagingAccount_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount_NotValid(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_savePosPackagingAccount_Duplication(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);
        PosPackagingAccountDto posPackagingAccountDtoSaved1 = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_FindPosPackagingAccountById(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoFound = posPackagingAccountService.findPosPackagingAccountById(
                posPackagingAccountDtoSaved.getId());
        assertNotNull(posPackagingAccountDtoFound);
        assertEquals(posPackagingAccountDtoSaved.getId(), posPackagingAccountDtoFound.getId());
    }

    @Test
    public void validate_FindPosPackagingAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoFound = posPackagingAccountService.findPosPackagingAccountInPos(
                packagingDtoSaved.getId(), posDtoSaved.getId());
        assertNotNull(posPackagingAccountDtoFound);
        assertEquals(posPackagingAccountDtoSaved.getId(), posPackagingAccountDtoFound.getId());
    }

    @Test
    public void validate_FindAllPackagingAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(1, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        PosPackagingAccountDto posPackagingAccountDtoSaved1 = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved1, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved1);

        PackagingDto packagingDtoSaved2 = usedForTestForProduct.savePackaging(2, providerDtoSaved1, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved2);

        PosPackagingAccountDto posPackagingAccountDtoSaved2 = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved2, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved2);

        List<PosPackagingAccountDto> posPackagingAccountDtoList = posPackagingAccountService.findAllPackagingAccountInPos(
                posDtoSaved.getId());
        assertNotNull(posPackagingAccountDtoList);
        assertEquals(3, posPackagingAccountDtoList.size());
    }

    @Test
    public void validate_FindPagePackagingAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(1, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        PosPackagingAccountDto posPackagingAccountDtoSaved1 = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved1, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved1);

        PackagingDto packagingDtoSaved2 = usedForTestForProduct.savePackaging(2, providerDtoSaved1, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved2);

        PosPackagingAccountDto posPackagingAccountDtoSaved2 = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved2, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved2);

        Page<PosPackagingAccountDto> posPackagingAccountDtoPage = posPackagingAccountService.findPagePackagingAccountInPos(
                posDtoSaved.getId(), 0, 2);
        assertNotNull(posPackagingAccountDtoPage);
        assertEquals(2, posPackagingAccountDtoPage.getTotalPages());
    }

    @Test
    public void validate_deletePosPackagingAccountById(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(1, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

        assertTrue(posPackagingAccountService.deletePosPackagingAccountById(posPackagingAccountDtoSaved.getId()));
    }

    @Test
    public void validate_savePackagingOperation(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(1, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PosPackagingAccountDto posPackagingAccountDtoSaved = usedForTestForAll.savePosPackagingAccount(0,
                packagingDtoSaved, posDtoSaved, posPackagingAccountService);
        assertNotNull(posPackagingAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(posPackagingAccountService.savePackagingOperation(posPackagingAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));

        PosPackagingAccountDto posPackagingAccountDtoFound = posPackagingAccountService.findPosPackagingAccountById(
                posPackagingAccountDtoSaved.getId());
        System.err.println("solde == "+posPackagingAccountDtoFound.getPpaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(posPackagingAccountDtoFound.getPpaNumber())==0);

        assertTrue(posPackagingAccountService.savePackagingOperation(posPackagingAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        PosPackagingAccountDto posPackagingAccountDtoFound1 = posPackagingAccountService.findPosPackagingAccountById(
                posPackagingAccountDtoSaved.getId());
        System.err.println("solde == "+posPackagingAccountDtoFound1.getPpaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(posPackagingAccountDtoFound1.getPpaNumber())==0);

        //On teste List<PosPackagingOperationDto> findAllPosPackagingOperation(Long ppamopId)
        List<PosPackagingOperationDto> posPackagingOperationDtoList = posPackagingOperationService.findAllPosPackagingOperation(
                posPackagingAccountDtoSaved.getId());
        assertNotNull(posPackagingOperationDtoList);
        assertEquals(2, posPackagingOperationDtoList.size());

        //On teste Page<PosPackagingOperationDto> findPagePosPackagingOperation(Long pcapsopId, int pagenum, int pagesize)
        Page<PosPackagingOperationDto> posPackagingOperationDtoPage = posPackagingOperationService.findPagePosPackagingOperation(
                posPackagingAccountDtoSaved.getId(), 0, 2);
        assertNotNull(posPackagingOperationDtoPage);
        assertEquals(1, posPackagingOperationDtoPage.getTotalPages());

        //On teste List<PosCapsuleOperationDto> findAllPosCapsuleOperationBetween(Long pcapsopId, Instant startDate,
        //                                                                          Instant endDate)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate1 = new Date();
            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            List<PosPackagingOperationDto> posPackagingOperationDtoListBetween = posPackagingOperationService.
                    findAllPosPackagingOperationBetween(posPackagingAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertNotNull(posPackagingOperationDtoListBetween);
            assertEquals(2, posPackagingOperationDtoListBetween.size());

            //Page<PosPackagingOperationDto> findPagePosPackagingOperationBetween(Long ppackopId, Instant startDate,
            //                                           Instant endDate, int pagenum, int pagesize)
            Page<PosPackagingOperationDto> posPackagingOperationDtoPageBetween = posPackagingOperationService.
                    findPagePosPackagingOperationBetween(posPackagingAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(posPackagingOperationDtoPageBetween);
            assertEquals(1, posPackagingOperationDtoPageBetween.getTotalPages());

            //List<PosPackagingOperationDto> findAllPosPackagingOperationBetween(Long ppackopId, OperationType op_type,
            //                                                        Instant startDate, Instant endDate)
            List<PosPackagingOperationDto> posPackagingOperationDtoListBetweenoftype = posPackagingOperationService.
                    findAllPosPackagingOperationBetween(posPackagingAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant());
            assertNotNull(posPackagingOperationDtoListBetweenoftype);
            assertEquals(1, posPackagingOperationDtoListBetweenoftype.size());

            //Page<PosPackagingOperationDto> findPagePosPackagingOperationBetween(Long ppackopId, OperationType op_type,
            //                                                                 Instant startDate, Instant endDate,
            //                                                                 int pagenum, int pagesize)
            Page<PosPackagingOperationDto> posPackagingOperationDtoPageBetweenoftype = posPackagingOperationService.
                    findPagePosPackagingOperationBetween(posPackagingAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(posPackagingOperationDtoPageBetweenoftype);
            assertEquals(1, posPackagingOperationDtoPageBetweenoftype.getTotalPages());

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }

}