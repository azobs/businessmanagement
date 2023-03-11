package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
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
public class CurrencyServiceImplTest {

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

    @Test
    public void saveCurrency_Valid() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved);
        assertNotNull(currencyDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void saveCurrency_CurrencyNull() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(null);
        assertNotNull(currencyDtoSaved);
        assertNotNull(currencyDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void saveCurrency_NonValidated() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "");
        assertNotNull(currencyDtoSaved);
        assertNotNull(currencyDtoSaved.getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void saveCurrency_CurrencyDuplicated() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved1 = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved1);
        assertNotNull(currencyDtoSaved1.getId());
        CurrencyDto currencyDtoSaved2 = saveCurrency("Franc cfa", "F cfa");
        //The above line must generate the excepted exception
    }

    @Test
    public void updateCurrency_Valid() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        //on lance la mise a jour
        assertNotNull(currencyDtoSaved.getId());
        currencyDtoSaved.setCurrencyName("Le franc cfa");
        CurrencyDto currencyDtoUpdated = currencyService.updateCurrency(currencyDtoSaved);
        assertNotNull(currencyDtoUpdated);
        assertNotNull(currencyDtoUpdated.getId());
        assertEquals("Le franc cfa", currencyDtoUpdated.getCurrencyName());
    }

    @Test(expected = InvalidEntityException.class)
    public void updateCurrency_InValidCurrency() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        //on lance la mise a jour
        assertNotNull(currencyDtoSaved.getId());
        currencyDtoSaved.setCurrencyName("");
        CurrencyDto currencyDtoUpdated = currencyService.updateCurrency(currencyDtoSaved);
       //This line above must launch the exception
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateCurrency_CurrencyNotFound() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        //on lance la mise a jour
        assertNotNull(currencyDtoSaved.getId());
        currencyDtoSaved.setId(Long.valueOf(0));
        currencyDtoSaved.setCurrencyName("Le franc cfa");
        CurrencyDto currencyDtoUpdated = currencyService.updateCurrency(currencyDtoSaved);
        //This line above must launch the exception
    }

    @Test(expected = DuplicateEntityException.class)
    public void updateCurrency_CurrencyDuplicatedAfterUpdate() {
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        CurrencyDto currencyDtoSaved1 = saveCurrency("Dollard", "$");
        //on lance la mise a jour
        assertNotNull(currencyDtoSaved.getId());
        assertEquals("F cfa", currencyDtoSaved.getCurrencyShortname());
        assertEquals("$", currencyDtoSaved1.getCurrencyShortname());
        currencyDtoSaved.setCurrencyName("Dollard");
        currencyDtoSaved.setCurrencyShortname("$");
        CurrencyDto currencyDtoUpdated = currencyService.updateCurrency(currencyDtoSaved);
        //This line above must launch the exception
    }

    @Test
    public void listofExistingCurrency_Valid(){
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved1 = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved1);
        CurrencyDto currencyDtoSaved2 = saveCurrency("Dollard", "$");
        assertNotNull(currencyDtoSaved2);
        CurrencyDto currencyDtoSaved3 = saveCurrency("Yen", "Y");
        assertNotNull(currencyDtoSaved3);
        List<CurrencyDto> currencyDtoList = currencyService.listofExistingCurrency();
        assertNotNull(currencyDtoList);
        assertEquals(3, currencyDtoList.size());
    }

    @Test
    public void pageofExistingCurrency_Valid(){
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved1 = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved1);
        CurrencyDto currencyDtoSaved2 = saveCurrency("Dollard", "$");
        assertNotNull(currencyDtoSaved2);
        CurrencyDto currencyDtoSaved3 = saveCurrency("Yen", "Y");
        assertNotNull(currencyDtoSaved3);
        Page<CurrencyDto> currencyDtoPage = currencyService.pageofExistingCurrency(0,1);
        assertNotNull(currencyDtoPage);
        assertTrue(currencyDtoPage.stream().findFirst().isPresent());
        /*System.out.println("currencyDtoPage TotalPage---->  "+currencyDtoPage.getTotalPages());
        System.out.println("currencyDtoPage---->  "+currencyDtoPage.stream().findFirst().get());*/
        if(currencyDtoPage.stream().findFirst().isPresent()){
            assertEquals("Dollard",  currencyDtoPage.stream().findFirst().get().getCurrencyName());
        }
    }

    @Test
    public void deleteCurrencyById_Valid(){
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved);
        Boolean b = currencyService.deleteCurrencyById(currencyDtoSaved.getId());
        assertTrue(b);
        Boolean b1 = currencyService.isCurrencyExistWithId(currencyDtoSaved.getId());
        assertFalse(b1);
    }

    @Test(expected = NullArgumentException.class)
    public void deleteCurrencyById_NullArgument(){
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved);
        Boolean b = currencyService.deleteCurrencyById(null);
        //The above line will launch the exception
    }

    @Test
    public void deleteCurrencyByFullname_Valid(){
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved);
        Boolean b = currencyService.deleteCurrencyByFullname(currencyDtoSaved.getCurrencyName(),
                currencyDtoSaved.getCurrencyShortname());
        assertTrue(b);
        Boolean b1 = currencyService.isCurrencyExistWithFullname(currencyDtoSaved.getCurrencyName(),
                currencyDtoSaved.getCurrencyShortname());
        assertFalse(b1);
    }

    @Test(expected = NullArgumentException.class)
    public void deleteCurrencyByFullname_NullName(){
        assertNotNull(currencyService);
        CurrencyDto currencyDtoSaved = saveCurrency("Franc cfa", "F cfa");
        assertNotNull(currencyDtoSaved);
        Boolean b = currencyService.deleteCurrencyByFullname(null,
                currencyDtoSaved.getCurrencyShortname());
        //The above line will launch the exception
    }


}