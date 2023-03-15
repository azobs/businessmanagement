package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
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
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class CategoryServiceImplTest {
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    PosCashAccountServiceImpl posCashAccountService;
    @Autowired
    CurrencyServiceImpl currencyService;

    @Test
    public void saveCategory_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void saveCategory_InValid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("", "Biere",
                "BIERE", "Biere alcoolisee",  pointofsaleDtoSaved, categoryService);
        //The above line is supposed to launch the exception expected
    }

    @Test(expected = EntityNotFoundException.class)
    public void saveCategory_PosNotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);
        pointofsaleDtoSaved.setId(Long.valueOf(10000));
        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        //The above line is supposed to launch the exception expected
    }

    @Test(expected = DuplicateEntityException.class)
    public void saveCategory_DuplicateCat(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);
        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee",  pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved1);
        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEERE", "Bieredss",
                "BIERE", "Biere alcoolisee",  pointofsaleDtoSaved, categoryService);
        //The above line is supposed to launch the exception expected
    }

    @Test
    public void updateCategory_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);
        //Une fois enregistrer, on va le mettre a jour en modifiant tout jusqua son code
        categoryDtoSaved.setCatCode("BIERETTE");
        categoryDtoSaved.setCatShortname("Bierrette");
        categoryDtoSaved.setCatName("Bierrette alcoolisee");
        categoryDtoSaved.setCatDescription("Description des bierrettes alcoolisee");

        CategoryDto categoryDtoUpdated = usedForTest.updateCategory(categoryDtoSaved, categoryService);
        assertNotNull(categoryDtoUpdated);
        assertEquals("BIERETTE", categoryDtoUpdated.getCatCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void updateCategory_InValid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);
        //Une fois enregistrer, on va le mettre a jour en modifiant tout jusqua son code
        categoryDtoSaved.setCatCode("");//Pour generer l'exception souhaitee
        categoryDtoSaved.setCatShortname("Bierrette");
        categoryDtoSaved.setCatName("Bierrette alcoolisee");
        categoryDtoSaved.setCatDescription("Description des bierrettes alcoolisee");

        CategoryDto categoryDtoUpdated = usedForTest.updateCategory(categoryDtoSaved, categoryService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected=InvalidEntityException.class)
    public void updateCategory_Nullparam(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);
        //Une fois enregistrer, on va le mettre a jour en modifiant tout jusqua son code
        categoryDtoSaved.setCatCode("BIERETTE");
        categoryDtoSaved.setCatShortname("Bierrette");
        categoryDtoSaved.setCatName("Bierrette alcoolisee");
        categoryDtoSaved.setCatDescription("Description des bierrettes alcoolisee");

        //null pour generer lexception souhaitee
        CategoryDto categoryDtoUpdated = usedForTest.updateCategory(null, categoryService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateCategory_CatNotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);
        //Une fois enregistrer, on va le mettre a jour en modifiant tout jusqua son code
        categoryDtoSaved.setCatCode("BIERETTE");
        categoryDtoSaved.setCatShortname("Bierrette");
        categoryDtoSaved.setCatName("Bierrette alcoolisee");
        categoryDtoSaved.setCatDescription("Description des bierrettes alcoolisee");

        categoryDtoSaved.setId(categoryDtoSaved.getId()+10000);//Pour generer lexception souhaitee

        CategoryDto categoryDtoUpdated = usedForTest.updateCategory(categoryDtoSaved, categoryService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void updateCategory_CatDuplicated(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved1);
        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere SABC",
                "BIERE SABC", "Biere alcoolisee SABC", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved2);
        //Une fois enregistrer, on va le mettre a jour en modifiant tout jusqua son code
        categoryDtoSaved1.setCatCode("BIERE SABC");
        categoryDtoSaved1.setCatShortname("Bierrette");
        categoryDtoSaved1.setCatName("Bierrette alcoolisee");
        categoryDtoSaved1.setCatDescription("Description des bierrettes alcoolisee");

        CategoryDto categoryDtoUpdated = usedForTest.updateCategory(categoryDtoSaved1, categoryService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void findAllCategory_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        List<CategoryDto> categoryDtoList = categoryService.findAllCategory();
        assertNotNull(categoryDtoList);
        assertEquals(3, categoryDtoList.size());
    }

    @Test
    public void findAllCategoryInPointofsale_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        List<CategoryDto> categoryDtoList1 = categoryService.findAllCategoryInPointofsale(pointofsaleDtoSaved1.getId());
        assertNotNull(categoryDtoList1);
        assertEquals(1, categoryDtoList1.size());

        List<CategoryDto> categoryDtoList2 = categoryService.findAllCategoryInPointofsale(pointofsaleDtoSaved2.getId());
        assertNotNull(categoryDtoList2);
        assertEquals(2, categoryDtoList2.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findAllCategoryInPointofsale_PosNotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        List<CategoryDto> categoryDtoList1 = categoryService.findAllCategoryInPointofsale(pointofsaleDtoSaved1.getId());
        assertNotNull(categoryDtoList1);
        assertEquals(1, categoryDtoList1.size());

        List<CategoryDto> categoryDtoList2 = categoryService.findAllCategoryInPointofsale(pointofsaleDtoSaved2.getId()+1000);
        //The above line should launch the exception
    }

    @Test
    public void pageofExistingCategory_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        Page<CategoryDto> categoryDtoPage = categoryService.pageofExistingCategory(0,2);
        assertNotNull(categoryDtoPage);
        assertEquals(2, categoryDtoPage.getTotalPages());
    }

    @Test
    public void pageofExistingCategoryInPos_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        Page<CategoryDto> categoryDtoPage1 = categoryService.pageofCategoryInPos(pointofsaleDtoSaved1.getId(), 0, 2);
        assertNotNull(categoryDtoPage1);
        assertEquals(1, categoryDtoPage1.getTotalPages());

        Page<CategoryDto> categoryDtoPage2 = categoryService.pageofCategoryInPos(pointofsaleDtoSaved2.getId(), 0, 2);
        assertNotNull(categoryDtoPage2);
        assertEquals(1, categoryDtoPage2.getTotalPages());
    }

    @Test(expected = EntityNotFoundException.class)
    public void pageofExistingCategoryInPos_PosNotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        Page<CategoryDto> categoryDtoPage1 = categoryService.pageofCategoryInPos(pointofsaleDtoSaved1.getId(), 0, 2);
        assertNotNull(categoryDtoPage1);
        assertEquals(1, categoryDtoPage1.getTotalPages());

        Page<CategoryDto> categoryDtoPage2 = categoryService.pageofCategoryInPos(pointofsaleDtoSaved2.getId()+1000, 0, 2);
        //The above line should launch the exception
    }

    @Test(expected = NullArgumentException.class)
    public void pageofExistingCategoryInPos_NullValue(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        EnterpriseDto enterpriseDto = usedForTest.saveEnterprise("107253564", "C2PSI", "enterprise " +
                "description ", "C2PSI", "simplifie", "commerce general", enterpriseService, userBMService);

        CurrencyDto currencyDto = usedForTest.saveCurrency("Franc cfa", "F cfa", currencyService);

        PointofsaleDto pointofsaleDtoSaved1 = usedForTest.savePointofsale("depot bonendale", "D2DB",
                "depot de boisson", "d2db@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto , pointofsaleService);

        assertNotNull(pointofsaleDtoSaved1);

        PointofsaleDto pointofsaleDtoSaved2 = usedForTest.savePointofsale("depot foret bar", "D2DF",
                "depot de boisson", "d2df@gmail.com", "676170067", 0.0,
                enterpriseDto, currencyDto, pointofsaleService);
        assertNotNull(pointofsaleDtoSaved2);

        CategoryDto categoryDtoSaved1 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved1, categoryService);
        assertNotNull(categoryDtoSaved1);

        CategoryDto categoryDtoSaved2 = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved2);

        CategoryDto categoryDtoSaved3 = usedForTest.saveCategory("BIERE ALCOOLISEE SABC", "Biere",
                "BIERE SABC", "Biere alcoolisee", pointofsaleDtoSaved2, categoryService);
        assertNotNull(categoryDtoSaved3);

        //Maintenant on va lister les categories enregistrer dans la BD
        Page<CategoryDto> categoryDtoPage1 = categoryService.pageofCategoryInPos(pointofsaleDtoSaved1.getId(), 0, 2);
        assertNotNull(categoryDtoPage1);
        assertEquals(1, categoryDtoPage1.getTotalPages());

        Page<CategoryDto> categoryDtoPage2 = categoryService.pageofCategoryInPos(null, 0, 2);
        //The above line should launch the expected exception
    }

    @Test
    public void deleteCategoryById_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);

        Boolean delete = categoryService.deleteCategoryById(categoryDtoSaved.getId());
        assertNotNull(delete);
        assertTrue(delete);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteCategoryById_NotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);

        Boolean delete = categoryService.deleteCategoryById(categoryDtoSaved.getId()+1000);
        assertNotNull(delete);
        assertTrue(delete);
    }

    @Test
    public void deleteCategoryByCatCode_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);

        Boolean delete = categoryService.deleteCategoryByCatCode(categoryDtoSaved.getCatCode(),
                categoryDtoSaved.getCatPosDto().getId());
        assertNotNull(delete);
        assertTrue(delete);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteCategoryByCatCode_NotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(categoryService);

        UsedForTest usedForTest = new UsedForTest();
        System.out.println("Called for a function "+ usedForTest.justToTest());

        PointofsaleDto pointofsaleDtoSaved = usedForTest.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa",pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved = usedForTest.saveCategory("BIERE ALCOOLISEE", "Biere",
                "BIERE", "Biere alcoolisee", pointofsaleDtoSaved, categoryService);
        assertNotNull(categoryDtoSaved);

        Boolean delete = categoryService.deleteCategoryByCatCode(categoryDtoSaved.getCatCode(),
                categoryDtoSaved.getCatPosDto().getId()+1000);
        assertNotNull(delete);
        assertTrue(delete);
    }

}