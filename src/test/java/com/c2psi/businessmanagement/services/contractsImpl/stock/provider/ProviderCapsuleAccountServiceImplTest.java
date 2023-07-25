package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.InvalidValueException;
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
public class ProviderCapsuleAccountServiceImplTest {
    @Autowired
    ProviderCapsuleAccountServiceImpl providerCapsuleAccountService;
    @Autowired
    ProviderCapsuleOperationServiceImpl providerCapsuleOperationService;
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
    public void validate_SaveProviderCapsuleAccount(){

        ////
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

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
        ////


        ProviderCapsuleAccountDto providerCapsuleAccountDtoSaved = usedForTestForProvider.saveProviderCapsuleAccount(10,
                providerDtoSaved, ArticleDtoSaved, providerCapsuleAccountService);
        assertNotNull(providerCapsuleAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(10).compareTo(providerCapsuleAccountDtoSaved.getPcsaNumber())==0);
    }

    @Test
    public void validate_SaveCapsuleOperation(){
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

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderCapsuleAccountDto providerCapsuleAccountDtoSaved = usedForTestForProvider.saveProviderCapsuleAccount(0,
                providerDtoSaved, ArticleDtoSaved, providerCapsuleAccountService);
        assertNotNull(providerCapsuleAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoSaved.getPcsaNumber())==0);

        ///////
        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        ///////////////////////////
        assertTrue(providerCapsuleAccountService.saveCapsuleOperation(providerCapsuleAccountDtoSaved.getId(), BigDecimal.valueOf(10),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCapsuleAccountDto providerCapsuleAccountDtoFound = providerCapsuleAccountService.findProviderCapsuleAccountById(
                providerCapsuleAccountDtoSaved.getId()
        );
        assertNotNull(providerCapsuleAccountDtoFound);
        System.err.println("providerCapsuleAccountDtoFound.getPcsaNumber() == "+providerCapsuleAccountDtoFound.getPcsaNumber());
        assertTrue(BigDecimal.valueOf(10).compareTo(providerCapsuleAccountDtoFound.getPcsaNumber())==0);

        /////////////////
        assertTrue(providerCapsuleAccountService.saveCapsuleOperation(providerCapsuleAccountDtoSaved.getId(), BigDecimal.valueOf(35),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCapsuleAccountDto providerCapsuleAccountDtoFound1 = providerCapsuleAccountService.findProviderCapsuleAccountById(
                providerCapsuleAccountDtoSaved.getId()
        );
        assertNotNull(providerCapsuleAccountDtoFound1);
        assertTrue(BigDecimal.valueOf(45).compareTo(providerCapsuleAccountDtoFound1.getPcsaNumber())==0);

        //////////////////////////////
        assertTrue(providerCapsuleAccountService.saveCapsuleOperation(providerCapsuleAccountDtoSaved.getId(), BigDecimal.valueOf(20),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCapsuleAccountDto providerCapsuleAccountDtoFound2 = providerCapsuleAccountService.findProviderCapsuleAccountById(
                providerCapsuleAccountDtoSaved.getId()
        );
        assertNotNull(providerCapsuleAccountDtoFound2);
        assertTrue(BigDecimal.valueOf(25).compareTo(providerCapsuleAccountDtoFound2.getPcsaNumber())==0);

        ////////////////////////

        List<ProviderCapsuleOperationDto> providerCapsuleOperationDtoListAll = providerCapsuleOperationService.
                findAllProviderCapsuleOperation(providerCapsuleAccountDtoFound.getId());
        assertEquals(3, providerCapsuleOperationDtoListAll.size());

        //On va tester le updateProviderCapsuleOperation/////
        System.err.println("Test de updateProviderCapsuleOperation");
        ProviderCapsuleOperationDto providerCapsuleOperationDtoToUpdate = providerCapsuleOperationDtoListAll.get(0);
        BigDecimal currentQte = providerCapsuleOperationDtoToUpdate.getProcsoNumberinmvt();
        System.err.println("currentQte == "+currentQte);
        providerCapsuleOperationDtoToUpdate.setProcsoNumberinmvt(BigDecimal.valueOf(100));
        ProviderCapsuleOperationDto providerCapsuleOperationDtoUpdate = providerCapsuleOperationService.
                updateProviderCapsuleOperation(providerCapsuleOperationDtoToUpdate);
        assertNotNull(providerCapsuleOperationDtoUpdate);
        ProviderCapsuleOperationDto providerCapsuleOperationDtoFound = providerCapsuleOperationService.
                findProviderCapsuleOperationById(providerCapsuleOperationDtoUpdate.getId());
        System.err.println("providerCapsuleOperationDtoFound "+providerCapsuleOperationDtoFound.getProcsoNumberinmvt());
        assertTrue(BigDecimal.valueOf(100).compareTo(providerCapsuleOperationDtoUpdate.getProcsoNumberinmvt())==0);
        System.err.println("Fin du Test de updateProviderCapsuleOperation ");
        //////////////////////////////

        Page<ProviderCapsuleOperationDto> providerCapsuleOperationDtoPageAll = providerCapsuleOperationService.
                findPageProviderCapsuleOperation(providerCapsuleAccountDtoFound.getId(), 0, 2);
        assertEquals(2, providerCapsuleOperationDtoPageAll.getTotalPages());

        //////////////////////////////

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

            /////////////////////////

            List<ProviderCapsuleOperationDto> providerCapsuleOperationDtoList = providerCapsuleOperationService
                    .findAllProviderCapsuleOperationBetween(providerCapsuleAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant());
            System.err.println("providerCapsuleOperationDtoList ="+providerCapsuleOperationDtoList);
            assertEquals(3, providerCapsuleOperationDtoList.size());

            //////////////////////////////////


            Page<ProviderCapsuleOperationDto> providerCapsuleOperationDtoPage = providerCapsuleOperationService
                    .findPageProviderCapsuleOperationBetween(providerCapsuleAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertEquals(2, providerCapsuleOperationDtoPage.getTotalPages());

            ///////////////////////////////////////

            List<ProviderCapsuleOperationDto> providerCapsuleOperationDtoList1 = providerCapsuleOperationService.
                    findAllProviderCapsuleOperationofTypeBetween(providerCapsuleAccountDtoFound.getId(), OperationType.Withdrawal,
                            startDate.toInstant(), endDate.toInstant());
            assertEquals(1, providerCapsuleOperationDtoList1.size());

            //////////////////////////

            Page<ProviderCapsuleOperationDto> providerCapsuleOperationDtoPage1 = providerCapsuleOperationService.
                    findPageProviderCapsuleOperationofTypeBetween(providerCapsuleAccountDtoFound.getId(), OperationType.Credit, startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(2, providerCapsuleOperationDtoPage1.getTotalPages());

        }catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveCapsuleOperation_InvalidValue(){
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

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderCapsuleAccountDto providerCapsuleAccountDtoSaved = usedForTestForProvider.saveProviderCapsuleAccount(0,
                providerDtoSaved, ArticleDtoSaved, providerCapsuleAccountService);
        assertNotNull(providerCapsuleAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoSaved.getPcsaNumber())==0);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoSaved.getPcsaNumber())==0);

        assertTrue(providerCapsuleAccountService.saveCapsuleOperation(providerCapsuleAccountDtoSaved.getId(), BigDecimal.valueOf(0),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCapsuleAccountDto providerCapsuleAccountDtoFound = providerCapsuleAccountService.findProviderCapsuleAccountById(
                providerCapsuleAccountDtoSaved.getId()
        );
        assertNotNull(providerCapsuleAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoFound.getPcsaNumber())==0);
    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveCashOperation_OperationType(){
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

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderCapsuleAccountDto providerCapsuleAccountDtoSaved = usedForTestForProvider.saveProviderCapsuleAccount(0,
                providerDtoSaved, ArticleDtoSaved, providerCapsuleAccountService);
        assertNotNull(providerCapsuleAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoSaved.getPcsaNumber())==0);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);

        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoSaved.getPcsaNumber())==0);

        assertTrue(providerCapsuleAccountService.saveCapsuleOperation(providerCapsuleAccountDtoSaved.getId(), BigDecimal.valueOf(4552),
                OperationType.Change, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ProviderCapsuleAccountDto providerCapsuleAccountDtoFound = providerCapsuleAccountService.findProviderCapsuleAccountById(
                providerCapsuleAccountDtoSaved.getId()
        );
        assertNotNull(providerCapsuleAccountDtoFound);
        assertTrue(BigDecimal.valueOf(0).compareTo(providerCapsuleAccountDtoFound.getPcsaNumber())==0);
    }



}