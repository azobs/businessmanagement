package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class UnitConversionServiceImplTest {
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
    UnitConversionServiceImpl unitConversionService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveUnitConversion(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveUnitConversion_NotValid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion_NotValid(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveUnitConversion_UnitNotExist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
        unitDtoSaved1.setId(Long.valueOf(145236));

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveUnitConversion_DifferentPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved1, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveUnitConversion_Duplication(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);
        UnitConversionDto unitConversionDtoSaved1 = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved1, unitDtoSaved,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved1);
        UnitConversionDto unitConversionDtoSaved2 = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved1, unitDtoSaved,
                unitConversionService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveUnitConversion_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved,
                unitConversionService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateUnitConversion(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);

        unitConversionDtoSaved.setConversionFactor(Double.valueOf(12));
        UnitConversionDto unitConversionDtoUpdated = unitConversionService.updateUnitConversion(unitConversionDtoSaved);
        assertNotNull(unitConversionDtoUpdated);
        assertEquals(Double.valueOf(12), unitConversionDtoUpdated.getConversionFactor());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateUnitConversion_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
        UnitDto unitDtoSaved2 = usedForTestForProduct.saveUnit(2, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved2);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);

        unitConversionDtoSaved.setConversionFactor(Double.valueOf(12));
        unitConversionDtoSaved.setUnitSourceDto(unitDtoSaved2);

        UnitConversionDto unitConversionDtoUpdated = unitConversionService.updateUnitConversion(unitConversionDtoSaved);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_ConvertTo(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);

        unitConversionDtoSaved.setConversionFactor(Double.valueOf(10));
        UnitConversionDto unitConversionDtoUpdated = unitConversionService.updateUnitConversion(unitConversionDtoSaved);
        assertNotNull(unitConversionDtoUpdated);
        assertEquals(Double.valueOf(10), unitConversionDtoUpdated.getConversionFactor());

        Double val = unitConversionService.convertTo(Double.valueOf(10), unitDtoSaved.getId(), unitDtoSaved1.getId());
        assertEquals(Double.valueOf(100), val);
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_ConvertTo_ConversionRuleNotFound(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
        UnitDto unitDtoSaved2 = usedForTestForProduct.saveUnit(2, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved2);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);

        unitConversionDtoSaved.setConversionFactor(Double.valueOf(10));
        UnitConversionDto unitConversionDtoUpdated = unitConversionService.updateUnitConversion(unitConversionDtoSaved);
        assertNotNull(unitConversionDtoUpdated);
        assertEquals(Double.valueOf(10), unitConversionDtoUpdated.getConversionFactor());

        Double val = unitConversionService.convertTo(Double.valueOf(10), unitDtoSaved.getId(), unitDtoSaved2.getId());
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_listofConvertibleUnitWith(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);
        UnitDto unitDtoSaved2 = usedForTestForProduct.saveUnit(2, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved2);
        UnitDto unitDtoSaved3 = usedForTestForProduct.saveUnit(3, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved3);
        UnitDto unitDtoSaved4 = usedForTestForProduct.saveUnit(4, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved4);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);
        UnitConversionDto unitConversionDtoSaved1 = usedForTestForProduct.saveUnitConversion(1, unitDtoSaved, unitDtoSaved2,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved1);
        UnitConversionDto unitConversionDtoSaved2 = usedForTestForProduct.saveUnitConversion(2, unitDtoSaved, unitDtoSaved3,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved2);
        UnitConversionDto unitConversionDtoSaved3 = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved3, unitDtoSaved4,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved3);
        List<UnitDto> unitDtoList = unitConversionService.listofConvertibleUnitWith(unitDtoSaved.getId());
        assertNotNull(unitDtoList);
        assertEquals(3, unitDtoList.size());
        List<UnitDto> unitDtoList1 = unitConversionService.listofConvertibleUnitWith(unitDtoSaved3.getId());
        assertNotNull(unitDtoList1);
        assertEquals(1, unitDtoList1.size());
    }

    @Test
    public void validate_DeleteUnitConversionById(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UnitDto unitDtoSaved = usedForTestForProduct.saveUnit(0, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved);
        UnitDto unitDtoSaved1 = usedForTestForProduct.saveUnit(1, posDtoSaved, unitService);
        assertNotNull(unitDtoSaved1);

        UnitConversionDto unitConversionDtoSaved = usedForTestForProduct.saveUnitConversion(0, unitDtoSaved, unitDtoSaved1,
                unitConversionService);
        assertNotNull(unitConversionDtoSaved);

        assertTrue(unitConversionService.deleteUnitConversionById(unitConversionDtoSaved.getId()));
    }
}