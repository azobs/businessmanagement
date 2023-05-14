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
public class DamageArrivalServiceImplTest {
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
    DamageArrivalServiceImpl damageArrivalService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    SupplyInvoiceDamageServiceImpl supplyInvoiceDamageService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;

    @Test
    public void validate_SaveDamageArrival(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoiceDamage (une facture Damage)
        SupplyInvoiceDamageDto supplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoiceDamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoiceDamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDamageArrival_Invalid(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoiceDamage (une facture Damage)
        SupplyInvoiceDamageDto supplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoiceDamageDtoSaved);

        SupplyInvoiceDamageDto supplyInvoiceDamageDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceDamage(32,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoiceDamageDtoSaved1);

        //On peut donc enregistrer un arrivage de l'article dans la facture Damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamArrival_NullSidam(0, articleDtoSaved,
                supplyInvoiceDamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

        /***
         * On va modifier le DamageArrival save sans supplyinvoiceDamage en lui ajoutant justement une facture
         */
        DamageArrivalDto damageArrivalDtoFound = damageArrivalService.findDamageArrivalById(damageArrivalDtoSaved.getId());
        assertNotNull(damageArrivalDtoFound);
        damageArrivalDtoFound.setDamaSidamDto(supplyInvoiceDamageDtoSaved1);
        DamageArrivalDto damageArrivalDtoUpdated = damageArrivalService.updateDamageArrival(damageArrivalDtoFound);
        assertNotNull(damageArrivalDtoUpdated);
        assertEquals(supplyInvoiceDamageDtoSaved1.getId(), damageArrivalDtoUpdated.getDamaSidamDto().getId());

        //On peut donc enregistrer un arrivage de l'article dans la facture Damage
        DamageArrivalDto damageArrivalDtoSaved1 = usedForTestForProduct.saveDamageArrival_Invalid(0, articleDtoSaved,
                supplyInvoiceDamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved1);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDamageArrival_NullArticle(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoiceDamage (une facture Damage)
        SupplyInvoiceDamageDto supplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoiceDamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture Damage
        articleDtoSaved.setId(null);
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoiceDamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

    }


    @Test(expected = InvalidEntityException.class)
    public void validate_SavedamageArrival_NotExistArticle(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        articleDtoSaved.setId(Long.valueOf(125478));
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SavedamageArrival_NotExistSupplyInvoicedamage(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        supplyInvoicedamageDtoSaved.setId(Long.valueOf(125478));
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SavedamageArrival_DifferentPos(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(45, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved, posDtoSaved1, articleService);
        assertNotNull(articleDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage

        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved1,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SavedamageArrival_Duplicata(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

        DamageArrivalDto damageArrivalDtoSaved1 = usedForTestForProduct.saveDamageArrival(4, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved1);

    }

    @Test
    public void validate_UpdatedamageArrival(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

        DamageArrivalDto damageArrivalDtoFound = damageArrivalService.findDamageArrivalById(damageArrivalDtoSaved.getId());
        assertNotNull(damageArrivalDtoFound);
        assertEquals(damageArrivalDtoSaved.getId(), damageArrivalDtoFound.getId());

        damageArrivalDtoFound.setDamaDeliveryquantity(BigDecimal.valueOf(20));
        damageArrivalDtoFound.setDamaQuantityartchanged(BigDecimal.valueOf(20));

        DamageArrivalDto damageArrivalDtoUpdate = damageArrivalService.updateDamageArrival(damageArrivalDtoFound);
        assertNotNull(damageArrivalDtoUpdate);
        assertEquals(damageArrivalDtoFound.getId(), damageArrivalDtoUpdate.getId());

    }

    @Test
    public void validate_UpdatedamageArrival_NewArticle(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(5, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

        DamageArrivalDto damageArrivalDtoFound = damageArrivalService.findDamageArrivalById(damageArrivalDtoSaved.getId());
        assertNotNull(damageArrivalDtoFound);
        assertEquals(damageArrivalDtoSaved.getId(), damageArrivalDtoFound.getId());

        damageArrivalDtoFound.setDamaDeliveryquantity(BigDecimal.valueOf(20));
        damageArrivalDtoFound.setDamaQuantityartchanged(BigDecimal.valueOf(20));
        damageArrivalDtoFound.setDamaArtDto(articleDtoSaved1);

        DamageArrivalDto damageArrivalDtoUpdate = damageArrivalService.updateDamageArrival(damageArrivalDtoFound);
        assertNotNull(damageArrivalDtoUpdate);
        assertEquals(damageArrivalDtoFound.getId(), damageArrivalDtoUpdate.getId());

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdatedamageArrival_Duplicata(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(5, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        ArticleDto articleDtoSaved2 = usedForTestForProduct.saveArticle(63, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved2);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

        DamageArrivalDto damageArrivalDtoSaved1 = usedForTestForProduct.saveDamageArrival(45, articleDtoSaved1,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved1);

        DamageArrivalDto damageArrivalDtoSaved2 = usedForTestForProduct.saveDamageArrival(478, articleDtoSaved2,
                null, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved2);

        if(damageArrivalDtoSaved.getDamaSidamDto() == null) {
            DamageArrivalDto damageArrivalDtoFound1 = damageArrivalService.findDamageArrivalofArticleinSidam(
                    damageArrivalDtoSaved2.getDamaArtDto().getId(), null);
            assertNotNull(damageArrivalDtoFound1);
            assertEquals(damageArrivalDtoSaved1.getId(), damageArrivalDtoFound1.getId());
        }

        if(damageArrivalDtoSaved.getDamaSidamDto() != null) {
            DamageArrivalDto damageArrivalDtoFound = damageArrivalService.findDamageArrivalofArticleinSidam(
                    damageArrivalDtoSaved.getDamaArtDto().getId(), damageArrivalDtoSaved.getDamaSidamDto().getId());
            assertNotNull(damageArrivalDtoFound);
            assertEquals(damageArrivalDtoSaved.getId(), damageArrivalDtoFound.getId());

            damageArrivalDtoFound.setDamaDeliveryquantity(BigDecimal.valueOf(20));
            damageArrivalDtoFound.setDamaQuantityartchanged(BigDecimal.valueOf(20));
            damageArrivalDtoFound.setDamaArtDto(articleDtoSaved1);

            DamageArrivalDto damageArrivalDtoUpdate = damageArrivalService.updateDamageArrival(damageArrivalDtoFound);
            assertNotNull(damageArrivalDtoUpdate);
            assertEquals(damageArrivalDtoFound.getId(), damageArrivalDtoUpdate.getId());
        }

    }

    @Test
    public void validate_FindAlldamageArrival(){
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
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        ArticleDto articleDtoSaved2 = usedForTestForProduct.saveArticle(3, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved2);

        ArticleDto articleDtoSaved3 = usedForTestForProduct.saveArticle(4, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved3);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        //On a donc le supplyinvoicedamage (une facture damage)
        SupplyInvoiceDamageDto supplyInvoicedamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamageDtoSaved);

        //On peut donc enregistrer un arrivage de l'article dans la facture damage
        DamageArrivalDto damageArrivalDtoSaved = usedForTestForProduct.saveDamageArrival(0, articleDtoSaved,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved);

        DamageArrivalDto damageArrivalDtoSaved1 = usedForTestForProduct.saveDamageArrival(1, articleDtoSaved1,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved1);

        DamageArrivalDto damageArrivalDtoSaved2 = usedForTestForProduct.saveDamageArrival(2, articleDtoSaved2,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved2);

        DamageArrivalDto damageArrivalDtoSaved3 = usedForTestForProduct.saveDamageArrival(3, articleDtoSaved3,
                supplyInvoicedamageDtoSaved, damageArrivalService);
        assertNotNull(damageArrivalDtoSaved3);
        assertTrue(damageArrivalService.deleteDamageArrivalById(damageArrivalDtoSaved3.getId()));

        /////////////////findAlldamageArrivalinSidamage
        List<DamageArrivalDto> damageArrivalDtoList = damageArrivalService.findAllDamageArrivalinSidam(
                supplyInvoicedamageDtoSaved.getId());
        assertNotNull(damageArrivalDtoList);
        assertEquals(3, damageArrivalDtoList.size());

        //////////////////////findPagedamageArrivalinSidamage
        Page<DamageArrivalDto> damageArrivalDtoPage = damageArrivalService.findPageDamageArrivalinSidam(
                supplyInvoicedamageDtoSaved.getId(), 0, 2);
        assertNotNull(damageArrivalDtoPage);
        assertEquals(2, damageArrivalDtoPage.getTotalPages());


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

            /////////////////findAlldamageArrivalinSidamageBetween
            List<DamageArrivalDto> damageArrivalDtoList2 = damageArrivalService.findAllDamageArrivalinSidamBetween(
                    supplyInvoicedamageDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(damageArrivalDtoList2);
            assertEquals(3, damageArrivalDtoList2.size());
            /////////////////findPagedamageArrivalinSidamageBetween
            Page<DamageArrivalDto> damageArrivalDtoPage2 = damageArrivalService.findPageDamageArrivalinSidamBetween(
                    supplyInvoicedamageDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(),0, 2);
            assertNotNull(damageArrivalDtoPage2);
            assertEquals(2, damageArrivalDtoPage2.getTotalPages());

        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }




}