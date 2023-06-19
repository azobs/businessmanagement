package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
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
public class UnitServiceImplTest {
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
    UnitServiceImpl unitService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveUnit(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveUnit_NotValid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit_NotValid(0, posDtoSaved, unitService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveUnit_PosNotExist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(Long.valueOf(1245789));

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveUnit_Duplicate(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateUnit(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        unitDtoSaved1.setUnitName("casier6");
        unitDtoSaved1.setUnitAbbreviation("1/2cs");
        UnitDto unitDtoUpdated = unitService.updateUnit(unitDtoSaved1);
        assertNotNull(unitDtoUpdated);
        assertEquals("casier6", unitDtoUpdated.getUnitName());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateUnit_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        unitDtoSaved1.setUnitName("casier6");
        unitDtoSaved1.setUnitAbbreviation("1/2cs");
        UnitDto unitDtoUpdated = unitService.updateUnit(unitDtoSaved1);
        assertNotNull(unitDtoUpdated);
        assertEquals("casier6", unitDtoUpdated.getUnitName());

        unitDtoSaved.setUnitName("casier6");
        unitDtoSaved.setUnitAbbreviation("1/2cs");
        UnitDto unitDtoUpdated1 = unitService.updateUnit(unitDtoSaved);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_FindListofUnitInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
        UnitDto unitDtoSaved2 = usedForTestForProduct.saveUnit(2, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved2);
        UnitDto unitDtoSaved3 = usedForTestForProduct.saveUnit(3, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved3);
        UnitDto unitDtoSaved4 = usedForTestForProduct.saveUnit(0, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved4);

        List<UnitDto> unitDtoList = unitService.findAllUnitInPos(posDtoSaved.getId());
        assertNotNull(unitDtoList);
        assertEquals(4, unitDtoList.size());
        List<UnitDto> unitDtoList1 = unitService.findAllUnitInPos(posDtoSaved1.getId());
        assertNotNull(unitDtoList1);
        assertEquals(1, unitDtoList1.size());
    }

    @Test
    public void validate_FindPageofUnitInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
        UnitDto unitDtoSaved2 = usedForTestForProduct.saveUnit(2, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved2);
        UnitDto unitDtoSaved3 = usedForTestForProduct.saveUnit(3, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved3);
        UnitDto unitDtoSaved4 = usedForTestForProduct.saveUnit(0, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved4);

        Page<UnitDto> unitDtoPage = unitService.findPageUnitInPos(posDtoSaved.getId(), 0, 3);
        assertNotNull(unitDtoPage);
        assertEquals(2, unitDtoPage.getTotalPages());
        Page<UnitDto> unitDtoPage1 = unitService.findPageUnitInPos(posDtoSaved1.getId(), 0, 3);
        assertNotNull(unitDtoPage1);
        assertEquals(1, unitDtoPage1.getTotalPages());
    }

    @Test
    public void validate_DeleteUnitById(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        assertTrue(unitService.deleteUnitById(unitDtoSaved.getId()));
    }

}