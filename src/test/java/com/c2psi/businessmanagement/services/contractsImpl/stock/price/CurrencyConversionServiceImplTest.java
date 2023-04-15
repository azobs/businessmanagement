package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class CurrencyConversionServiceImplTest {
    @Autowired
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    CurrencyServiceImpl currencyService;

    public CurrencyDto saveCurrency(String name, String shortName){
        CurrencyDto currencyDtoToSaved = CurrencyDto.builder()
                .currencyShortname(shortName)
                .currencyName(name)
                .build();
        assertNull(currencyDtoToSaved.getId());
        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(currencyDtoToSaved);
        return currencyDtoSaved;
    }

    public CurrencyConversionDto saveCurrencyConversion(double facteur, CurrencyDto currencyDtoSource,
                                                        CurrencyDto currencyDtoDestination){
        CurrencyConversionDto currencyConversionDtoToSaved = CurrencyConversionDto.builder()
                .conversionFactor(BigDecimal.valueOf(facteur))
                .currencySourceDto(currencyDtoSource)
                .currencyDestinationDto(currencyDtoDestination)
                .build();
        CurrencyConversionDto currencyConversionDtoSaved = currencyConversionService.saveCurrencyConversion(currencyConversionDtoToSaved);
        return currencyConversionDtoSaved;
    }

    @Test
    public void saveCurrencyConversion_Valid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        double facteur = 655.0;

        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);

        assertNotNull(currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoSaved.getId());
    }

    @Test
    public void updateCurrencyConversion_Valid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        double facteur = 655.0;

        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);

        assertNotNull(currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoSaved.getId());

        currencyConversionDtoSaved.setConversionFactor(BigDecimal.valueOf(650));
        CurrencyConversionDto currencyConversionDtoUpdated = currencyConversionService.updateCurrencyConversion(
                currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoUpdated);
        assertEquals(BigDecimal.valueOf(650), currencyConversionDtoUpdated.getConversionFactor());
    }

    @Test(expected = InvalidEntityException.class)
    public void updateCurrencyConversion_NotValid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        CurrencyDto currencyDtoSourceSaved1 = saveCurrency("Yen", "Y");

        double facteur = 655.0;

        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);

        assertNotNull(currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoSaved.getId());
        currencyConversionDtoSaved.setConversionFactor(BigDecimal.valueOf(650));
        currencyConversionDtoSaved.setCurrencySourceDto(currencyDtoSourceSaved1);
        CurrencyConversionDto currencyConversionDtoUpdated = currencyConversionService.updateCurrencyConversion(
                currencyConversionDtoSaved);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void saveCurrencyConversion_InValid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        double facteur = 655.0;
        currencyDtoSourceSaved.setId(Long.valueOf(125478));
        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);

        assertNotNull(currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void saveCurrencyConversion_NullFacteur(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        double facteur = 655.0;

        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(0, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);
        //The above line must launch the exception expected
    }

    @Test(expected = DuplicateEntityException.class)
    public void saveCurrencyConversion_DuplicatedRule(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        double facteur1 = 655.0;
        double facteur2 = 0.655;
        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(facteur1, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);
        assertNotNull(currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoSaved.getId());
        CurrencyConversionDto currencyConversionDtoSaved1 = saveCurrencyConversion(facteur2, currencyDtoDestinationSaved,
                currencyDtoSourceSaved);
        //The above line must launch the exception expected
    }

    @Test
    public void convertTo_Valid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoDestinationSaved.getId());
        double facteur = 655.0;
        CurrencyConversionDto currencyConversionDtoSaved = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);
        assertNotNull(currencyConversionDtoSaved);
        assertNotNull(currencyConversionDtoSaved.getId());
        BigDecimal amount = BigDecimal.valueOf(1);
        BigDecimal amountConvert = currencyConversionService.convertTo(amount, currencyDtoSourceSaved.getId(),
                currencyDtoDestinationSaved.getId());
        assertNotNull(amountConvert);
        assertEquals(Double.valueOf("655.0"), Optional.of(amountConvert.doubleValue()).get());
    }

    @Test(expected = NullArgumentException.class)
    public void convertTo_NullArgument(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoDestinationSaved.getId());
        BigDecimal amount = BigDecimal.valueOf(1);
        BigDecimal amountConvert = currencyConversionService.convertTo(null, currencyDtoSourceSaved.getId(),
                currencyDtoDestinationSaved.getId());
        //The above line must launch the exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void convertTo_RuleNotFound(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoDestinationSaved.getId());
        BigDecimal amount = BigDecimal.valueOf(1);
        BigDecimal amountConvert = currencyConversionService.convertTo(amount, currencyDtoSourceSaved.getId(),
                currencyDtoDestinationSaved.getId());
        //The above line must launch the exception
    }

    @Test
    public void listofConvertibleCurrencyWith_Valid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        CurrencyDto currencyDtoDestinationSaved1 = saveCurrency("Euro", "E");
        assertNotNull(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoDestinationSaved.getId());
        double facteur = 655.0;
        double facteur1 = 1.06;
        CurrencyConversionDto currencyConversionDtoSaved1 = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);
        CurrencyConversionDto currencyConversionDtoSaved2 = saveCurrencyConversion(facteur1, currencyDtoSourceSaved,
                currencyDtoDestinationSaved1);
        assertNotNull(currencyConversionDtoSaved1);
        assertNotNull(currencyConversionDtoSaved2);

        List<CurrencyDto> currencyDtoList = currencyConversionService.listofConvertibleCurrencyWith(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoList);
        assertEquals(2, currencyDtoList.size());
        assertTrue(currencyDtoList.contains(currencyDtoDestinationSaved));
    }

    @Test
    public void deleteCurrencyConversionById_Valid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        CurrencyDto currencyDtoDestinationSaved1 = saveCurrency("Euro", "E");
        assertNotNull(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoDestinationSaved.getId());
        double facteur = 655.0;
        double facteur1 = 1.06;
        CurrencyConversionDto currencyConversionDtoSaved1 = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);
        CurrencyConversionDto currencyConversionDtoSaved2 = saveCurrencyConversion(facteur1, currencyDtoSourceSaved,
                currencyDtoDestinationSaved1);
        assertNotNull(currencyConversionDtoSaved1);
        assertNotNull(currencyConversionDtoSaved2);

        Boolean b = currencyConversionService.deleteCurrencyConversionById(currencyConversionDtoSaved1.getId());
        assertTrue(b);

        b = currencyConversionService.isCurrencyConversionExist(currencyDtoSourceSaved.getId(),
                currencyDtoDestinationSaved.getId());
        assertFalse(b);
    }

    @Test
    public void deleteCurrencyConversionByCurrencyLink_Valid(){
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);
        CurrencyDto currencyDtoSourceSaved = saveCurrency("Dollard", "$");
        CurrencyDto currencyDtoDestinationSaved = saveCurrency("Franc cfa", "F cfa");
        CurrencyDto currencyDtoDestinationSaved1 = saveCurrency("Euro", "E");
        assertNotNull(currencyDtoSourceSaved.getId());
        assertNotNull(currencyDtoDestinationSaved.getId());
        double facteur = 655.0;
        double facteur1 = 1.06;
        CurrencyConversionDto currencyConversionDtoSaved1 = saveCurrencyConversion(facteur, currencyDtoSourceSaved,
                currencyDtoDestinationSaved);
        CurrencyConversionDto currencyConversionDtoSaved2 = saveCurrencyConversion(facteur1, currencyDtoSourceSaved,
                currencyDtoDestinationSaved1);
        assertNotNull(currencyConversionDtoSaved1);
        assertNotNull(currencyConversionDtoSaved2);

        Boolean b = currencyConversionService.deleteCurrencyConversionByCurrencyLink(currencyDtoSourceSaved.getId(),
                currencyDtoDestinationSaved.getId());
        assertTrue(b);

        b = currencyConversionService.isCurrencyConversionExist(currencyDtoSourceSaved.getId(),
                currencyDtoDestinationSaved.getId());
        assertFalse(b);
    }

}