package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
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
public class PosCapsuleAccountServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    PosCashAccountServiceImpl posCashAccountService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    PosCapsuleAccountServiceImpl posCapsuleAccountService;
    @Autowired
    PosCapsuleOperationServiceImpl posCapsuleOperationService;
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
    public void validate_savePosCapsuleAccount(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosCapsuleAccount_Invalid(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount_Invalid(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosCapsuleAccount_IdPosNull(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_savePosCapsuleAccount_PosNotFound(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosCapsuleAccount_IdArtNull(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_savePosCapsuleAccount_ArtNotFound(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_savePosCapsuleAccount_PosInvalid(){
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


        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved1, posCapsuleAccountService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_savePosCapsuleAccount_Duplication(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);
        PosCapsuleAccountDto posCapsuleAccountDtoSaved1 = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_FindPosCapsuleAccountById(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        PosCapsuleAccountDto posCapsuleAccountDtoFound = posCapsuleAccountService.findPosCapsuleAccountById(
                posCapsuleAccountDtoSaved.getId());
        assertNotNull(posCapsuleAccountDtoFound);
        assertEquals(posCapsuleAccountDtoSaved.getId(), posCapsuleAccountDtoFound.getId());
    }

    @Test
    public void validate_FindPosCapsuleAccountofArticleInPos(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        PosCapsuleAccountDto posCapsuleAccountDtoFound = posCapsuleAccountService.findPosCapsuleAccountofArticleInPos(
                ArticleDtoSaved.getId(), posDtoSaved.getId());
        assertNotNull(posCapsuleAccountDtoFound);
        assertEquals(posCapsuleAccountDtoSaved.getId(), posCapsuleAccountDtoFound.getId());
    }

    @Test
    public void validate_FindAllCapsuleAccountInPos(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        PosCapsuleAccountDto posCapsuleAccountDtoSaved1 = usedForTestForAll.savePosCapsuleAccount(1,
                ArticleDtoSaved1, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        PosCapsuleAccountDto posCapsuleAccountDtoSaved2 = usedForTestForAll.savePosCapsuleAccount(2,
                ArticleDtoSaved2, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved2);

        ArticleDto ArticleDtoSaved3 = usedForTestForProduct.saveArticle(3, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved1, posDtoSaved1, articleService);
        assertNotNull(ArticleDtoSaved3);

        PosCapsuleAccountDto posCapsuleAccountDtoSaved3 = usedForTestForAll.savePosCapsuleAccount(3,
                ArticleDtoSaved3, posDtoSaved1, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved3);

        List<PosCapsuleAccountDto> posCapsuleAccountDtoList = posCapsuleAccountService.findAllCapsuleAccountInPos(
                posDtoSaved.getId());
        assertNotNull(posCapsuleAccountDtoList);
        assertEquals(3, posCapsuleAccountDtoList.size());
    }

    @Test
    public void validate_FindPageCapsuleAccountInPos(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        PosCapsuleAccountDto posCapsuleAccountDtoSaved1 = usedForTestForAll.savePosCapsuleAccount(1,
                ArticleDtoSaved1, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        PosCapsuleAccountDto posCapsuleAccountDtoSaved2 = usedForTestForAll.savePosCapsuleAccount(2,
                ArticleDtoSaved2, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved2);

        ArticleDto ArticleDtoSaved3 = usedForTestForProduct.saveArticle(3, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved1, posDtoSaved1, articleService);
        assertNotNull(ArticleDtoSaved3);

        PosCapsuleAccountDto posCapsuleAccountDtoSaved3 = usedForTestForAll.savePosCapsuleAccount(3,
                ArticleDtoSaved3, posDtoSaved1, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved3);

        Page<PosCapsuleAccountDto> posCapsuleAccountDtoPage = posCapsuleAccountService.findPageCapsuleAccountInPos(
                posDtoSaved.getId(), 0, 2);
        assertNotNull(posCapsuleAccountDtoPage);
        assertEquals(2, posCapsuleAccountDtoPage.getTotalPages());
    }

    @Test
    public void validate_deletePosCapsuleAccountById(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        assertTrue(posCapsuleAccountService.deletePosCapsuleAccountById(posCapsuleAccountDtoSaved.getId()));
    }

    @Test
    public void validate_saveCapsuleOperation(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(posCapsuleAccountService.saveCapsuleOperation(posCapsuleAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));
        PosCapsuleAccountDto posCapsuleAccountDtoFound = posCapsuleAccountService.findPosCapsuleAccountById(
                posCapsuleAccountDtoSaved.getId());
        System.err.println("solde == "+posCapsuleAccountDtoFound.getPcsaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(posCapsuleAccountDtoFound.getPcsaNumber())==0);

        assertTrue(posCapsuleAccountService.saveCapsuleOperation(posCapsuleAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        PosCapsuleAccountDto posCapsuleAccountDtoFound1 = posCapsuleAccountService.findPosCapsuleAccountById(
                posCapsuleAccountDtoSaved.getId());
        System.err.println("solde == "+posCapsuleAccountDtoFound1.getPcsaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(posCapsuleAccountDtoFound1.getPcsaNumber())==0);

        BigDecimal newQteToSave = qteToSave.add(BigDecimal.valueOf(5));
        System.err.println("qteToSave == "+qteToSave);

        assertTrue(posCapsuleAccountService.saveCapsuleOperation(posCapsuleAccountDtoSaved.getId(), newQteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));
        PosCapsuleAccountDto posCapsuleAccountDtoFound2 = posCapsuleAccountService.findPosCapsuleAccountById(
                posCapsuleAccountDtoSaved.getId());
        System.err.println("solde == "+posCapsuleAccountDtoFound2.getPcsaNumber());
        assertTrue(BigDecimal.valueOf(20).compareTo(posCapsuleAccountDtoFound2.getPcsaNumber())==0);

        //On teste List<PosCapsuleOperationDto> findAllPosCapsuleOperation(Long pcapsopId)
        List<PosCapsuleOperationDto> posCapsuleOperationDtoList = posCapsuleOperationService.findAllPosCapsuleOperation(
                posCapsuleAccountDtoSaved.getId());
        assertNotNull(posCapsuleOperationDtoList);
        assertEquals(3, posCapsuleOperationDtoList.size());

        //On teste Page<PosCapsuleOperationDto> findPagePosCapsuleOperation(Long pcapsopId, int pagenum, int pagesize)
        Page<PosCapsuleOperationDto> posCapsuleOperationDtoPage = posCapsuleOperationService.findPagePosCapsuleOperation(
                posCapsuleAccountDtoSaved.getId(), 0, 2);
        assertNotNull(posCapsuleOperationDtoPage);
        assertEquals(2, posCapsuleOperationDtoPage.getTotalPages());

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

            List<PosCapsuleOperationDto> posCapsuleOperationDtoListBetween = posCapsuleOperationService.
                    findAllPosCapsuleOperationBetween(posCapsuleAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertNotNull(posCapsuleOperationDtoListBetween);
            assertEquals(3, posCapsuleOperationDtoListBetween.size());

            //Page<PosCapsuleOperationDto> findPagePosCapsuleOperationBetween(Long pcapsopId, Instant startDate,
            //                                           Instant endDate, int pagenum, int pagesize)
            Page<PosCapsuleOperationDto> posCapsuleOperationDtoPageBetween = posCapsuleOperationService.
                    findPagePosCapsuleOperationBetween(posCapsuleAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(posCapsuleOperationDtoPageBetween);
            assertEquals(2, posCapsuleOperationDtoPageBetween.getTotalPages());

            //List<PosCapsuleOperationDto> findAllPosCapsuleOperationBetween(Long pcapsopId, OperationType op_type,
            //                                                        Instant startDate, Instant endDate)
            List<PosCapsuleOperationDto> posCapsuleOperationDtoListBetweenoftype = posCapsuleOperationService.
                    findAllPosCapsuleOperationofTypeBetween(posCapsuleAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant());
            assertNotNull(posCapsuleOperationDtoListBetweenoftype);
            assertEquals(2, posCapsuleOperationDtoListBetweenoftype.size());

            //Page<PosCapsuleOperationDto> findPagePosCapsuleOperationBetween(Long pcapsopId, OperationType op_type,
            //                                                                 Instant startDate, Instant endDate,
            //                                                                 int pagenum, int pagesize)
            Page<PosCapsuleOperationDto> posCapsuleOperationDtoPageBetweenoftype = posCapsuleOperationService.
                    findPagePosCapsuleOperationofTypeBetween(posCapsuleAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(posCapsuleOperationDtoPageBetweenoftype);
            assertEquals(1, posCapsuleOperationDtoPageBetweenoftype.getTotalPages());
        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }

    @Test(expected = InvalidValueException.class)
    public void validate_saveCapsuleOperation_InvalidValue(){
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

        PosCapsuleAccountDto posCapsuleAccountDtoSaved = usedForTestForAll.savePosCapsuleAccount(0,
                ArticleDtoSaved, posDtoSaved, posCapsuleAccountService);
        assertNotNull(posCapsuleAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(posCapsuleAccountService.saveCapsuleOperation(posCapsuleAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));
        PosCapsuleAccountDto posCapsuleAccountDtoFound = posCapsuleAccountService.findPosCapsuleAccountById(
                posCapsuleAccountDtoSaved.getId());
        System.err.println("solde == "+posCapsuleAccountDtoFound.getPcsaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(posCapsuleAccountDtoFound.getPcsaNumber())==0);

        qteToSave = BigDecimal.valueOf(16);
        assertTrue(posCapsuleAccountService.saveCapsuleOperation(posCapsuleAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        PosCapsuleAccountDto posCapsuleAccountDtoFound1 = posCapsuleAccountService.findPosCapsuleAccountById(
                posCapsuleAccountDtoSaved.getId());
        System.err.println("solde == "+posCapsuleAccountDtoFound1.getPcsaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(posCapsuleAccountDtoFound1.getPcsaNumber())==0);
    }


}