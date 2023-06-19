package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.controllers.apiImpl.stock.price.PriceDtoConstructor;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
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
public class PointofsaleApiImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserBMService userBMService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyConversionService currconvService;
    @Autowired
    private PointofsaleService posService;

    @Test
    public void test_PointofsaleApi_savePointofsale() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        PointofsaleDto pointofsaleDtoToSave = PosDtoConstructor.getPointofsaleDtoToSave(num1, currencyDtoSaved,
                entDtoSaved);

        ObjectMapper mapper = new ObjectMapper();
        String pointofsaleDtoJson = mapper.writeValueAsString(pointofsaleDtoToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/pos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pointofsaleDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.posName").value("Pos_"+num1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_PointofsaleApi_updatePointofsale() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);
        pointofsaleDtoSaved.setPosAcronym("NewAcronym");
        pointofsaleDtoSaved.setPosName("NewPosName");

        ObjectMapper mapper = new ObjectMapper();
        String pointofsaleDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "/pos/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pointofsaleDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.posName").value("NewPosName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.posAcronym").value("NewAcronym"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void test_CurrencyApi_findPointofsaleById() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        ObjectMapper mapper = new ObjectMapper();
        String posDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/pos/"+pointofsaleDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.posName").value("Pos_"+num1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_deletePosById() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        ObjectMapper mapper = new ObjectMapper();
        String posDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/pos/delete/id/"+pointofsaleDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_deletePosInEnterpriseByName() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        ObjectMapper mapper = new ObjectMapper();
        String posDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/pos/delete/name/"+pointofsaleDtoSaved.getPosName()+"/"+pointofsaleDtoSaved.getPosEnterpriseDto().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_listofConvertibleCurrency() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        CurrencyDto from = pointofsaleDtoSaved.getPosCurrencyDto();
        CurrencyDto to1 = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        CurrencyDto to2 = PriceDtoConstructor.getCurrencyDtoSaved("Dollard", num1, currencyService);

        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);
        BigDecimal convFactor1 = BigDecimal.valueOf(655).setScale(1, RoundingMode.UP);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to1,
                currconvService);
        CurrencyConversionDto currconvDto2 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor1, from, to2,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String posDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/pos/currencies/"+pointofsaleDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_getDefaultCurrency() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        CurrencyDto from = pointofsaleDtoSaved.getPosCurrencyDto();
        CurrencyDto to1 = PriceDtoConstructor.getCurrencyDtoSaved("Euro", num1, currencyService);
        CurrencyDto to2 = PriceDtoConstructor.getCurrencyDtoSaved("Dollard", num1, currencyService);

        BigDecimal convFactor = BigDecimal.valueOf(650).setScale(1, RoundingMode.UP);
        BigDecimal convFactor1 = BigDecimal.valueOf(655).setScale(1, RoundingMode.UP);

        CurrencyConversionDto currconvDto = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor, from, to1,
                currconvService);
        CurrencyConversionDto currconvDto2 = PriceDtoConstructor.getCurrencyConversionDtoSaved(convFactor1, from, to2,
                currconvService);

        ObjectMapper mapper = new ObjectMapper();
        String posDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/pos/currency/"+pointofsaleDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyName").value(prefix+"_"+num1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void test_CurrencyApi_getTotalCashofPos() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);
        PointofsaleDto pointofsaleDtoSaved = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        ObjectMapper mapper = new ObjectMapper();
        String posDtoJson = mapper.writeValueAsString(pointofsaleDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/pos/totalcash/"+pointofsaleDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(BigDecimal.valueOf(0).setScale(1,RoundingMode.UP)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }




}