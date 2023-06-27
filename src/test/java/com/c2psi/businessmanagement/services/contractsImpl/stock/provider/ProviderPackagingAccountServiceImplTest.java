package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.PackagingServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.UsedForTestForProduct;
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
public class ProviderPackagingAccountServiceImplTest {

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
    ProviderPackagingAccountServiceImpl providerPackagingAccountService;
    @Autowired
    ProviderPackagingOperationServiceImpl providerPackagingOperationService;
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
    public void validate_saveProviderPackagingAccount(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                 providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveProviderPackagingAccount_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount_Invalid(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_saveProviderPackagingAccount_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);
        ProviderPackagingAccountDto providerPackagingAccountDtoSaved1 = usedForTestForProvider.saveProviderPackagingAccount(2,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_FindProviderPackagingAccountById() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoFound = providerPackagingAccountService.findProviderPackagingAccountById(
                providerPackagingAccountDtoSaved.getId());
        assertNotNull(providerPackagingAccountDtoFound);
        assertEquals(providerPackagingAccountDtoSaved.getId(), providerPackagingAccountDtoFound.getId());
    }

    @Test
    public void validate_FindProviderPackagingAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoFound = providerPackagingAccountService.findProviderPackagingAccount(
                packagingDtoSaved.getId(), providerDtoSaved.getId());
        assertNotNull(providerPackagingAccountDtoFound);
        assertEquals(providerPackagingAccountDtoSaved.getId(), providerPackagingAccountDtoFound.getId());
    }

    @Test
    public void validate_FindAllPackagingAccountInPos() {
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

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved1 = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved1, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved1);

        PackagingDto packagingDtoSaved2 = usedForTestForProduct.savePackaging(2, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved2);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved2 = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved2, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved2);

        List<ProviderPackagingAccountDto> providerPackagingAccountDtoList = providerPackagingAccountService.findAllPackagingAccountofProvider(
                providerDtoSaved.getId());
        assertNotNull(providerPackagingAccountDtoList);
        assertEquals(3, providerPackagingAccountDtoList.size());
    }

    @Test
    public void validate_FindPagePackagingAccountInPos() {
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

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved1 = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved1, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved1);

        PackagingDto packagingDtoSaved2 = usedForTestForProduct.savePackaging(2, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved2);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved2 = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved2, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved2);

        Page<ProviderPackagingAccountDto> providerPackagingAccountDtoPage = providerPackagingAccountService.findPagePackagingAccountofProvider(
                providerDtoSaved.getId(), 0, 2);
        assertNotNull(providerPackagingAccountDtoPage);
        assertEquals(2, providerPackagingAccountDtoPage.getTotalPages());
    }

    @Test
    public void validate_DeleteProviderPackagingAccountById() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);

        ProviderPackagingAccountDto providerPackagingAccountDtoFound = providerPackagingAccountService.findProviderPackagingAccountById(
                providerPackagingAccountDtoSaved.getId());
        assertNotNull(providerPackagingAccountDtoFound);
        assertEquals(providerPackagingAccountDtoSaved.getId(), providerPackagingAccountDtoFound.getId());

        assertTrue(providerPackagingAccountService.deleteProviderPackagingAccountById(providerPackagingAccountDtoFound.getId()));
    }


    @Test
    public void validate_savePackagingOperationforProvider(){
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

        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = usedForTestForProvider.saveProviderPackagingAccount(0,
                providerDtoSaved, packagingDtoSaved, providerPackagingAccountService);
        assertNotNull(providerPackagingAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(providerPackagingAccountService.savePackagingOperationforProvider(providerPackagingAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));

        ProviderPackagingAccountDto providerPackagingAccountDtoFound = providerPackagingAccountService.findProviderPackagingAccountById(
                providerPackagingAccountDtoSaved.getId());
        System.err.println("solde == "+providerPackagingAccountDtoFound.getPpaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(providerPackagingAccountDtoFound.getPpaNumber())==0);

        assertTrue(providerPackagingAccountService.savePackagingOperationforProvider(providerPackagingAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        ProviderPackagingAccountDto providerPackagingAccountDtoFound1 = providerPackagingAccountService.findProviderPackagingAccountById(
                providerPackagingAccountDtoSaved.getId());
        System.err.println("solde == "+providerPackagingAccountDtoFound1.getPpaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(providerPackagingAccountDtoFound1.getPpaNumber())==0);

        //On teste List<ProviderPackagingOperationDto> findAllProviderPackagingOperation(Long propamopId)
        List<ProviderPackagingOperationDto> providerPackagingOperationDtoList = providerPackagingOperationService.findAllProviderPackagingOperation(
                providerPackagingAccountDtoSaved.getId());
        assertNotNull(providerPackagingOperationDtoList);
        assertEquals(2, providerPackagingOperationDtoList.size());

        //On teste Page<ProviderPackagingOperationDto> findPageProviderPackagingOperation(Long pproopId, int pagenum, int pagesize)
        Page<ProviderPackagingOperationDto> providerPackagingOperationDtoPage = providerPackagingOperationService.findPageProviderPackagingOperation(
                providerPackagingAccountDtoSaved.getId(), 0, 2);
        assertNotNull(providerPackagingOperationDtoPage);
        assertEquals(1, providerPackagingOperationDtoPage.getTotalPages());

        //On teste List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationBetween(Long pproopId, Instant startDate,
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

            List<ProviderPackagingOperationDto> providerPackagingOperationDtoListBetween = providerPackagingOperationService.
                    findAllProviderPackagingOperationBetween(providerPackagingAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertNotNull(providerPackagingOperationDtoListBetween);
            assertEquals(2, providerPackagingOperationDtoListBetween.size());

            //Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationBetween(Long propackopId, Instant startDate,
            //                                           Instant endDate, int pagenum, int pagesize)
            Page<ProviderPackagingOperationDto> providerPackagingOperationDtoPageBetween = providerPackagingOperationService.
                    findPageProviderPackagingOperationBetween(providerPackagingAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(providerPackagingOperationDtoPageBetween);
            assertEquals(1, providerPackagingOperationDtoPageBetween.getTotalPages());

            //List<ProviderPackagingOperationDto> findAllProviderPackagingOperationBetween(Long propackopId, OperationType op_type,
            //                                                        Instant startDate, Instant endDate)
            List<ProviderPackagingOperationDto> providerPackagingOperationDtoListBetweenoftype = providerPackagingOperationService.
                    findAllProviderPackagingOperationofTypeBetween(providerPackagingAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant());
            assertNotNull(providerPackagingOperationDtoListBetweenoftype);
            assertEquals(1, providerPackagingOperationDtoListBetweenoftype.size());

            //Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationBetween(Long propackopId, OperationType op_type,
            //                                                                 Instant startDate, Instant endDate,
            //                                                                 int pagenum, int pagesize)
            Page<ProviderPackagingOperationDto> providerPackagingOperationDtoPageBetweenoftype = providerPackagingOperationService.
                    findPageProviderPackagingOperationofTypeBetween(providerPackagingAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(providerPackagingOperationDtoPageBetweenoftype);
            assertEquals(1, providerPackagingOperationDtoPageBetweenoftype.getTotalPages());


        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }


}