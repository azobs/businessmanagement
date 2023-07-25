package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.exceptions.InvalidValueException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
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
public class ClientDamageAccountServiceImplTest {

    @Autowired
    ClientDamageAccountServiceImpl clientDamageAccountService;
    @Autowired
    ClientDamageOperationServiceImpl clientDamageOperationService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    UsedForTestForClient usedForTestForClient;
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
    ClientServiceImpl clientService;

    @Test
    public void validate_SaveClientDamageAccount(){
        ///////////////////////
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

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDamageAccountDto clientDamageAccountDtoSaved = usedForTestForClient.saveClientDamageAccount(5,
                clientDtoSaved, ArticleDtoSaved, clientDamageAccountService);
        assertNotNull(clientDamageAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(5).compareTo(clientDamageAccountDtoSaved.getCdaNumber())==0);
        ///////////////////

    }

    @Test
    public void validate_SaveDamageOperation(){
        ///////////////////////
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

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDamageAccountDto clientDamageAccountDtoSaved = usedForTestForClient.saveClientDamageAccount(5,
                clientDtoSaved, ArticleDtoSaved, clientDamageAccountService);
        assertNotNull(clientDamageAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(5).compareTo(clientDamageAccountDtoSaved.getCdaNumber())==0);
        ///////////////////
        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);
        ///////////////////////////////////
        assertTrue(clientDamageAccountService.saveDamageOperation(clientDamageAccountDtoSaved.getId(), BigDecimal.valueOf(2),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientDamageAccountDto clientDamageAccountDtoFound = clientDamageAccountService.findClientDamageAccountById(
                clientDamageAccountDtoSaved.getId()
        );
        assertNotNull(clientDamageAccountDtoFound);
        //System.err.println("clientDamageAccountDtoFound.getCdaNumber() == "+clientDamageAccountDtoFound.getCdaNumber());
        assertTrue(BigDecimal.valueOf(7).compareTo(clientDamageAccountDtoFound.getCdaNumber())==0);
        ////////////////////////////////
        assertTrue(clientDamageAccountService.saveDamageOperation(clientDamageAccountDtoSaved.getId(), BigDecimal.valueOf(1),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientDamageAccountDto clientDamageAccountDtoFound1 = clientDamageAccountService.findClientDamageAccountById(
                clientDamageAccountDtoSaved.getId()
        );
        assertNotNull(clientDamageAccountDtoFound1);
        assertTrue(BigDecimal.valueOf(8).compareTo(clientDamageAccountDtoFound1.getCdaNumber())==0);
        ////////////////////////////////////
        assertTrue(clientDamageAccountService.saveDamageOperation(clientDamageAccountDtoSaved.getId(), BigDecimal.valueOf(5),
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientDamageAccountDto clientDamageAccountDtoFound2 = clientDamageAccountService.findClientDamageAccountById(
                clientDamageAccountDtoSaved.getId()
        );
        assertNotNull(clientDamageAccountDtoFound2);
        assertTrue(BigDecimal.valueOf(3).compareTo(clientDamageAccountDtoFound2.getCdaNumber())==0);
        //////////////////////////////////
        List<ClientDamageOperationDto> clientDamageOperationDtoListAll = clientDamageOperationService.
                findAllClientDamageOperation(clientDamageAccountDtoFound.getId());
        assertEquals(3, clientDamageOperationDtoListAll.size());
        ////////////////////////////
        Page<ClientDamageOperationDto> clientDamageOperationDtoPageAll = clientDamageOperationService.
                findPageClientDamageOperation(clientDamageAccountDtoFound.getId(), 0, 2);
        assertEquals(2, clientDamageOperationDtoPageAll.getTotalPages());
        //////////////////////////////////
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

            List<ClientDamageOperationDto> clientDamageOperationDtoList = clientDamageOperationService
                    .findAllClientDamageOperationBetween(clientDamageAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertEquals(3, clientDamageOperationDtoList.size());

            //////////////////////////////////

            Page<ClientDamageOperationDto> clientDamageOperationDtoPage = clientDamageOperationService
                    .findPageClientDamageOperationBetween(clientDamageAccountDtoFound.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertEquals(2, clientDamageOperationDtoPage.getTotalPages());
            //////////////////////////////////////

            List<ClientDamageOperationDto> clientDamageOperationDtoList1 = clientDamageOperationService.
                    findAllClientDamageOperationofTypeBetween(clientDamageAccountDtoFound.getId(), OperationType.Withdrawal,
                            startDate.toInstant(), endDate.toInstant());
            assertEquals(1, clientDamageOperationDtoList1.size());
            //////////////////////////////////

            Page<ClientDamageOperationDto> clientDamageOperationDtoPage1 = clientDamageOperationService.
                    findPageClientDamageOperationofTypeBetween(clientDamageAccountDtoFound.getId(), OperationType.Credit, startDate.toInstant(),
                            endDate.toInstant(), 0, 1);
            assertEquals(2, clientDamageOperationDtoPage1.getTotalPages());

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveDamageOperation_InvalidValue(){
        ///////////////////////
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

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDamageAccountDto clientDamageAccountDtoSaved = usedForTestForClient.saveClientDamageAccount(5,
                clientDtoSaved, ArticleDtoSaved, clientDamageAccountService);
        assertNotNull(clientDamageAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(5).compareTo(clientDamageAccountDtoSaved.getCdaNumber())==0);
        ///////////////////
        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);
        ///////////////////////////
        assertTrue(clientDamageAccountService.saveDamageOperation(clientDamageAccountDtoSaved.getId(), BigDecimal.valueOf(0),
                OperationType.Credit, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientDamageAccountDto clientDamageAccountDtoFound = clientDamageAccountService.findClientDamageAccountById(
                clientDamageAccountDtoSaved.getId()
        );
        assertNotNull(clientDamageAccountDtoFound);
        //System.err.println("clientDamageAccountDtoFound.getCdaNumber() == "+clientDamageAccountDtoFound.getCdaNumber());
        assertTrue(BigDecimal.valueOf(5).compareTo(clientDamageAccountDtoFound.getCdaNumber())==0);
    }

    @Test(expected = InvalidValueException.class)
    public void validate_SaveDamageOperation_InvalidOperationType(){
        ///////////////////////
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

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDamageAccountDto clientDamageAccountDtoSaved = usedForTestForClient.saveClientDamageAccount(5,
                clientDtoSaved, ArticleDtoSaved, clientDamageAccountService);
        assertNotNull(clientDamageAccountDtoSaved);
        assertTrue(BigDecimal.valueOf(5).compareTo(clientDamageAccountDtoSaved.getCdaNumber())==0);
        ///////////////////
        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(3, userBMService);
        assertNotNull(userBMDtoSaved);
        ////////////////////////////
        assertTrue(clientDamageAccountService.saveDamageOperation(clientDamageAccountDtoSaved.getId(), BigDecimal.valueOf(10),
                OperationType.Change, userBMDtoSaved.getId(), "Reinitialisation", "description de l'operation"));

        ClientDamageAccountDto clientDamageAccountDtoFound = clientDamageAccountService.findClientDamageAccountById(
                clientDamageAccountDtoSaved.getId()
        );
        assertNotNull(clientDamageAccountDtoFound);
        System.err.println("clientDamageAccountDtoFound.getCdaNumber() == "+clientDamageAccountDtoFound.getCdaNumber());
        assertTrue(BigDecimal.valueOf(5).compareTo(clientDamageAccountDtoFound.getCdaNumber())==0);
    }


}