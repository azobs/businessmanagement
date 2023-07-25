package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
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
public class CapsuleArrivalServiceImplTest {
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
    CapsuleArrivalServiceImpl capsuleArrivalService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    SupplyInvoiceCapsuleServiceImpl supplyInvoiceCapsuleService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;


    @Test
    public void validate_SaveCapsuleArrival(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

    }


    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCapsuleArrival_Invalid(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCapsule(32,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved1);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsArrival_NullSiCaps(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

        /***
         * On va modifier le CapsuleArrival save sans supplyinvoiceCapsule en lui ajoutant justement une facture
         */
        CapsuleArrivalDto CapsuleArrivalDtoFound = capsuleArrivalService.findCapsuleArrivalById(CapsuleArrivalDtoSaved.getId());
        assertNotNull(CapsuleArrivalDtoFound);
        CapsuleArrivalDtoFound.setCapsaSicapsDto(supplyInvoiceCapsuleDtoSaved1);
        CapsuleArrivalDto CapsuleArrivalDtoUpdated = capsuleArrivalService.updateCapsuleArrival(CapsuleArrivalDtoFound);
        assertNotNull(CapsuleArrivalDtoUpdated);
        assertEquals(supplyInvoiceCapsuleDtoSaved1.getId(), CapsuleArrivalDtoUpdated.getCapsaSicapsDto().getId());

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved1 = usedForTestForProduct.saveCapsuleArrival_Invalid(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved1);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCapsuleArrival_NullArticle(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        ArticleDtoSaved.setId(null);
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCapsuleArrival_NotExistArticle(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        ArticleDtoSaved.setId(Long.valueOf(125478));
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCapsuleArrival_NotExistSupplyInvoiceCapsule(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        supplyInvoiceCapsuleDtoSaved.setId(Long.valueOf(125478));
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCapsuleArrival_DifferentPos(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule

        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved1,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveCapsuleArrival_Duplicata(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

        CapsuleArrivalDto CapsuleArrivalDtoSaved1 = usedForTestForProduct.saveCapsuleArrival(4, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved1);

    }


    @Test
    public void validate_UpdateCapsuleArrival(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

        CapsuleArrivalDto CapsuleArrivalDtoFound = capsuleArrivalService.findCapsuleArrivalById(CapsuleArrivalDtoSaved.getId());
        assertNotNull(CapsuleArrivalDtoFound);
        assertEquals(CapsuleArrivalDtoSaved.getId(), CapsuleArrivalDtoFound.getId());

        CapsuleArrivalDtoFound.setCapsaDeliveryquantity(BigDecimal.valueOf(20));
        CapsuleArrivalDtoFound.setCapsaQuantitycapschanged(BigDecimal.valueOf(20));

        CapsuleArrivalDto CapsuleArrivalDtoUpdate = capsuleArrivalService.updateCapsuleArrival(CapsuleArrivalDtoFound);
        assertNotNull(CapsuleArrivalDtoUpdate);
        assertEquals(CapsuleArrivalDtoFound.getId(), CapsuleArrivalDtoUpdate.getId());

    }

    @Test
    public void validate_UpdateCapsuleArrival_NewArticle(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

        CapsuleArrivalDto CapsuleArrivalDtoFound = capsuleArrivalService.findCapsuleArrivalById(CapsuleArrivalDtoSaved.getId());
        assertNotNull(CapsuleArrivalDtoFound);
        assertEquals(CapsuleArrivalDtoSaved.getId(), CapsuleArrivalDtoFound.getId());

        CapsuleArrivalDtoFound.setCapsaDeliveryquantity(BigDecimal.valueOf(20));
        CapsuleArrivalDtoFound.setCapsaQuantitycapschanged(BigDecimal.valueOf(20));
        CapsuleArrivalDtoFound.setCapsaArtDto(ArticleDtoSaved1);

        CapsuleArrivalDto CapsuleArrivalDtoUpdate = capsuleArrivalService.updateCapsuleArrival(CapsuleArrivalDtoFound);
        assertNotNull(CapsuleArrivalDtoUpdate);
        assertEquals(CapsuleArrivalDtoFound.getId(), CapsuleArrivalDtoUpdate.getId());

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateCapsuleArrival_Duplicata(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

        CapsuleArrivalDto CapsuleArrivalDtoSaved1 = usedForTestForProduct.saveCapsuleArrival(45, ArticleDtoSaved1,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved1);

        CapsuleArrivalDto CapsuleArrivalDtoSaved2 = usedForTestForProduct.saveCapsuleArrival(478, ArticleDtoSaved2,
                null, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved2);

        if(CapsuleArrivalDtoSaved.getCapsaSicapsDto() == null) {
            CapsuleArrivalDto CapsuleArrivalDtoFound1 = capsuleArrivalService.findCapsuleArrivalofArticleinSicaps(
                    CapsuleArrivalDtoSaved2.getCapsaArtDto().getId(), null);
            assertNotNull(CapsuleArrivalDtoFound1);
            assertEquals(CapsuleArrivalDtoSaved1.getId(), CapsuleArrivalDtoFound1.getId());
        }

        if(CapsuleArrivalDtoSaved.getCapsaSicapsDto() != null) {
            CapsuleArrivalDto CapsuleArrivalDtoFound = capsuleArrivalService.findCapsuleArrivalofArticleinSicaps(
                    CapsuleArrivalDtoSaved.getCapsaArtDto().getId(), CapsuleArrivalDtoSaved.getCapsaSicapsDto().getId());
            assertNotNull(CapsuleArrivalDtoFound);
            assertEquals(CapsuleArrivalDtoSaved.getId(), CapsuleArrivalDtoFound.getId());

            CapsuleArrivalDtoFound.setCapsaDeliveryquantity(BigDecimal.valueOf(20));
            CapsuleArrivalDtoFound.setCapsaQuantitycapschanged(BigDecimal.valueOf(20));
            CapsuleArrivalDtoFound.setCapsaArtDto(ArticleDtoSaved1);

            CapsuleArrivalDto CapsuleArrivalDtoUpdate = capsuleArrivalService.updateCapsuleArrival(CapsuleArrivalDtoFound);
            assertNotNull(CapsuleArrivalDtoUpdate);
            assertEquals(CapsuleArrivalDtoFound.getId(), CapsuleArrivalDtoUpdate.getId());
        }

    }

    @Test
    public void validate_FindAllCapsuleArrival(){
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

        //On a donc le supplyinvoiceCapsule (une facture Capsule)
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Capsule
        CapsuleArrivalDto CapsuleArrivalDtoSaved = usedForTestForProduct.saveCapsuleArrival(0, ArticleDtoSaved,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved);

        CapsuleArrivalDto CapsuleArrivalDtoSaved1 = usedForTestForProduct.saveCapsuleArrival(1, ArticleDtoSaved1,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved1);

        CapsuleArrivalDto CapsuleArrivalDtoSaved2 = usedForTestForProduct.saveCapsuleArrival(2, ArticleDtoSaved2,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved2);

        CapsuleArrivalDto CapsuleArrivalDtoSaved3 = usedForTestForProduct.saveCapsuleArrival(3, ArticleDtoSaved3,
                supplyInvoiceCapsuleDtoSaved, capsuleArrivalService);
        assertNotNull(CapsuleArrivalDtoSaved3);
        assertTrue(capsuleArrivalService.deleteCapsuleArrivalById(CapsuleArrivalDtoSaved3.getId()));

        /////////////////findAllCapsuleArrivalinSiCapsule
        List<CapsuleArrivalDto> CapsuleArrivalDtoList = capsuleArrivalService.findAllCapsuleArrivalinSicaps(
                supplyInvoiceCapsuleDtoSaved.getId());
        assertNotNull(CapsuleArrivalDtoList);
        assertEquals(3, CapsuleArrivalDtoList.size());

        //////////////////////findPageCapsuleArrivalinSiCapsule
        Page<CapsuleArrivalDto> CapsuleArrivalDtoPage = capsuleArrivalService.findPageCapsuleArrivalinSicaps(
                supplyInvoiceCapsuleDtoSaved.getId(), 0, 2);
        assertNotNull(CapsuleArrivalDtoPage);
        assertEquals(2, CapsuleArrivalDtoPage.getTotalPages());


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

            /////////////////findAllCapsuleArrivalinSiCapsuleBetween
            List<CapsuleArrivalDto> CapsuleArrivalDtoList2 = capsuleArrivalService.findAllCapsuleArrivalinSicapsBetween(
                    supplyInvoiceCapsuleDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(CapsuleArrivalDtoList2);
            assertEquals(3, CapsuleArrivalDtoList2.size());
            /////////////////findPageCapsuleArrivalinSiCapsuleBetween
            Page<CapsuleArrivalDto> CapsuleArrivalDtoPage2 = capsuleArrivalService.findPageCapsuleArrivalinSicapsBetween(
                    supplyInvoiceCapsuleDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(),0, 2);
            assertNotNull(CapsuleArrivalDtoPage2);
            assertEquals(2, CapsuleArrivalDtoPage2.getTotalPages());

        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }




}