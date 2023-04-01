package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTest;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class ProductServiceImplTest {
    @Autowired
    CategoryServiceImpl categoryService;
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
    ProductServiceImpl productService;

    @Test
    public void validateSaveProduct(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";

        String catDescription = "description de la categorie bierre en bouteille";
        String catName = "bierre en bouteille";
        String catCode = "b_btle";
        String catShortname = "bierre_btle";
        Long catParentId = null;

        PointofsaleDto posDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoToSaved = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, posDtoSaved);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        String prodCode = "exp_btle";
        String prodName = "Export";
        String prodDescription = "Export description";
        String prodAlias = "Exp";
        Boolean prodPerishable = true;


        ProductDto prodDtoToSaved = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved);

        ProductDto prodDtoSaved = productService.saveProduct(prodDtoToSaved);
        assertNotNull(prodDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_NotValidated(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";

        String catDescription = "description de la categorie bierre en bouteille";
        String catName = "bierre en bouteille";
        String catCode = "b_btle";
        String catShortname = "bierre_btle";
        Long catParentId = null;

        PointofsaleDto posDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoToSaved = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, posDtoSaved);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        String prodCode = "";
        String prodName = "Export";
        String prodDescription = "Export description";
        String prodAlias = "Exp";
        Boolean prodPerishable = true;


        ProductDto prodDtoToSaved = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved);

        ProductDto prodDtoSaved = productService.saveProduct(prodDtoToSaved);
        //The above line is supposed to launch the expected exception

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_MissingCategory(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";

        String catDescription = "description de la categorie bierre en bouteille";
        String catName = "bierre en bouteille";
        String catCode = "b_btle";
        String catShortname = "bierre_btle";
        Long catParentId = null;

        PointofsaleDto posDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoToSaved = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, posDtoSaved);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        catDtoSaved.setId(Long.valueOf(125487));

        String prodCode = "exp_btle";
        String prodName = "Export";
        String prodDescription = "Export description";
        String prodAlias = "Exp";
        Boolean prodPerishable = true;


        ProductDto prodDtoToSaved = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved);

        ProductDto prodDtoSaved = productService.saveProduct(prodDtoToSaved);
        //The above line is supposed to launch the expected exception due to the fact that the id of the category does
        //not match with any category in the DB

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_MissingPos(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";

        String catDescription = "description de la categorie bierre en bouteille";
        String catName = "bierre en bouteille";
        String catCode = "b_btle";
        String catShortname = "bierre_btle";
        Long catParentId = null;

        PointofsaleDto posDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoToSaved = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, posDtoSaved);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        posDtoSaved.setId(Long.valueOf(1254785));

        String prodCode = "exp_btle";
        String prodName = "Export";
        String prodDescription = "Export description";
        String prodAlias = "Exp";
        Boolean prodPerishable = true;


        ProductDto prodDtoToSaved = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved);

        ProductDto prodDtoSaved = productService.saveProduct(prodDtoToSaved);
        //The above line is supposed to launch the expected exception due to the fact that the id of the pointofsale does
        //not match with any pointofsale in the DB

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_DifferentPos(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";

        PointofsaleDto posDtoSaved1 = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        posName = "Depot djoutsa1";
        posAcronym = "D2D1";
        posDescription = "Les depots de Madame Djoutsa1";
        posEmail = "d2demail1@gmail.com";
        posNumtel1 = "6894574121";
        accountBalance = 0;
        currencyName = "Franc cfa1";
        currencyShortname = "F cfa1";

        String catDescription = "description de la categorie bierre en bouteille";
        String catName = "bierre en bouteille";
        String catCode = "b_btle";
        String catShortname = "bierre_btle";
        Long catParentId = null;

        PointofsaleDto posDtoSaved2 = new UsedForTest().savePointofsalePrim(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved2);

        CategoryDto catDtoToSaved = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, posDtoSaved1);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        String prodCode = "exp_btle";
        String prodName = "Export";
        String prodDescription = "Export description";
        String prodAlias = "Exp";
        Boolean prodPerishable = true;


        ProductDto prodDtoToSaved = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved2);

        ProductDto prodDtoSaved = productService.saveProduct(prodDtoToSaved);
        assertNotNull(prodDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validateSaveProduct_duplicated(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";

        String catDescription = "description de la categorie bierre en bouteille";
        String catName = "bierre en bouteille";
        String catCode = "b_btle";
        String catShortname = "bierre_btle";
        Long catParentId = null;

        PointofsaleDto posDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoToSaved = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, posDtoSaved);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        String prodCode = "exp_btle";
        String prodName = "Export";
        String prodDescription = "Export description";
        String prodAlias = "Exp";
        Boolean prodPerishable = true;


        ProductDto prodDtoToSaved = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved);

        ProductDto prodDtoSaved = productService.saveProduct(prodDtoToSaved);
        assertNotNull(prodDtoSaved);

        prodName = "Castel";
        prodDescription = "Castel description";
        prodAlias = "Cast";
        prodPerishable = true;


        ProductDto prodDtoToSaved1 = new UsedForTest().prepareProductToSaved(prodCode, prodName, prodDescription, prodAlias,
                prodPerishable, catDtoSaved, posDtoSaved);

        ProductDto prodDtoSaved1 = productService.saveProduct(prodDtoToSaved);
        //The above line is supposed to launch the expected exception

    }

}