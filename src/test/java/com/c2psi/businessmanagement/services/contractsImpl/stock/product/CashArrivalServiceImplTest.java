package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.BasePriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
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
public class CashArrivalServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    UnitServiceImpl unitService;
    @Autowired
    FormatServiceImpl formatService;
    @Autowired
    ProductFormatedServiceImpl productFormatedService;
    @Autowired
    BasePriceServiceImpl basePriceService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    CashArrivalServiceImpl cashArrivalService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    SupplyInvoiceCashServiceImpl supplyInvoiceCashService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;

    @Test
    public void validate_SaveCashArrival(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCashArrival_Invalid(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCash(32,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved1);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival_NullSicash(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

        /***
         * On va modifier le cashArrival save sans supplyinvoicecash en lui ajoutant justement une facture
         */
        CashArrivalDto cashArrivalDtoFound = cashArrivalService.findCashArrivalById(cashArrivalDtoSaved.getId());
        assertNotNull(cashArrivalDtoFound);
        cashArrivalDtoFound.setCashaSicashDto(supplyInvoiceCashDtoSaved1);
        CashArrivalDto cashArrivalDtoUpdated = cashArrivalService.updateCashArrival(cashArrivalDtoFound);
        assertNotNull(cashArrivalDtoUpdated);
        assertEquals(supplyInvoiceCashDtoSaved1.getId(), cashArrivalDtoUpdated.getCashaSicashDto().getId());

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved1 = usedForTestForProduct.saveCashArrival_Invalid(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved1);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCashArrival_NullArticle(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        ArticleDtoSaved.setId(null);
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCashArrival_NotExistArticle(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        ArticleDtoSaved.setId(Long.valueOf(125478));
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCashArrival_NotExistSupplyInvoiceCash(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        supplyInvoiceCashDtoSaved.setId(Long.valueOf(125478));
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCashArrival_DifferentPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(5, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
        assertNotNull(formatDtoSaved);

        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(95, posDtoSaved1, formatService);
        assertNotNull(formatDtoSaved1);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, null, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(85, null, posDtoSaved1, categoryService);
        assertNotNull(catDtoSaved1);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(78, posDtoSaved1, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);

        ProductFormatedDto productFormatedDtoSaved = usedForTestForProduct.saveProductFormatedDto(0, prodDtoSaved,
                formatDtoSaved, productFormatedService);
        assertNotNull(productFormatedDtoSaved);

        ProductFormatedDto productFormatedDtoSaved1 = usedForTestForProduct.saveProductFormatedDto(110, prodDtoSaved1,
                formatDtoSaved1, productFormatedService);
        assertNotNull(productFormatedDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);

        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(963, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved1);

        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        CurrencyDto currencyDtoSaved1 = usedForTestForAll.saveCurrency(865, currencyService);
        assertNotNull(currencyDtoSaved1);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(45, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved, posDtoSaved1, articleService);
        assertNotNull(ArticleDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash

        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved1,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveCashArrival_Duplicata(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

        CashArrivalDto cashArrivalDtoSaved1 = usedForTestForProduct.saveCashArrival(4, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved1);

    }

    @Test
    public void validate_UpdateCashArrival(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

        CashArrivalDto cashArrivalDtoFound = cashArrivalService.findCashArrivalById(cashArrivalDtoSaved.getId());
        assertNotNull(cashArrivalDtoFound);
        assertEquals(cashArrivalDtoSaved.getId(), cashArrivalDtoFound.getId());

        cashArrivalDtoFound.setCashaArrivaltype(CashArrivalType.Standard);
        cashArrivalDtoFound.setCashaDeliveryquantity(BigDecimal.valueOf(20));
        cashArrivalDtoFound.setCashaUnitprice(BigDecimal.valueOf(7100));

        CashArrivalDto cashArrivalDtoUpdate = cashArrivalService.updateCashArrival(cashArrivalDtoFound);
        assertNotNull(cashArrivalDtoUpdate);
        assertEquals(cashArrivalDtoFound.getId(), cashArrivalDtoUpdate.getId());

    }

    @Test
    public void validate_UpdateCashArrival_NewArticle(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(5, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

        CashArrivalDto cashArrivalDtoFound = cashArrivalService.findCashArrivalById(cashArrivalDtoSaved.getId());
        assertNotNull(cashArrivalDtoFound);
        assertEquals(cashArrivalDtoSaved.getId(), cashArrivalDtoFound.getId());

        cashArrivalDtoFound.setCashaArrivaltype(CashArrivalType.Standard);
        cashArrivalDtoFound.setCashaDeliveryquantity(BigDecimal.valueOf(20));
        cashArrivalDtoFound.setCashaUnitprice(BigDecimal.valueOf(7100));
        cashArrivalDtoFound.setCashaArtDto(ArticleDtoSaved1);

        CashArrivalDto cashArrivalDtoUpdate = cashArrivalService.updateCashArrival(cashArrivalDtoFound);
        assertNotNull(cashArrivalDtoUpdate);
        assertEquals(cashArrivalDtoFound.getId(), cashArrivalDtoUpdate.getId());

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateCashArrival_Duplicata(){
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(5, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(63, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

        CashArrivalDto cashArrivalDtoSaved1 = usedForTestForProduct.saveCashArrival(45, ArticleDtoSaved1,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved1);

        CashArrivalDto cashArrivalDtoSaved2 = usedForTestForProduct.saveCashArrival(478, ArticleDtoSaved2,
                null, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved2);

        if(cashArrivalDtoSaved.getCashaSicashDto() == null) {
            CashArrivalDto cashArrivalDtoFound1 = cashArrivalService.findCashArrivalofArticleinSicash(
                    cashArrivalDtoSaved2.getCashaArtDto().getId(), null);
            assertNotNull(cashArrivalDtoFound1);
            assertEquals(cashArrivalDtoSaved1.getId(), cashArrivalDtoFound1.getId());
        }

        if(cashArrivalDtoSaved.getCashaSicashDto() != null) {
            CashArrivalDto cashArrivalDtoFound = cashArrivalService.findCashArrivalofArticleinSicash(
                    cashArrivalDtoSaved.getCashaArtDto().getId(), cashArrivalDtoSaved.getCashaSicashDto().getId());
            assertNotNull(cashArrivalDtoFound);
            assertEquals(cashArrivalDtoSaved.getId(), cashArrivalDtoFound.getId());

            cashArrivalDtoFound.setCashaArrivaltype(CashArrivalType.Standard);
            cashArrivalDtoFound.setCashaDeliveryquantity(BigDecimal.valueOf(20));
            cashArrivalDtoFound.setCashaUnitprice(BigDecimal.valueOf(7100));
            cashArrivalDtoFound.setCashaArtDto(ArticleDtoSaved1);

            CashArrivalDto cashArrivalDtoUpdate = cashArrivalService.updateCashArrival(cashArrivalDtoFound);
            assertNotNull(cashArrivalDtoUpdate);
            assertEquals(cashArrivalDtoFound.getId(), cashArrivalDtoUpdate.getId());
        }

    }

    @Test
    public void validate_FindAllCashArrival(){
        Date startDate1 = new Date();
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

        //on a donc l'article
        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(3, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        ArticleDto ArticleDtoSaved3 = usedForTestForProduct.saveArticle(4, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved3);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicecash (une facture cash)
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture cash
        CashArrivalDto cashArrivalDtoSaved = usedForTestForProduct.saveCashArrival(0, ArticleDtoSaved,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved);

        CashArrivalDto cashArrivalDtoSaved1 = usedForTestForProduct.saveCashArrival(1, ArticleDtoSaved1,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved1);

        CashArrivalDto cashArrivalDtoSaved2 = usedForTestForProduct.saveCashArrival(2, ArticleDtoSaved2,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved2);

        CashArrivalDto cashArrivalDtoSaved3 = usedForTestForProduct.saveCashArrival(3, ArticleDtoSaved3,
                supplyInvoiceCashDtoSaved, cashArrivalService);
        assertNotNull(cashArrivalDtoSaved3);
        assertTrue(cashArrivalService.deleteCashArrivalById(cashArrivalDtoSaved3.getId()));

        CashArrivalDto cashArrivalDtoFound2 = cashArrivalService.findCashArrivalById(cashArrivalDtoSaved2.getId());
        cashArrivalDtoFound2.setCashaArrivaltype(CashArrivalType.Standard);
        CashArrivalDto cashArrivalDtoUpdate2 = cashArrivalService.updateCashArrival(cashArrivalDtoFound2);
        assertNotNull(cashArrivalDtoUpdate2);

        /////////////////findAllCashArrivalinSicash
        List<CashArrivalDto> cashArrivalDtoList = cashArrivalService.findAllCashArrivalinSicash(
                supplyInvoiceCashDtoSaved.getId());
        assertNotNull(cashArrivalDtoList);
        assertEquals(3, cashArrivalDtoList.size());

        //////////////////////findPageCashArrivalinSicash
        Page<CashArrivalDto> cashArrivalDtoPage = cashArrivalService.findPageCashArrivalinSicash(
                supplyInvoiceCashDtoSaved.getId(), 0, 2);
        assertNotNull(cashArrivalDtoPage);
        assertEquals(2, cashArrivalDtoPage.getTotalPages());

        /////////////////findAllCashArrivalofCashArrivalTypeinSicash
        List<CashArrivalDto> cashArrivalDtoList1 = cashArrivalService.findAllCashArrivalofCashArrivalTypeinSicash(
                CashArrivalType.Standard, supplyInvoiceCashDtoSaved.getId());
        assertNotNull(cashArrivalDtoList1);
        assertEquals(1, cashArrivalDtoList1.size());

        /////////////////findPageCashArrivalofCashArrivalTypeinSicash
        Page<CashArrivalDto> cashArrivalDtoPage1 = cashArrivalService.findPageCashArrivalofCashArrivalTypeinSicash(
                CashArrivalType.Standard, supplyInvoiceCashDtoSaved.getId(), 0, 2);
        assertNotNull(cashArrivalDtoPage1);
        assertEquals(1, cashArrivalDtoPage1.getTotalPages());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            /////////////////findAllCashArrivalinSicashBetween
            List<CashArrivalDto> cashArrivalDtoList2 = cashArrivalService.findAllCashArrivalinSicashBetween(
                    supplyInvoiceCashDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(cashArrivalDtoList2);
            assertEquals(3, cashArrivalDtoList2.size());
            /////////////////findPageCashArrivalinSicashBetween
            Page<CashArrivalDto> cashArrivalDtoPage2 = cashArrivalService.findPageCashArrivalinSicashBetween(
                    supplyInvoiceCashDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(),0, 2);
            assertNotNull(cashArrivalDtoPage2);
            assertEquals(2, cashArrivalDtoPage2.getTotalPages());

            /////////////////findAllCashArrivalofCashArrivalTypeinSicashBetween
            List<CashArrivalDto> cashArrivalDtoList3 = cashArrivalService.findAllCashArrivalofCashArrivalTypeinSicashBetween(
                    CashArrivalType.Standard, supplyInvoiceCashDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(cashArrivalDtoList3);
            assertEquals(1, cashArrivalDtoList3.size());
            /////////////////findPageCashArrivalofCashArrivalTypeinSicashBetween
            Page<CashArrivalDto> cashArrivalDtoPage3 = cashArrivalService.findPageCashArrivalofCashArrivalTypeinSicashBetween(
                    CashArrivalType.Standard, supplyInvoiceCashDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(),
                    0, 2);
            assertNotNull(cashArrivalDtoPage3);
            assertEquals(1, cashArrivalDtoPage3.getTotalPages());

        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }





}