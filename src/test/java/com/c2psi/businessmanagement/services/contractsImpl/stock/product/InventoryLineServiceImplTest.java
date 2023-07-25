package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryLineService;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryService;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.BasePriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class InventoryLineServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductFormatedServiceImpl productFormatedService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    FormatServiceImpl formatService;
    @Autowired
    UnitServiceImpl unitService;
    @Autowired
    BasePriceServiceImpl basePriceService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    InventoryLineService inventorylineService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveInventoryLine(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventoryLine_Invalid(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine_Invalid(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventoryLine_NullInv(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        inventoryDtoSaved.setId(null);

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventoryLine_NotExistInv(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        inventoryDtoSaved.setId(Long.valueOf(1245789));

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventoryLine_NullArt(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventoryLine_NotExistArt(){
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

        ArticleDtoSaved.setId(Long.valueOf(45896321));

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventoryLine_DifferentPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(78, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
        assertNotNull(formatDtoSaved);

        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(74, posDtoSaved1, formatService);
        assertNotNull(formatDtoSaved1);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, null, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(45, null, posDtoSaved1, categoryService);
        assertNotNull(catDtoSaved1);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(96, posDtoSaved1, catDtoSaved, productService);
        assertNotNull(prodDtoSaved1);

        ProductFormatedDto productFormatedDtoSaved = usedForTestForProduct.saveProductFormatedDto(0, prodDtoSaved,
                formatDtoSaved, productFormatedService);
        assertNotNull(productFormatedDtoSaved);

        ProductFormatedDto productFormatedDtoSaved1 = usedForTestForProduct.saveProductFormatedDto(52, prodDtoSaved1,
                formatDtoSaved1, productFormatedService);
        assertNotNull(productFormatedDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);

        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(41, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved1);

        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        CurrencyDto currencyDtoSaved1 = usedForTestForAll.saveCurrency(5, currencyService);
        assertNotNull(currencyDtoSaved1);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(852, currencyDtoSaved1, basePriceService);
        assertNotNull(basePriceDtoSaved1);

        ArticleDto ArticleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved);

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(45, productFormatedDtoSaved1, unitDtoSaved1,
                basePriceDtoSaved1, posDtoSaved1, articleService);
        assertNotNull(ArticleDtoSaved1);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved1, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveInventoryLine_Duplicated(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoSaved1 = usedForTestForProduct.saveInventoryLine(25, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);

    }

    @Test
    public void validate_UpdateInventoryLine(){
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

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(13, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoFound = inventorylineService.findInventoryLineById(inventoryLineDtoSaved.getId());
        assertNotNull(inventoryLineDtoFound);
        assertEquals(inventoryLineDtoSaved.getId(), inventoryLineDtoFound.getId());

        inventoryLineDtoFound.setInvlineArtDto(ArticleDtoSaved1);
        inventoryLineDtoFound.setInvlineComment("newComment inventoryline");
        InventoryLineDto inventoryLineDtoUpdated = inventorylineService.updateInventoryLine(inventoryLineDtoFound);
        assertEquals("newComment inventoryline", inventoryLineDtoUpdated.getInvlineComment());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateInventoryLine_NotExist(){
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

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(13, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoFound = inventorylineService.findInventoryLineById(inventoryLineDtoSaved.getId());
        assertNotNull(inventoryLineDtoFound);
        assertEquals(inventoryLineDtoSaved.getId(), inventoryLineDtoFound.getId());

        inventoryLineDtoFound.setId(Long.valueOf(4578569));
        inventoryLineDtoFound.setInvlineArtDto(ArticleDtoSaved1);
        inventoryLineDtoFound.setInvlineComment("newComment inventoryline");
        InventoryLineDto inventoryLineDtoUpdated = inventorylineService.updateInventoryLine(inventoryLineDtoFound);
        assertEquals("newComment inventoryline", inventoryLineDtoUpdated.getInvlineComment());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateInventoryLine_UpdateInv(){
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

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(13, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryDto inventoryDtoSaved1 = usedForTestForProduct.saveInventory(85, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved1);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved1.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoFound = inventorylineService.findInventoryLineById(inventoryLineDtoSaved.getId());
        assertNotNull(inventoryLineDtoFound);
        assertEquals(inventoryLineDtoSaved.getId(), inventoryLineDtoFound.getId());


        inventoryLineDtoFound.setInvlineArtDto(ArticleDtoSaved1);
        inventoryLineDtoFound.setInvlineInvDto(inventoryDtoSaved1);
        inventoryLineDtoFound.setInvlineComment("newComment inventoryline");
        InventoryLineDto inventoryLineDtoUpdated = inventorylineService.updateInventoryLine(inventoryLineDtoFound);
        assertEquals("newComment inventoryline", inventoryLineDtoUpdated.getInvlineComment());

    }


    @Test
    public void validate_FindInventoryLineByArticleinInv(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoFound = inventorylineService.findInventoryLineByArticleinInv(
                inventoryDtoSaved.getId(), ArticleDtoSaved.getId());
        assertNotNull(inventoryLineDtoFound);
        assertTrue(inventoryLineDtoSaved.getInvlineRealqteinstock().compareTo(inventoryLineDtoFound.getInvlineRealqteinstock()) == 0);

    }

    @Test
    public void validate_DeleteInventoryLine(){
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

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        assertTrue(inventorylineService.deleteInventoryLineById(inventoryLineDtoSaved.getId()));

    }

    @Test
    public void validate_FindAllInventorylineofInv(){
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

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoSaved1 = usedForTestForProduct.saveInventoryLine(1, posDtoSaved,
                ArticleDtoSaved1, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved1);

        InventoryLineDto inventoryLineDtoSaved2 = usedForTestForProduct.saveInventoryLine(2, posDtoSaved,
                ArticleDtoSaved2, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved2);

        List<InventoryLineDto> inventoryLineDtoList = inventorylineService.findAllInventorylineofInv(
                inventoryDtoSaved.getId());
        assertNotNull(inventoryLineDtoList);
        assertEquals(3, inventoryLineDtoList.size());
    }

    @Test
    public void validate_FindPageInventorylineofInv(){
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

        ArticleDto ArticleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved1);

        ArticleDto ArticleDtoSaved2 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(ArticleDtoSaved2);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryLineDto inventoryLineDtoSaved = usedForTestForProduct.saveInventoryLine(0, posDtoSaved,
                ArticleDtoSaved, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved);

        InventoryLineDto inventoryLineDtoSaved1 = usedForTestForProduct.saveInventoryLine(1, posDtoSaved,
                ArticleDtoSaved1, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved1);

        InventoryLineDto inventoryLineDtoSaved2 = usedForTestForProduct.saveInventoryLine(2, posDtoSaved,
                ArticleDtoSaved2, inventoryDtoSaved, inventorylineService);
        assertNotNull(inventoryLineDtoSaved2);

        Page<InventoryLineDto> inventoryLineDtoPage = inventorylineService.findPageInventorylineofInv(
                inventoryDtoSaved.getId(), 0, 2);
        assertNotNull(inventoryLineDtoPage);
        assertEquals(2, inventoryLineDtoPage.getTotalPages());
    }

}