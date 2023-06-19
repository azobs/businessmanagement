package com.c2psi.businessmanagement.controllers.apiImpl.stock.price;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyConversionService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
@AutoConfigureMockMvc
public class CurrencyApiImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyConversionService currconvService;

    @Test
    public void test_CurrencyApi_saveCurrency() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToSave = PriceDtoConstructor.getCurrencyDtoToSave("Franc CFA", num1);
        ObjectMapper mapper = new ObjectMapper();
        String currencyDtoJson = mapper.writeValueAsString(currencyDtoToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/currency/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currencyDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyShortname").value(prefix))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_updateCurrency() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToUpdate = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        currencyDtoToUpdate.setCurrencyName(prefix);
        ObjectMapper mapper = new ObjectMapper();
        String currencyDtoJson = mapper.writeValueAsString(currencyDtoToUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "/currency/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currencyDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyName").value(prefix))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findCurrencyById() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToUpdate = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currency/"+currencyDtoToUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyName").value(currencyDtoToUpdate.getCurrencyName()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findCurrencyByFullname() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToUpdate = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        String name = currencyDtoToUpdate.getCurrencyName();
        String shortName = currencyDtoToUpdate.getCurrencyShortname();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currency/fullname/"+name+"/"+shortName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyName").value(currencyDtoToUpdate.getCurrencyName()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findAllCurrency() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToUpdate1 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        CurrencyDto currencyDtoToUpdate2 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1+1, currencyService);
        CurrencyDto currencyDtoToUpdate3 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1+2, currencyService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currency/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findPageCurrency() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToUpdate1 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        CurrencyDto currencyDtoToUpdate2 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1+1, currencyService);
        CurrencyDto currencyDtoToUpdate3 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1+2, currencyService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currency/page/0/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(3))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_deleteCurrencyById() throws Exception {
        int num1 = 1;
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoToUpdate1 = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/currency/delete/id/"+currencyDtoToUpdate1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_saveCurrencyConversion() throws Exception {
        int num1 = 1;
        CurrencyDto to = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoToSave(convFactor, from, to);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/currencyconversion/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currconvDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversionFactor").value(convFactor))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_updateCurrencyConversion() throws Exception {
        int num1 = 1;
        CurrencyDto to = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to,
                currconvService);
        currconvDto.setConversionFactor(BigDecimal.valueOf(655));

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "/currencyconversion/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currconvDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversionFactor").value(BigDecimal.valueOf(655)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_convertTo() throws Exception {
        int num1 = 1;
        CurrencyDto to = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to,
                currconvService);

        BigDecimal amountToConvert = BigDecimal.valueOf(100).setScale(1, RoundingMode.UP);
        BigDecimal expected = BigDecimal.valueOf(650).multiply(amountToConvert).setScale(1, RoundingMode.UP);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currencyconversion/convertTo/"+amountToConvert+"/"+from.getId()+"/"+to.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(expected))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findCurrencyConversionById() throws Exception {
        int num1 = 1;
        CurrencyDto to = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currencyconversion/id/"+currconvDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversionFactor").value(convFactor))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findConversionRuleBetween() throws Exception {
        int num1 = 1;
        CurrencyDto to = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);
        System.err.println("convFactor == "+convFactor);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currencyconversion/link/"+from.getId()+"/"+to.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversionFactor").value(convFactor))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_listofConvertibleCurrencyWith() throws Exception {
        int num1 = 1;
        CurrencyDto to1 = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        CurrencyDto to2 = PriceDtoConstructor.getCurrencyDtoSaved("Dollard", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);
        BigDecimal convFactor1 = BigDecimal.valueOf(655).setScale(1, RoundingMode.UP);
        BigDecimal convFactor2 = BigDecimal.valueOf(0.9).setScale(1, RoundingMode.UP);
        System.err.println("convFactor == "+convFactor2);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to1,
                currconvService);
        CurrencyConversionDto currconvDto1 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor1, to2, to1,
                currconvService);
        CurrencyConversionDto currconvDto2 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor2, from, to2,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/currencyconversion/rules/"+from.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_deleteCurrencyConversionById() throws Exception {
        int num1 = 1;
        CurrencyDto to1 = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        CurrencyDto to2 = PriceDtoConstructor.getCurrencyDtoSaved("Dollard", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);
        BigDecimal convFactor1 = BigDecimal.valueOf(655).setScale(1, RoundingMode.UP);
        BigDecimal convFactor2 = BigDecimal.valueOf(0.9).setScale(1, RoundingMode.UP);
        System.err.println("convFactor == "+convFactor2);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to1,
                currconvService);
        CurrencyConversionDto currconvDto1 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor1, to2, to1,
                currconvService);
        CurrencyConversionDto currconvDto2 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor2, from, to2,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/currencyconversion/delete/id/"+currconvDto2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_deleteCurrencyConversionByCurrencyLink() throws Exception {
        int num1 = 1;
        CurrencyDto to1 = PriceDtoConstructor.getCurrencyDtoSaved("Franc CFA", num1, currencyService);
        CurrencyDto from = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        CurrencyDto to2 = PriceDtoConstructor.getCurrencyDtoSaved("Dollard", num1, currencyService);
        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);
        BigDecimal convFactor1 = BigDecimal.valueOf(655).setScale(1, RoundingMode.UP);
        BigDecimal convFactor2 = BigDecimal.valueOf(0.9).setScale(1, RoundingMode.UP);
        System.err.println("convFactor == "+convFactor2);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to1,
                currconvService);
        CurrencyConversionDto currconvDto1 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor1, to2, to1,
                currconvService);
        CurrencyConversionDto currconvDto2 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor2, from, to2,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String currconvDtoJson = mapper.writeValueAsString(currconvDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/currencyconversion/delete/link/"+from.getId()+"/"+to2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}