package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.controllers.apiImpl.pos.pos.PosDtoConstructor;
import com.c2psi.businessmanagement.controllers.apiImpl.stock.price.PriceDtoConstructor;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
import com.c2psi.businessmanagement.services.contracts.stock.product.CategoryService;
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

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
@AutoConfigureMockMvc
public class CategoryApiImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private UserBMService userBMService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private PointofsaleService posService;

    @Test
    public void test_CurrencyApi_saveCategory() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        PointofsaleDto pointofsaleDtoToSave = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        CategoryDto categoryDtoToSave = ProductDtoConstructor.getCategoryDtoToSave(num1, null, currencyDtoSaved,
                entDtoSaved, pointofsaleDtoToSave);

        ObjectMapper mapper = new ObjectMapper();
        String categoryDtoJson = mapper.writeValueAsString(categoryDtoToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catName").value("CatName"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_saveCategoryWithParent() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        PointofsaleDto pointofsaleDtoToSave = PosDtoConstructor.getPointofsaleDtoSaved(num1, currencyDtoSaved,
                entDtoSaved, posService);

        CategoryDto categoryDtoSavedParent = ProductDtoConstructor.getCategoryDtoSaved(num1, null, currencyDtoSaved,
                entDtoSaved, posService, categoryService);

        CategoryDto categoryDtoToSave = ProductDtoConstructor.getCategoryDtoToSave(num1+1, categoryDtoSavedParent, currencyDtoSaved,
                entDtoSaved, pointofsaleDtoToSave);

        ObjectMapper mapper = new ObjectMapper();
        String categoryDtoJson = mapper.writeValueAsString(categoryDtoToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catName").value("CatName"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_updateCategory() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        CategoryDto categoryDtoSaved = ProductDtoConstructor.getCategoryDtoSaved(num1, null, currencyDtoSaved,
                entDtoSaved, posService, categoryService);
        categoryDtoSaved.setCatName("setCatName");

        ObjectMapper mapper = new ObjectMapper();
        String categoryDtoJson = mapper.writeValueAsString(categoryDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catName").value("setCatName"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_CurrencyApi_findCategoryById() throws Exception {
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        String prefix = "Franc CFA";
        CurrencyDto currencyDtoSaved = PriceDtoConstructor.getCurrencyDtoSaved(prefix, num1, currencyService);

        CategoryDto categoryDtoSaved = ProductDtoConstructor.getCategoryDtoSaved(num1, null, currencyDtoSaved,
                entDtoSaved, posService, categoryService);
        categoryDtoSaved.setCatName("setCatName");

        ObjectMapper mapper = new ObjectMapper();
        String categoryDtoJson = mapper.writeValueAsString(categoryDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/category/"+categoryDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catCode").value("Code_"+num1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }




}