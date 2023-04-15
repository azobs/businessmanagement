package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.ProductServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.UnitServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class SpecialPriceServiceImplTest {
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
    BasePriceServiceImpl basePriceService;
    @Autowired
    SpecialPriceServiceImpl specialPriceService;
    @Autowired
    UsedForTestForAll usedForTestForAll;

    @Test
    public void validate_SaveSpecialPrice(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSpecialPrice_NotValid(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice_NotValid(0, basePriceDtoSaved, specialPriceService);
        //The above line must launch the expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_SaveSpecialPrice_MissingBaseprice(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);
        basePriceDtoSaved.setId(Long.valueOf(125487));

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved,
                specialPriceService);
        //The above line must launch the expected exception
    }

    @Test
    public void validate_UpdateSpecialPrice(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        specialPriceDtoSaved.setSpWholesaleprice(BigDecimal.valueOf(7400));
        SpecialPriceDto specialPriceDtoUpdated = specialPriceService.updateSpecialPrice(specialPriceDtoSaved);
        assertNotNull(specialPriceDtoUpdated);
        assertEquals(BigDecimal.valueOf(7400), specialPriceDtoUpdated.getSpWholesaleprice());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSpecialPrice_nullId(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        specialPriceDtoSaved.setSpWholesaleprice(BigDecimal.valueOf(7400));
        specialPriceDtoSaved.setId(null);
        SpecialPriceDto specialPriceDtoUpdated = specialPriceService.updateSpecialPrice(specialPriceDtoSaved);
        //the above line is supposed to launch the expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_UpdateSpecialPrice_SpecialpriceNotFound(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        specialPriceDtoSaved.setSpWholesaleprice(BigDecimal.valueOf(7400));
        specialPriceDtoSaved.setId(Long.valueOf(145278));
        SpecialPriceDto specialPriceDtoUpdated = specialPriceService.updateSpecialPrice(specialPriceDtoSaved);
        //the above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSpecialPrice_modifyBaseprice(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved1);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        specialPriceDtoSaved.setSpWholesaleprice(BigDecimal.valueOf(7400));
        specialPriceDtoSaved.setSpBasepriceDto(basePriceDtoSaved1);
        SpecialPriceDto specialPriceDtoUpdated = specialPriceService.updateSpecialPrice(specialPriceDtoSaved);
        //the above line is supposed to launch the expected exception
    }

    @Test
    public void validate_DeleteSpecialPriceById(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);

        assertTrue(specialPriceService.deleteSpecialPriceById(specialPriceDtoSaved.getId()));
    }

    @Test
    public void validate_FindListofSpecialPriceOf(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);

        SpecialPriceDto specialPriceDtoSaved = usedForTestForAll.saveSpecialPrice(0, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved);
        SpecialPriceDto specialPriceDtoSaved1 = usedForTestForAll.saveSpecialPrice(1, basePriceDtoSaved, specialPriceService);
        assertNotNull(specialPriceDtoSaved1);

        List<SpecialPriceDto> specialPriceDtoList = specialPriceService.findListofSpecialPriceOf(basePriceDtoSaved.getId());
        assertNotNull(specialPriceDtoList);
        assertEquals(2, specialPriceDtoList.size());
    }

}