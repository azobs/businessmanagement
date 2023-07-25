//package com.c2psi.businessmanagement.services.contractsImpl.stock.product;
//
//import com.c2psi.businessmanagement.BusinessmanagementApplication;
//import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
//import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
//import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
//import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
//import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
//import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
//import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
//import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
//import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
//import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BusinessmanagementApplication.class)
//public class FormatServiceImplTest {
//    @Autowired
//    CategoryServiceImpl categoryService;
//    @Autowired
//    EnterpriseServiceImpl enterpriseService;
//    @Autowired
//    PointofsaleServiceImpl pointofsaleService;
//    @Autowired
//    UserBMServiceImpl userBMService;
//    @Autowired
//    PosCashAccountServiceImpl posCashAccountService;
//    @Autowired
//    CurrencyServiceImpl currencyService;
//    @Autowired
//    ProductServiceImpl productService;
//    @Autowired
//    FormatServiceImpl formatService;
//    @Autowired
//    UsedForTestForAll usedForTestForAll;
//
//    @Autowired
//    UsedForTestForProduct usedForTestForProduct;
//
//    @Test
//    public void validateSaveFormat(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved1);
//    }
//
//    @Test(expected = InvalidEntityException.class)
//    public void validateSaveFormat_NotValidated(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat_NonValid(0, posDtoSaved, formatService);
//        //the above line is supposed to launch the expected exception
//    }
//
//    @Test(expected = InvalidEntityException.class)
//    public void validateSaveFormat_PosNotValid(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//        posDtoSaved.setId(posDtoSaved.getId()+12545);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat_NonValid(0, posDtoSaved, formatService);
//        //the above line is supposed to launch the expected exception
//    }
//
//    @Test(expected = DuplicateEntityException.class)
//    public void validateSaveFormat_FormatDuplicated(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        //The above line is supposed to launch the expected exception
//    }
//
//    @Test
//    public void validateUpdateFormat(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved1);
//        //On va update le formatDtoSaved
//        formatDtoSaved.setFormatCapacity(BigDecimal.valueOf(0.65));
//        formatDtoSaved.setFormatName("Moyen");
//        FormatDto formatDtoUpdated = formatService.updateFormat(formatDtoSaved);
//        assertNotNull(formatDtoUpdated);
//        assertEquals("Moyen", formatDtoUpdated.getFormatName());
//        assertEquals(BigDecimal.valueOf(0.65), formatDtoUpdated.getFormatCapacity());
//    }
//
//    @Test(expected = DuplicateEntityException.class)
//    public void validateUpdateFormat_Duplicated(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved1);
//        //On va update le formatDtoSaved
//        formatDtoSaved.setFormatCapacity(BigDecimal.valueOf(0.65));
//        formatDtoSaved.setFormatName("Moyen");
//        FormatDto formatDtoUpdated = formatService.updateFormat(formatDtoSaved);
//        assertNotNull(formatDtoUpdated);
//        assertEquals("Moyen", formatDtoUpdated.getFormatName());
//        assertEquals(BigDecimal.valueOf(0.65), formatDtoUpdated.getFormatCapacity());
//
//        FormatDto formatDtoUpdated1 = formatService.updateFormat(formatDtoSaved);
//        assertNotNull(formatDtoUpdated1);
//
//        formatDtoSaved1.setFormatCapacity(BigDecimal.valueOf(0.65));
//        formatDtoSaved1.setFormatName("Moyen");
//        FormatDto formatDtoUpdated2 = formatService.updateFormat(formatDtoSaved1);
//        //The above line is supposed to launch the excepted exception
//    }
//
//    @Test
//    public void validateDeleteFormatById(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved1);
//
//        assertTrue(formatService.deleteFormatById(formatDtoSaved1.getId()));
//    }
//
//    @Test
//    public void validateFindAllFormatInPos(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved1);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved1);
//        FormatDto formatDtoSaved2 = usedForTestForProduct.saveFormat(2, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved2);
//
//        FormatDto formatDtoSaved3 = usedForTestForProduct.saveFormat(3, posDtoSaved1, formatService);
//        assertNotNull(formatDtoSaved3);
//        FormatDto formatDtoSaved4 = usedForTestForProduct.saveFormat(4, posDtoSaved1, formatService);
//        assertNotNull(formatDtoSaved4);
//
//        List<FormatDto> formatDtoList = formatService.findAllFormatInPos(posDtoSaved1.getId());
//        assertNotNull(formatDtoList);
//        assertEquals(2, formatDtoList.size());
//
//    }
//
//    @Test
//    public void validatefindPageFormatInPos(){
//        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved);
//        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
//                userBMService, currencyService);
//        assertNotNull(posDtoSaved1);
//
//        FormatDto formatDtoSaved = usedForTestForProduct.saveFormat(0, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved);
//        FormatDto formatDtoSaved1 = usedForTestForProduct.saveFormat(1, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved1);
//        FormatDto formatDtoSaved2 = usedForTestForProduct.saveFormat(2, posDtoSaved, formatService);
//        assertNotNull(formatDtoSaved2);
//
//        FormatDto formatDtoSaved3 = usedForTestForProduct.saveFormat(3, posDtoSaved1, formatService);
//        assertNotNull(formatDtoSaved3);
//        FormatDto formatDtoSaved4 = usedForTestForProduct.saveFormat(4, posDtoSaved1, formatService);
//        assertNotNull(formatDtoSaved4);
//
//        Page<FormatDto> formatDtoPage = formatService.findPageFormatInPos(posDtoSaved.getId(), 0, 2);
//        assertNotNull(formatDtoPage);
//        assertEquals(2, formatDtoPage.getTotalPages());
//
//    }
//}