package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
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

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class CategoryServiceImplTest {
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

    @Test
    public void validateNullParentCategory(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosDto(pointofsaleDtoSaved)
                .categoryParentId(null)
                .catDescription("description de la categorie 1")
                .catCode("cat code1")
                .catShortname("cat1")
                .catName("Cat Name1")
                .build();

        CategoryDto categoryDtoSaved = categoryService.saveCategory(categoryDtoToSaved);
        assertNotNull(categoryDtoSaved);
    }

    @Test
    public void validateNotNullParentCategory(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        catCode = "catCode3";
        catName = "catName3";
        catDescription = "catDescription3";
        catShortname = "catShortname3";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved3 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved3 = categoryService.saveCategory(categoryDtoToSaved3);
        assertNotNull(categoryDtoSaved3);

    }

    @Test(expected = InvalidEntityException.class)
    public void saveCategory_NonValidated(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosDto(null)
                .categoryParentId(null)
                .catDescription("description de la categorie 1")
                .catCode("cat code1")
                .catShortname("cat1")
                .catName("Cat Name1")
                .build();

        CategoryDto categoryDtoSaved = categoryService.saveCategory(categoryDtoToSaved);
        //The above line is supposed normally to launch the exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void saveCategory_Duplicated(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode1";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        //The above line is supposed to launch the expected exception.
        //assertNotNull(categoryDtoSaved2);

    }

    @Test(expected = EntityNotFoundException.class)
    public void saveCategory_ParentNotFound(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId()+20000;

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        //The above line is supposed to launch the expected exception.
        //assertNotNull(categoryDtoSaved2);

    }

    @Test
    public void validateUpdateNullParentCategory(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosDto(pointofsaleDtoSaved)
                .categoryParentId(null)
                .catDescription("description de la categorie 1")
                .catCode("cat code1")
                .catShortname("cat1")
                .catName("Cat Name1")
                .build();

        CategoryDto categoryDtoSaved = categoryService.saveCategory(categoryDtoToSaved);
        assertNotNull(categoryDtoSaved);

        assertEquals("cat code1", categoryDtoSaved.getCatCode());

        //On peut donc modifier des parametres
        categoryDtoSaved.setCatCode("cat code 2");
        categoryDtoSaved.setCatName("cat name");
        categoryDtoSaved.setCatShortname("cat shortname");
        categoryDtoSaved.setCatDescription("new cat description of cat code 2");

        //On peut donc lancer la modification
        CategoryDto categoryDtoUpdated = categoryService.updateCategory(categoryDtoSaved);
        assertEquals(categoryDtoSaved.getId().longValue(), categoryDtoUpdated.getId().longValue());
        assertEquals("cat code 2", categoryDtoUpdated.getCatCode());
        assertEquals("cat name", categoryDtoUpdated.getCatName());
    }

    @Test
    public void validateUpdateNotNullParentCategory(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        catCode = "catCode3";
        catName = "catName3";
        catDescription = "catDescription3";
        catShortname = "catShortname3";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved3 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved3 = categoryService.saveCategory(categoryDtoToSaved3);
        assertNotNull(categoryDtoSaved3);

        categoryDtoSaved3.setCatCode("cat code 4");
        categoryDtoSaved3.setCatName("cat name");
        categoryDtoSaved3.setCatShortname("cat shortname");
        categoryDtoSaved3.setCatDescription("new cat description of cat code 4");
        categoryDtoSaved3.setCategoryParentId(categoryDtoSaved2.getId());

        //On peut donc lancer la modification
        CategoryDto categoryDtoUpdated = categoryService.updateCategory(categoryDtoSaved3);
        assertEquals(categoryDtoSaved3.getId().longValue(), categoryDtoUpdated.getId().longValue());
        assertEquals("cat code 4", categoryDtoUpdated.getCatCode());
        assertEquals("cat name", categoryDtoUpdated.getCatName());
        assertEquals(categoryDtoSaved3.getId(), categoryDtoUpdated.getId());

    }

    @Test
    public void validatefindChildCategoryof(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        catCode = "catCode3";
        catName = "catName3";
        catDescription = "catDescription3";
        catShortname = "catShortname3";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved3 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved3 = categoryService.saveCategory(categoryDtoToSaved3);
        assertNotNull(categoryDtoSaved3);

        catCode = "catCode4";
        catName = "catName4";
        catDescription = "catDescription4";
        catShortname = "catShortname4";
        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoToSaved4 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved4 = categoryService.saveCategory(categoryDtoToSaved4);
        assertNotNull(categoryDtoSaved4);

        List<CategoryDto> categoryDtoListOfChild1 = categoryService.findChildCategoryOf(categoryDtoSaved1.getId());//2
        List<CategoryDto> categoryDtoListOfChild2 = categoryService.findChildCategoryOf(categoryDtoSaved2.getId());//1
        List<CategoryDto> categoryDtoListOfChild3 = categoryService.findChildCategoryOf(categoryDtoSaved3.getId());//0
        List<CategoryDto> categoryDtoListOfChild4 = categoryService.findChildCategoryOf(categoryDtoSaved4.getId());//0

        System.out.println("categoryDtoListOfChild1"+categoryDtoListOfChild1.toString());
        System.out.println("categoryDtoListOfChild2"+categoryDtoListOfChild2.toString());

        assertNotNull(categoryDtoListOfChild1);
        assertNotNull(categoryDtoListOfChild2);

        assertEquals(2, categoryDtoListOfChild1.size());
        assertEquals(1, categoryDtoListOfChild2.size());
        assertEquals(0, categoryDtoListOfChild3.size());
        assertEquals(0, categoryDtoListOfChild4.size());

    }

    @Test
    public void validatefindAscendantCategoryof(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        catCode = "catCode3";
        catName = "catName3";
        catDescription = "catDescription3";
        catShortname = "catShortname3";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved3 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved3 = categoryService.saveCategory(categoryDtoToSaved3);
        assertNotNull(categoryDtoSaved3);

        catCode = "catCode4";
        catName = "catName4";
        catDescription = "catDescription4";
        catShortname = "catShortname4";
        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoToSaved4 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved4 = categoryService.saveCategory(categoryDtoToSaved4);
        assertNotNull(categoryDtoSaved4);

        List<CategoryDto> categoryDtoListOfAsc1 = categoryService.findAscandantCategoryof(categoryDtoSaved1.getId());//0
        List<CategoryDto> categoryDtoListOfAsc2 = categoryService.findAscandantCategoryof(categoryDtoSaved2.getId());//1
        List<CategoryDto> categoryDtoListOfAsc3 = categoryService.findAscandantCategoryof(categoryDtoSaved3.getId());//1
        List<CategoryDto> categoryDtoListOfAsc4 = categoryService.findAscandantCategoryof(categoryDtoSaved4.getId());//2

        System.out.println("categoryDtoListOfAsc1"+categoryDtoListOfAsc1.toString());
        System.out.println("categoryDtoListOfAsc2"+categoryDtoListOfAsc2.toString());

        assertNotNull(categoryDtoListOfAsc1);
        assertNotNull(categoryDtoListOfAsc2);

        assertEquals(0, categoryDtoListOfAsc1.size());
        assertEquals(1, categoryDtoListOfAsc2.size());
        assertEquals(1, categoryDtoListOfAsc3.size());
        assertEquals(2, categoryDtoListOfAsc4.size());

    }

    @Test
    public void validate_findAllCategoryInPointofsale(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        catCode = "catCode3";
        catName = "catName3";
        catDescription = "catDescription3";
        catShortname = "catShortname3";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved3 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved3 = categoryService.saveCategory(categoryDtoToSaved3);
        assertNotNull(categoryDtoSaved3);

        catCode = "catCode4";
        catName = "catName4";
        catDescription = "catDescription4";
        catShortname = "catShortname4";
        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoToSaved4 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved4 = categoryService.saveCategory(categoryDtoToSaved4);
        assertNotNull(categoryDtoSaved4);

        List<CategoryDto> categoryDtoList = categoryService.findAllCategoryInPointofsale(pointofsaleDtoSaved.getId());
        assertNotNull(categoryDtoList);
        assertEquals(4, categoryDtoList.size());

    }

    @Test
    public void validate_findCategoryInPointofsale(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        catCode = "catCode3";
        catName = "catName3";
        catDescription = "catDescription3";
        catShortname = "catShortname3";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved3 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved3 = categoryService.saveCategory(categoryDtoToSaved3);
        assertNotNull(categoryDtoSaved3);

        catCode = "catCode4";
        catName = "catName4";
        catDescription = "catDescription4";
        catShortname = "catShortname4";
        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoToSaved4 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);;

        CategoryDto categoryDtoSaved4 = categoryService.saveCategory(categoryDtoToSaved4);
        assertNotNull(categoryDtoSaved4);

        Page<CategoryDto> categoryDtoPage = categoryService.findCategoryInPointofsale(pointofsaleDtoSaved.getId(), 0, 2);
        assertNotNull(categoryDtoPage);
        assertEquals(2, categoryDtoPage.getTotalPages());

    }

    @Test
    public void validate_deleteCategoryById(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        assertTrue(categoryService.deleteCategoryById(categoryDtoSaved2.getId()));

    }

    @Test(expected = EntityNotFoundException.class)
    public void validateNotFound_deleteCategoryById(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        categoryService.deleteCategoryById(categoryDtoSaved2.getId()+1000);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_deleteCategoryByCatCode(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        assertTrue(categoryService.deleteCategoryByCatCode(categoryDtoSaved2.getCatCode(), pointofsaleDtoSaved.getId()));

    }

    @Test(expected = EntityNotFoundException.class)
    public void validateNotFound_deleteCategoryByCatCode(){
        String posName = "Depot djoutsa";
        String posAcronym = "D2D";
        String posDescription = "Les depots de Madame Djoutsa";
        String posEmail = "d2demail@gmail.com";
        String posNumtel1 = "689457412";
        double accountBalance = 0;
        String currencyName = "Franc cfa";
        String currencyShortname = "F cfa";
        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = new UsedForTest().savePointofsale(posName, posAcronym, posDescription, posEmail, posNumtel1, accountBalance,
                currencyName, currencyShortname, pointofsaleService, enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);
        String catCode = "catCode1";
        String catName = "catName1";
        String catDescription = "catDescription1";
        String catShortname = "catShortname1";
        Long catParentId  = null;

        CategoryDto categoryDtoToSaved1 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);
        System.out.println("categoryDtoToSaved1 = "+categoryDtoToSaved1);
        CategoryDto categoryDtoSaved1 = categoryService.saveCategory(categoryDtoToSaved1);
        assertNotNull(categoryDtoSaved1);

        catCode = "catCode2";
        catName = "catName2";
        catDescription = "catDescription2";
        catShortname = "catShortname2";
        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = new UsedForTest().prepareCategoryToSaved(catCode, catName, catShortname,
                catDescription, catParentId, pointofsaleDtoSaved);

        CategoryDto categoryDtoSaved2 = categoryService.saveCategory(categoryDtoToSaved2);
        assertNotNull(categoryDtoSaved2);

        categoryService.deleteCategoryByCatCode(categoryDtoSaved2.getCatCode()+"shdsdsd",
                pointofsaleDtoSaved.getId());
        //The above line is supposed to launch the excepted exception

    }


}