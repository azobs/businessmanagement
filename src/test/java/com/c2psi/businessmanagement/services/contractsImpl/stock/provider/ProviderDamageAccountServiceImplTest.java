package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.dtos.stock.provider.*;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.BasePriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.*;
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
public class ProviderDamageAccountServiceImplTest {
    @Autowired
    ProviderDamageAccountServiceImpl providerDamageAccountService;
    @Autowired
    ProviderDamageOperationServiceImpl providerDamageOperationService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    FormatServiceImpl formatService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductFormatedServiceImpl productFormatedService;
    @Autowired
    UnitServiceImpl unitService;
    @Autowired
    BasePriceServiceImpl basePriceService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    ProviderServiceImpl providerService;

    @Test
    public void validate_SaveProviderDamageAccount(){
        //////////////////
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
        assertNotNull(formatDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, null, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

        ProductFormatedDto productFormatedDtoSaved = usedForTestForProduct.saveProductFormatedDto(0, prodDtoSaved,
                formatDtoSaved, productFormatedService);
        assertNotNull(productFormatedDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);

        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
        //////////////////
        ProviderDamageAccountDto providerDamageAccountDtoSaved = usedForTestForProvider.saveProviderDamageAccount(10,
                providerDtoSaved, articleDtoSaved, providerDamageAccountService);
        assertNotNull(providerDamageAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(10).compareTo(providerDamageAccountDtoSaved.getPdaNumber())==0);
    }

    @Test
    public void validate_SaveCashOperation(){
        //////////////////
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
        assertNotNull(formatDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, null, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

        ProductFormatedDto productFormatedDtoSaved = usedForTestForProduct.saveProductFormatedDto(0, prodDtoSaved,
                formatDtoSaved, productFormatedService);
        assertNotNull(productFormatedDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);

        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
        //////////////////
        ProviderDamageAccountDto providerDamageAccountDtoSaved = usedForTestForProvider.saveProviderDamageAccount(10,
                providerDtoSaved, articleDtoSaved, providerDamageAccountService);
        assertNotNull(providerDamageAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(10).compareTo(providerDamageAccountDtoSaved.getPdaNumber())==0);
        //////////////////////
        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);
        /////////////////////////////////////////
        assertTrue(providerDamageAccountService.saveDamageOperation(providerDamageAccountDtoSaved.getId(), BigDecimal.valueOf(10),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderDamageAccountDto providerDamageAccountDtoFound = providerDamageAccountService.findProviderDamageAccountById(
                providerDamageAccountDtoSaved.getId()
        );
        assertNotNull(providerDamageAccountDtoFound);
        System.err.println("providerDamageAccountDtoFound.getPcsaNumber() == "+providerDamageAccountDtoFound.getPdaNumber());
        assertTrue(BigDecimal.valueOf(20).compareTo(providerDamageAccountDtoFound.getPdaNumber())==0);
        //////////////////////////////
        assertTrue(providerDamageAccountService.saveDamageOperation(providerDamageAccountDtoSaved.getId(), BigDecimal.valueOf(35),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderDamageAccountDto providerDamageAccountDtoFound1 = providerDamageAccountService.findProviderDamageAccountById(
                providerDamageAccountDtoSaved.getId()
        );
        assertNotNull(providerDamageAccountDtoFound1);
        assertTrue(BigDecimal.valueOf(55).compareTo(providerDamageAccountDtoFound1.getPdaNumber())==0);
        ////////////////////////////////////
        assertTrue(providerDamageAccountService.saveDamageOperation(providerDamageAccountDtoSaved.getId(), BigDecimal.valueOf(15),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderDamageAccountDto providerDamageAccountDtoFound2 = providerDamageAccountService.findProviderDamageAccountById(
                providerDamageAccountDtoSaved.getId()
        );
        assertNotNull(providerDamageAccountDtoFound2);
        assertTrue(BigDecimal.valueOf(40).compareTo(providerDamageAccountDtoFound2.getPdaNumber())==0);
        ///////////////////////////////////////
        List<ProviderDamageOperationDto> providerDamageOperationDtoListAll = providerDamageOperationService.
                findAllProviderDamageOperation(providerDamageAccountDtoFound.getId());
        assertEquals(3, providerDamageOperationDtoListAll.size());
        ///////////////////////////////////////

        Page<ProviderDamageOperationDto> providerDamageOperationDtoPageAll = providerDamageOperationService.
                findPageProviderDamageOperation(providerDamageAccountDtoFound.getId(), 0, 2);
        assertEquals(2, providerDamageOperationDtoPageAll.getTotalPages());

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

            /////////////////////////

            List<ProviderDamageOperationDto> providerDamageOperationDtoList = providerDamageOperationService
                    .findAllProviderDamageOperationBetween(providerDamageAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant());
            System.err.println("providerDamageOperationDtoList ="+providerDamageOperationDtoList);
            assertEquals(3, providerDamageOperationDtoList.size());

            //////////////////////////////////
            Page<ProviderDamageOperationDto> providerDamageOperationDtoPage = providerDamageOperationService
                    .findPageProviderDamageOperationBetween(providerDamageAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertEquals(2, providerDamageOperationDtoPage.getTotalPages());

            ///////////////////////////////////////
            List<ProviderDamageOperationDto> providerDamageOperationDtoList1 = providerDamageOperationService.
                    findAllProviderDamageOperationBetween(providerDamageAccountDtoFound.getId(), OperationType.Withdrawal,
                            startDate.toInstant(), endDate.toInstant());
            assertEquals(1, providerDamageOperationDtoList1.size());

            //////////////////////////

            Page<ProviderDamageOperationDto> providerDamageOperationDtoPage1 = providerDamageOperationService.
                    findPageProviderDamageOperationBetween(providerDamageAccountDtoFound.getId(), OperationType.Credit, startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(2, providerDamageOperationDtoPage1.getTotalPages());

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears "+e.getMessage());
            e.printStackTrace();
        }

    }



}