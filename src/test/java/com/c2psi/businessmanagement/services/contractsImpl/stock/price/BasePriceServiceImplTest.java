package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class BasePriceServiceImplTest {
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
    UsedForTestForAll usedForTestForAll;

    @Test
    public void validate_SaveBasePrice(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);
        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved1);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveBasePrice_NotValid(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);
        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice_NotValid(1, currencyDtoSaved, basePriceService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_SaveBasePrice_CurrencyNotFound(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);
        currencyDtoSaved.setId(Long.valueOf(154782));
        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved, basePriceService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateBasePrice(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);
        CurrencyDto currencyDtoSaved1 = usedForTestForAll.saveCurrency(1, currencyService);
        assertNotNull(currencyDtoSaved1);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);
        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved1);
        basePriceDtoSaved1.setBpCurrencyDto(currencyDtoSaved1);
        basePriceDtoSaved1.setBpDetailprice(BigDecimal.valueOf(8900));
        BasePriceDto basePriceDtoUpdated = basePriceService.updateBasePrice(basePriceDtoSaved1);
        assertNotNull(basePriceDtoUpdated);
        assertEquals(BigDecimal.valueOf(8900), basePriceDtoUpdated.getBpDetailprice());
    }

    @Test
    public void validate_DeleteBasePriceById(){
        CurrencyDto currencyDtoSaved = usedForTestForAll.saveCurrency(0, currencyService);
        assertNotNull(currencyDtoSaved);

        BasePriceDto basePriceDtoSaved = usedForTestForAll.saveBasePrice(0, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved);
        BasePriceDto basePriceDtoSaved1 = usedForTestForAll.saveBasePrice(1, currencyDtoSaved, basePriceService);
        assertNotNull(basePriceDtoSaved1);
        assertTrue(basePriceService.deleteBasePriceById(basePriceDtoSaved1.getId()));
    }
}