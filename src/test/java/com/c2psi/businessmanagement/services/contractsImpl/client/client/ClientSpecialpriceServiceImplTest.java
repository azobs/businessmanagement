package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientSpecialpriceService;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.BasePriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.SpecialPriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.*;
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
public class ClientSpecialpriceServiceImplTest {
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
    ClientServiceImpl clientService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    UnitServiceImpl unitService;
    @Autowired
    BasePriceServiceImpl basePriceService;
    @Autowired
    SpecialPriceServiceImpl specialPriceService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    FormatServiceImpl formatService;
    @Autowired
    ProductFormatedServiceImpl productFormatedService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    ClientSpecialpriceServiceImpl clientSpecialpriceService;

    @Test
    public void validate_SaveClientSpecialprice(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveClientSpecialprice_Duplicated(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(2, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved1, clientSpecialpriceService);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClientSpecialprice_Invalid(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice_Invalid(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClientSpecialprice_NullIdClient(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());
        clientDtoSaved.setId(null);

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClientSpecialprice_ClientNotFound(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());
        clientDtoSaved.setId(Long.valueOf(125487));

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClientSpecialprice_ArticleNotFound(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);
        articleDtoSaved.setId(Long.valueOf(125487));

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClientSpecialprice_SpecialpriceNotFound(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);
        specialPriceDtoSaved.setId(Long.valueOf(125487));

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

    }


    @Test
    public void validate_UpdateClientSpecialprice(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(1, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        clientSpecialpriceDtoSaved.setCltSpPDto(specialPriceDtoSaved1);
        ClientSpecialpriceDto clientSpecialpriceDtoUpdated = clientSpecialpriceService.updateClientSpecialprice(clientSpecialpriceDtoSaved);
        assertNotNull(clientSpecialpriceDtoUpdated);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateClientSpecialprice_NewClient(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(1, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        clientSpecialpriceDtoSaved.setCltSpPDto(specialPriceDtoSaved1);
        clientSpecialpriceDtoSaved.setCltSpClientDto(clientDtoSaved1);
        ClientSpecialpriceDto clientSpecialpriceDtoUpdated = clientSpecialpriceService.updateClientSpecialprice(clientSpecialpriceDtoSaved);
        assertNotNull(clientSpecialpriceDtoUpdated);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateClientSpecialprice_NewArticle(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(2, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(1, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        clientSpecialpriceDtoSaved.setCltSpPDto(specialPriceDtoSaved1);
        //clientSpecialpriceDtoSaved.setCltSpClientDto(clientDtoSaved1);
        clientSpecialpriceDtoSaved.setCltSpArtDto(articleDtoSaved1);
        ClientSpecialpriceDto clientSpecialpriceDtoUpdated = clientSpecialpriceService.updateClientSpecialprice(clientSpecialpriceDtoSaved);
        assertNotNull(clientSpecialpriceDtoUpdated);

    }

    @Test
    public void validate_FindClientSpecialpriceById(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(1, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        clientSpecialpriceDtoSaved.setCltSpPDto(specialPriceDtoSaved1);
        ClientSpecialpriceDto clientSpecialpriceDtoUpdated = clientSpecialpriceService.updateClientSpecialprice(clientSpecialpriceDtoSaved);
        assertNotNull(clientSpecialpriceDtoUpdated);

        ClientSpecialpriceDto clientSpecialpriceDtoFound = clientSpecialpriceService.findClientSpecialpriceById(
                clientSpecialpriceDtoUpdated.getId());
        assertNotNull(clientSpecialpriceDtoFound);
        assertEquals(clientSpecialpriceDtoSaved.getCltSpClientDto().getClientName(), clientSpecialpriceDtoFound.getCltSpClientDto().getClientName());

    }

    @Test
    public void validate_FindClientSpecialpriceByArticleAndClient(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(1, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        clientSpecialpriceDtoSaved.setCltSpPDto(specialPriceDtoSaved1);
        ClientSpecialpriceDto clientSpecialpriceDtoUpdated = clientSpecialpriceService.updateClientSpecialprice(clientSpecialpriceDtoSaved);
        assertNotNull(clientSpecialpriceDtoUpdated);

        ClientSpecialpriceDto clientSpecialpriceDtoFound = clientSpecialpriceService.
                findClientSpecialpriceofArticleforClient(articleDtoSaved.getId(), clientDtoSaved.getId());
        assertNotNull(clientSpecialpriceDtoFound);
        assertEquals(clientSpecialpriceDtoSaved.getCltSpClientDto().getClientName(), clientSpecialpriceDtoFound.getCltSpClientDto().getClientName());
        assertEquals(clientSpecialpriceDtoUpdated.getCltSpPDto().getId(), clientSpecialpriceDtoFound.getCltSpPDto().getId());
    }

    @Test
    public void validate_FindAllSpecialpriceofArticle(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);


        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved1, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved2 = usedForTestForClient.saveClientSpecialprice(2,
                clientDtoSaved2, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved2);

        List<ClientSpecialpriceDto> clientSpecialpriceDtoList = clientSpecialpriceService.findAllSpecialpriceofArticle(articleDtoSaved.getId());
        assertNotNull(clientSpecialpriceDtoList);
        assertEquals(3, clientSpecialpriceDtoList.size());

    }

    @Test
    public void validate_FindPageSpecialpriceofArticle(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);


        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved1, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved2 = usedForTestForClient.saveClientSpecialprice(2,
                clientDtoSaved2, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved2);

        Page<ClientSpecialpriceDto> clientSpecialpriceDtoPage = clientSpecialpriceService.
                findPageSpecialpriceofArticle(articleDtoSaved.getId(), 0, 1);
        assertNotNull(clientSpecialpriceDtoPage);
        assertEquals(3, clientSpecialpriceDtoPage.getTotalPages());

    }

    @Test
    public void validate_FindAllSpecialpriceofClient(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);


        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved1, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved2 = usedForTestForClient.saveClientSpecialprice(2,
                clientDtoSaved2, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved2);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved3 = usedForTestForClient.saveClientSpecialprice(3,
                clientDtoSaved2, articleDtoSaved1, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved3);

        List<ClientSpecialpriceDto> clientSpecialpriceDtoList = clientSpecialpriceService.findAllSpecialpriceofClient(clientDtoSaved2.getId());
        assertNotNull(clientSpecialpriceDtoList);
        assertEquals(2, clientSpecialpriceDtoList.size());

    }

    @Test
    public void validate_FindPageSpecialpriceofClient(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);


        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved1, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved2 = usedForTestForClient.saveClientSpecialprice(2,
                clientDtoSaved2, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved2);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved3 = usedForTestForClient.saveClientSpecialprice(3,
                clientDtoSaved2, articleDtoSaved1, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved3);

        Page<ClientSpecialpriceDto> clientSpecialpriceDtoPage = clientSpecialpriceService.
                findPageSpecialpriceofClient(clientDtoSaved2.getId(),0,2);
        assertNotNull(clientSpecialpriceDtoPage);
        assertEquals(1, clientSpecialpriceDtoPage.getTotalPages());

    }

    @Test
    public void validate_FindAllClientWithSpecialpriceonArticle(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);


        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved1, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved2 = usedForTestForClient.saveClientSpecialprice(2,
                clientDtoSaved2, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved2);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved3 = usedForTestForClient.saveClientSpecialprice(3,
                clientDtoSaved2, articleDtoSaved1, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved3);

        List<ClientDto> clientDtoList = clientSpecialpriceService.findAllClientWithSpecialpriceonArticle(articleDtoSaved.getId());
        assertNotNull(clientDtoList);
        assertEquals(3, clientDtoList.size());

    }

    @Test
    public void validate_FindPageClientWithSpecialpriceonArticle(){
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

        //On a l'article
        ArticleDto articleDtoSaved = usedForTestForProduct.saveArticle(0, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved);

        ArticleDto articleDtoSaved1 = usedForTestForProduct.saveArticle(1, productFormatedDtoSaved, unitDtoSaved,
                basePriceDtoSaved, posDtoSaved, articleService);
        assertNotNull(articleDtoSaved1);

        //On a le client
        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        //On a le prix special
        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);


        ClientSpecialpriceDto clientSpecialpriceDtoSaved = usedForTestForClient.saveClientSpecialprice(0,
                clientDtoSaved, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved1 = usedForTestForClient.saveClientSpecialprice(1,
                clientDtoSaved1, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved1);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved2 = usedForTestForClient.saveClientSpecialprice(2,
                clientDtoSaved2, articleDtoSaved, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved2);

        ClientSpecialpriceDto clientSpecialpriceDtoSaved3 = usedForTestForClient.saveClientSpecialprice(3,
                clientDtoSaved2, articleDtoSaved1, specialPriceDtoSaved, clientSpecialpriceService);
        assertNotNull(clientSpecialpriceDtoSaved3);

        Page<ClientDto> clientDtoPage = clientSpecialpriceService.findPageClientWithSpecialpriceonArticle(articleDtoSaved.getId(), 0, 2);
        assertNotNull(clientDtoPage);
        assertEquals(2, clientDtoPage.getTotalPages());

    }



}