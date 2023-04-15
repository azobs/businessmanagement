package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
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
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validateSaveProduct(){

        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        assertNotNull(prodDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_NotValidated(){

        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct_NonValid(0, posDtoSaved, catDtoSaved, productService);
        //The above line is supposed to launch the expected exception

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_MissingCategory(){

        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        catDtoSaved.setId(Long.valueOf(125487));

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);

        //The above line is supposed to launch the expected exception due to the fact that the id of the category does
        //not match with any category in the DB

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_MissingPos(){

        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService, userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);

        assertNotNull(catDtoSaved);

        posDtoSaved.setId(Long.valueOf(1254785));

        ProductDto prodDtoToSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);
        //The above line is supposed to launch the expected exception due to the fact that the id of the pointofsale does
        //not match with any pointofsale in the DB

    }

    @Test(expected = InvalidEntityException.class)
    public void validateSaveProduct_DifferentPos(){

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        Long catParentId = null;

        PointofsaleDto posDtoSaved2 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved2);

        //P0125468970 Commerce general DJOUTSA0
        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved1, categoryService);

        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved2, catDtoSaved, productService);

        assertNotNull(prodDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validateSaveProduct_duplicated(){

        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoToSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);

        CategoryDto catDtoSaved = categoryService.saveCategory(catDtoToSaved);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);

        assertNotNull(prodDtoSaved);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);

        //The above line is supposed to launch the expected exception

    }

    @Test
    public void validateUpdateProduct(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved);

        prodDtoSaved.setProdCode("prodCodeprim1");
        assertEquals(catDtoSaved1.getId(), prodDtoSaved.getProdCatDto().getId());
        prodDtoSaved.setProdCatDto(catDtoSaved2);
        prodDtoSaved.setProdName("newNameProdName");
        prodDtoSaved.setProdDescription("new Description prodName");

        ProductDto productDtoUpdated = productService.updateProduct(prodDtoSaved);
        assertNotNull(productDtoUpdated);
        assertEquals(prodDtoSaved.getId(), productDtoUpdated.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validateUpdateProduct_EntityNotValid(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved);

        prodDtoSaved.setProdCode("");
        assertEquals(catDtoSaved1.getId(), prodDtoSaved.getProdCatDto().getId());
        prodDtoSaved.setProdCatDto(catDtoSaved2);
        prodDtoSaved.setProdName("newNameProdName");
        prodDtoSaved.setProdDescription("new Description prodName");

        ProductDto productDtoUpdated = productService.updateProduct(prodDtoSaved);
        //The above line is supposed to launch the exception due to the fact that prodCode is empty
    }

    @Test(expected = InvalidEntityException.class)
    public void validateUpdateProduct_prodIdnull(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved);

        prodDtoSaved.setId(null);
        prodDtoSaved.setProdCode("prodCodeprim1");
        assertEquals(catDtoSaved1.getId(), prodDtoSaved.getProdCatDto().getId());
        prodDtoSaved.setProdCatDto(catDtoSaved2);
        prodDtoSaved.setProdName("newNameProdName");
        prodDtoSaved.setProdDescription("new Description prodName");

        ProductDto productDtoUpdated = productService.updateProduct(prodDtoSaved);
        //the above line will launch the expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void validateUpdateProduct_prodnonexistent(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved);

        prodDtoSaved.setId(Long.valueOf(154878));
        prodDtoSaved.setProdCode("prodCodeprim1");
        assertEquals(catDtoSaved1.getId(), prodDtoSaved.getProdCatDto().getId());
        prodDtoSaved.setProdCatDto(catDtoSaved2);
        prodDtoSaved.setProdName("newNameProdName");
        prodDtoSaved.setProdDescription("new Description prodName");

        ProductDto productDtoUpdated = productService.updateProduct(prodDtoSaved);
        //the above line will launch the expected exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void validateUpdateProduct_EntityDuplicated(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(1, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved1);

        prodDtoSaved.setProdCode(prodDtoSaved1.getProdCode());
        assertEquals(catDtoSaved1.getId(), prodDtoSaved.getProdCatDto().getId());
        prodDtoSaved.setProdCatDto(catDtoSaved2);
        prodDtoSaved.setProdName("newNameProdName");
        prodDtoSaved.setProdDescription("new Description prodName");

        ProductDto productDtoUpdated = productService.updateProduct(prodDtoSaved);
        //The above line is supposed to launch the exception due to the fact that prodCode is empty
    }

    @Test(expected = EntityNotFoundException.class)
    public void validateUpdateProduct_ModifCategory(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);

        assertNotNull(prodDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved1, categoryService);
        assertNotNull(catDtoSaved2);

        prodDtoSaved.setProdCode("prodCodeprim1");
        assertEquals(catDtoSaved1.getId(), prodDtoSaved.getProdCatDto().getId());
        prodDtoSaved.setProdCatDto(catDtoSaved2);
        prodDtoSaved.setProdName("newNameProdName");
        prodDtoSaved.setProdDescription("new Description prodName");


        ProductDto productDtoUpdated = productService.updateProduct(prodDtoSaved);
        assertNotNull(productDtoUpdated);
        assertEquals(prodDtoSaved.getId(), productDtoUpdated.getId());
    }

    @Test
    public void validate_FindProductByProductCodeInPos(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);

        assertNotNull(prodDtoSaved);

        ProductDto productDtoFound = productService.findProductByProductCodeInPos(prodDtoSaved.getProdCode(),
                posDtoSaved.getId());
        assertNotNull(productDtoFound);
        assertEquals(prodDtoSaved.getProdAlias(), productDtoFound.getProdAlias());
    }

    @Test
    public void validate_deleteProductById(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved);

        ProductDto prodDtoSaved = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved, productService);

        assertNotNull(prodDtoSaved);

        assertTrue(productService.deleteProductById(prodDtoSaved.getId()));
    }

    @Test
    public void validate_findAllProductInPos(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);
        ProductDto prodDtoSaved2 = usedForTestForProduct.saveProduct(1, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved2);
        ProductDto prodDtoSaved3 = usedForTestForProduct.saveProduct(2, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved3);
        ProductDto prodDtoSaved4 = usedForTestForProduct.saveProduct(3, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved4);
        ProductDto prodDtoSaved5 = usedForTestForProduct.saveProduct(4, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved5);

        List<ProductDto> productDtoList = productService.findAllProductInPos(posDtoSaved.getId());
        assertNotNull(productDtoList);
        assertEquals(5, productDtoList.size());
    }

    @Test
    public void validate_findAllProductOfCategory(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);
        ProductDto prodDtoSaved2 = usedForTestForProduct.saveProduct(1, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved2);
        ProductDto prodDtoSaved3 = usedForTestForProduct.saveProduct(2, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved3);
        ProductDto prodDtoSaved4 = usedForTestForProduct.saveProduct(3, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved4);
        ProductDto prodDtoSaved5 = usedForTestForProduct.saveProduct(4, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved5);

        List<ProductDto> productDtoList = productService.findAllProductOfCategory(catDtoSaved2.getId());
        assertNotNull(productDtoList);
        assertEquals(2, productDtoList.size());
    }

    @Test
    public void validate_findPageofProductInPos(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);
        ProductDto prodDtoSaved2 = usedForTestForProduct.saveProduct(1, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved2);
        ProductDto prodDtoSaved3 = usedForTestForProduct.saveProduct(2, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved3);
        ProductDto prodDtoSaved4 = usedForTestForProduct.saveProduct(3, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved4);
        ProductDto prodDtoSaved5 = usedForTestForProduct.saveProduct(4, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved5);

        Page<ProductDto> productDtoPage = productService.findPageofProductInPos(posDtoSaved.getId(), 0, 2);
        assertNotNull(productDtoPage);
        assertEquals(3, productDtoPage.getTotalPages());
    }

    @Test
    public void validate_findPageOfProductOfCategory(){
        Long catParentId = null;

        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        CategoryDto catDtoSaved1 = usedForTestForProduct.saveCategory(0, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved1);

        CategoryDto catDtoSaved2 = usedForTestForProduct.saveCategory(1, catParentId, posDtoSaved, categoryService);
        assertNotNull(catDtoSaved2);

        ProductDto prodDtoSaved1 = usedForTestForProduct.saveProduct(0, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved1);
        ProductDto prodDtoSaved2 = usedForTestForProduct.saveProduct(1, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved2);
        ProductDto prodDtoSaved3 = usedForTestForProduct.saveProduct(2, posDtoSaved, catDtoSaved1, productService);
        assertNotNull(prodDtoSaved3);
        ProductDto prodDtoSaved4 = usedForTestForProduct.saveProduct(3, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved4);
        ProductDto prodDtoSaved5 = usedForTestForProduct.saveProduct(4, posDtoSaved, catDtoSaved2, productService);
        assertNotNull(prodDtoSaved5);

        Page<ProductDto> productDtoPage = productService.findPageOfProductOfCategory(catDtoSaved1.getId(), 0, 2);
        assertNotNull(productDtoPage);
        assertEquals(2, productDtoPage.getTotalPages());
    }


}