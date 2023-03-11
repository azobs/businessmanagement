package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
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

import java.math.BigDecimal;

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


}