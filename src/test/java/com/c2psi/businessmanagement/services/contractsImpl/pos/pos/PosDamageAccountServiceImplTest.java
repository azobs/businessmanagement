package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidValueException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.BasePriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
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
public class PosDamageAccountServiceImplTest {

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
    PosDamageAccountServiceImpl posDamageAccountService;
    @Autowired
    PosDamageOperationServiceImpl posDamageOperationService;
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
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_savePosDamageAccount(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosDamageAccount_Invalid(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount_Invalid(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosDamageAccount_IdPosNull(){
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

        posDtoSaved.setId(null);

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_savePosDamageAccount_PosNotFound(){
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

        posDtoSaved.setId(Long.valueOf(12458963));

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosDamageAccount_IdArtNull(){
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

        ArticleDtoSaved.setId(null);

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_savePosDamageAccount_ArtNotFound(){
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

        ArticleDtoSaved.setId(Long.valueOf(4852136));

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosDamageAccount_PosInvalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

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


        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved1, posDamageAccountService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_savePosDamageAccount_Duplication(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);
        PosDamageAccountDto posDamageAccountDtoSaved1 = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_FindPosDamageAccountById(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        PosDamageAccountDto posDamageAccountDtoFound = posDamageAccountService.findPosDamageAccountById(
                posDamageAccountDtoSaved.getId());
        assertNotNull(posDamageAccountDtoFound);
        assertEquals(posDamageAccountDtoSaved.getId(), posDamageAccountDtoFound.getId());
    }

    @Test
    public void validate_FindPosDamageAccountofArticleInPos(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        PosDamageAccountDto posDamageAccountDtoFound = posDamageAccountService.findPosDamageAccountofArticleInPos(
                ArticleDtoSaved.getId(), posDtoSaved.getId());
        assertNotNull(posDamageAccountDtoFound);
        assertEquals(posDamageAccountDtoSaved.getId(), posDamageAccountDtoFound.getId());
    }

    @Test
    public void validate_FindAllDamageAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
        assertNotNull(formatDtoSaved);

        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved1, formatService);
        assertNotNull(formatDtoSaved1);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, null, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(1, null, posDtoSaved1, categoryService);
        assertNotNull(catDtoSaved1);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(1, posDtoSaved1, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);

        ProductFormatedDto productFormatedDtoSaved = usedForTestForProduct.saveProductFormatedDto(0, prodDtoSaved,
                formatDtoSaved, productFormatedService);
        assertNotNull(productFormatedDtoSaved);

        ProductFormatedDto productFormatedDtoSaved1 = usedForTestForProduct.saveProductFormatedDto(1, prodDtoSaved1,
                formatDtoSaved1, productFormatedService);
        assertNotNull(productFormatedDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);

        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved1);

        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        CurrencyDto currencyDtoSaved1 = usedForTestForAll.saveCurrency(15, currencyService);
        assertNotNull(currencyDtoSaved1);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved1, basePriceService);
        assertNotNull(basePriceDtoSaved1);

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        PosDamageAccountDto posDamageAccountDtoSaved1 = usedForTestForAll.savePosDamageAccount(1,
                ArticleDtoSaved1, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        PosDamageAccountDto posDamageAccountDtoSaved2 = usedForTestForAll.savePosDamageAccount(2,
                ArticleDtoSaved2, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved2);

        ArticleDto ArticleDtoSaved3 = usedForTestForProduct.saveArticle(3, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved1, posDtoSaved1, articleService);
        assertNotNull(ArticleDtoSaved3);

        PosDamageAccountDto posDamageAccountDtoSaved3 = usedForTestForAll.savePosDamageAccount(3,
                ArticleDtoSaved3, posDtoSaved1, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved3);

        List<PosDamageAccountDto> posDamageAccountDtoList = posDamageAccountService.findAllDamageAccountInPos(
                posDtoSaved.getId());
        assertNotNull(posDamageAccountDtoList);
        assertEquals(3, posDamageAccountDtoList.size());
    }

    @Test
    public void validate_FindPageDamageAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
        assertNotNull(formatDtoSaved);

        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved1, formatService);
        assertNotNull(formatDtoSaved1);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, null, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(1, null, posDtoSaved1, categoryService);
        assertNotNull(catDtoSaved1);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(1, posDtoSaved1, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);

        ProductFormatedDto productFormatedDtoSaved = usedForTestForProduct.saveProductFormatedDto(0, prodDtoSaved,
                formatDtoSaved, productFormatedService);
        assertNotNull(productFormatedDtoSaved);

        ProductFormatedDto productFormatedDtoSaved1 = usedForTestForProduct.saveProductFormatedDto(1, prodDtoSaved1,
                formatDtoSaved1, productFormatedService);
        assertNotNull(productFormatedDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);

        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved1);

        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        CurrencyDto currencyDtoSaved1 = usedForTestForAll.saveCurrency(15, currencyService);
        assertNotNull(currencyDtoSaved1);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved1, basePriceService);
        assertNotNull(basePriceDtoSaved1);

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        PosDamageAccountDto posDamageAccountDtoSaved1 = usedForTestForAll.savePosDamageAccount(1,
                ArticleDtoSaved1, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        PosDamageAccountDto posDamageAccountDtoSaved2 = usedForTestForAll.savePosDamageAccount(2,
                ArticleDtoSaved2, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved2);

        ArticleDto ArticleDtoSaved3 = usedForTestForProduct.saveArticle(3, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved1, posDtoSaved1, articleService);
        assertNotNull(ArticleDtoSaved3);

        PosDamageAccountDto posDamageAccountDtoSaved3 = usedForTestForAll.savePosDamageAccount(3,
                ArticleDtoSaved3, posDtoSaved1, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved3);

        Page<PosDamageAccountDto> posDamageAccountDtoPage = posDamageAccountService.findPageDamageAccountInPos(
                posDtoSaved.getId(), 0, 2);
        assertNotNull(posDamageAccountDtoPage);
        assertEquals(2, posDamageAccountDtoPage.getTotalPages());
    }

    @Test
    public void validate_deletePosDamageAccountById(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        assertTrue(posDamageAccountService.deletePosDamageAccountById(posDamageAccountDtoSaved.getId()));
    }

    @Test
    public void validate_saveDamageOperation(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(posDamageAccountService.saveDamageOperation(posDamageAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));
        PosDamageAccountDto posDamageAccountDtoFound = posDamageAccountService.findPosDamageAccountById(
                posDamageAccountDtoSaved.getId());
        System.err.println("solde == "+posDamageAccountDtoFound.getPdaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(posDamageAccountDtoFound.getPdaNumber())==0);

        assertTrue(posDamageAccountService.saveDamageOperation(posDamageAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        PosDamageAccountDto posDamageAccountDtoFound1 = posDamageAccountService.findPosDamageAccountById(
                posDamageAccountDtoSaved.getId());
        System.err.println("solde == "+posDamageAccountDtoFound1.getPdaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(posDamageAccountDtoFound1.getPdaNumber())==0);

        //On teste List<PosDamageOperationDto> findAllPosDamageOperation(Long pdamopId)
        List<PosDamageOperationDto> posDamageOperationDtoList = posDamageOperationService.findAllPosDamageOperation(
                posDamageAccountDtoSaved.getId());
        assertNotNull(posDamageOperationDtoList);
        assertEquals(2, posDamageOperationDtoList.size());

        //On teste Page<PosDamageOperationDto> findPagePosDamageOperation(Long pcapsopId, int pagenum, int pagesize)
        Page<PosDamageOperationDto> posDamageOperationDtoPage = posDamageOperationService.findPagePosDamageOperation(
                posDamageAccountDtoSaved.getId(), 0, 2);
        assertNotNull(posDamageOperationDtoPage);
        assertEquals(1, posDamageOperationDtoPage.getTotalPages());

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

            List<PosDamageOperationDto> posDamageOperationDtoListBetween = posDamageOperationService.
                    findAllPosDamageOperationBetween(posDamageAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertNotNull(posDamageOperationDtoListBetween);
            assertEquals(2, posDamageOperationDtoListBetween.size());

            //Page<PosDamageOperationDto> findPagePosDamageOperationBetween(Long pcapsopId, Instant startDate,
            //                                           Instant endDate, int pagenum, int pagesize)
            Page<PosDamageOperationDto> posDamageOperationDtoPageBetween = posDamageOperationService.
                    findPagePosDamageOperationBetween(posDamageAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(posDamageOperationDtoPageBetween);
            assertEquals(1, posDamageOperationDtoPageBetween.getTotalPages());

            //List<PosDamageOperationDto> findAllPosDamageOperationBetween(Long pcapsopId, OperationType op_type,
            //                                                        Instant startDate, Instant endDate)
            List<PosDamageOperationDto> posDamageOperationDtoListBetweenoftype = posDamageOperationService.
                    findAllPosDamageOperationofTypeBetween(posDamageAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant());
            assertNotNull(posDamageOperationDtoListBetweenoftype);
            assertEquals(1, posDamageOperationDtoListBetweenoftype.size());

            //Page<PosDamageOperationDto> findPagePosDamageOperationBetween(Long pcapsopId, OperationType op_type,
            //                                                                 Instant startDate, Instant endDate,
            //                                                                 int pagenum, int pagesize)
            Page<PosDamageOperationDto> posDamageOperationDtoPageBetweenoftype = posDamageOperationService.
                    findPagePosDamageOperationofTypeBetween(posDamageAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(posDamageOperationDtoPageBetweenoftype);
            assertEquals(1, posDamageOperationDtoPageBetweenoftype.getTotalPages());

        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }


    }

    @Test(expected = InvalidValueException.class)
    public void validate_saveDamageOperation_InvalidValue(){
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

        PosDamageAccountDto posDamageAccountDtoSaved = usedForTestForAll.savePosDamageAccount(0,
                ArticleDtoSaved, posDtoSaved, posDamageAccountService);
        assertNotNull(posDamageAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(posDamageAccountService.saveDamageOperation(posDamageAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));
        PosDamageAccountDto posDamageAccountDtoFound = posDamageAccountService.findPosDamageAccountById(
                posDamageAccountDtoSaved.getId());
        System.err.println("solde == "+posDamageAccountDtoFound.getPdaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(posDamageAccountDtoFound.getPdaNumber())==0);

        qteToSave = BigDecimal.valueOf(16);
        assertTrue(posDamageAccountService.saveDamageOperation(posDamageAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        PosDamageAccountDto posDamageAccountDtoFound1 = posDamageAccountService.findPosDamageAccountById(
                posDamageAccountDtoSaved.getId());
        System.err.println("solde == "+posDamageAccountDtoFound1.getPdaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(posDamageAccountDtoFound1.getPdaNumber())==0);
    }

}