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
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
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

    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validateNullParentCategory(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosId(pointofsaleDtoSaved.getId())
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

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(2, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved3 = usedForTestForProduct.saveCategory(3, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved3);

    }

    @Test(expected = InvalidEntityException.class)
    public void saveCategory_NonValidated(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosId(null)
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

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoToSaved2 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);

        //The above line is supposed to launch the expected exception.
        //assertNotNull(categoryDtoSaved2);

    }

    @Test(expected = EntityNotFoundException.class)
    public void saveCategory_ParentNotFound(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId()+20000;

        CategoryDto categoryDtoToSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        //The above line is supposed to launch the expected exception.
        //assertNotNull(categoryDtoSaved2);

    }

    @Test
    public void validateUpdateNullParentCategory(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosId(pointofsaleDtoSaved.getId())
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

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved3 = usedForTestForProduct.saveCategory(2, catParentId, pointofsaleDtoSaved,
                categoryService);
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

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved3 = usedForTestForProduct.saveCategory(2, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved3);

        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoSaved4 = usedForTestForProduct.saveCategory(3, catParentId, pointofsaleDtoSaved,
                categoryService);
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

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved3 = usedForTestForProduct.saveCategory(2, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved3);

        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoSaved4 = usedForTestForProduct.saveCategory(3, catParentId, pointofsaleDtoSaved,
                categoryService);
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

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved3 = usedForTestForProduct.saveCategory(2, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved3);

        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoSaved4 = usedForTestForProduct.saveCategory(3, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved4);

        List<CategoryDto> categoryDtoList = categoryService.findAllCategoryInPointofsale(pointofsaleDtoSaved.getId());
        assertNotNull(categoryDtoList);
        assertEquals(4, categoryDtoList.size());

    }

    @Test
    public void validate_findCategoryInPointofsale(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved3 = usedForTestForProduct.saveCategory(2, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved3);

        catParentId  = categoryDtoSaved2.getId();

        CategoryDto categoryDtoSaved4 = usedForTestForProduct.saveCategory(3, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved4);

        Page<CategoryDto> categoryDtoPage = categoryService.findPageCategoryInPointofsale(pointofsaleDtoSaved.getId(), 0, 2);
        assertNotNull(categoryDtoPage);
        assertEquals(2, categoryDtoPage.getTotalPages());

    }

    @Test
    public void validate_deleteCategoryById(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        assertTrue(categoryService.deleteCategoryById(categoryDtoSaved2.getId()));

    }

    @Test(expected = EntityNotFoundException.class)
    public void validateNotFound_deleteCategoryById(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        categoryService.deleteCategoryById(categoryDtoSaved2.getId()+1000);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_deleteCategoryByCatCode(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        assertTrue(categoryService.deleteCategoryByCatCode(categoryDtoSaved2.getCatCode(), pointofsaleDtoSaved.getId()));

    }

    @Test(expected = EntityNotFoundException.class)
    public void validateNotFound_deleteCategoryByCatCode(){

        PointofsaleService pointofsaleService = this.pointofsaleService;
        EnterpriseService enterpriseService = this.enterpriseService;
        UserBMService userBMService = this.userBMService;
        CurrencyService currencyService = this.currencyService;

        PointofsaleDto pointofsaleDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService,
                enterpriseService, userBMService, currencyService);

        assertNotNull(pointofsaleDtoSaved);

        Long catParentId  = null;

        CategoryDto categoryDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved1);

        catParentId  = categoryDtoSaved1.getId();

        CategoryDto categoryDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, pointofsaleDtoSaved,
                categoryService);
        assertNotNull(categoryDtoSaved2);

        categoryService.deleteCategoryByCatCode(categoryDtoSaved2.getCatCode()+"shdsdsd",
                pointofsaleDtoSaved.getId());
        //The above line is supposed to launch the excepted exception

    }


}