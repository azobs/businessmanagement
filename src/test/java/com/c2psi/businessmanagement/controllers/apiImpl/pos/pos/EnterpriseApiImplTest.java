package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
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
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
@AutoConfigureMockMvc
public class EnterpriseApiImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserBMService userBMService;
    @Autowired
    private EnterpriseService enterpriseService;

    @Test
    public void test_EnterpriseApi_saveEnterprise() throws Exception {
        /******************************************************
         * Enregistrement d'une entreprise
         */
        int num1 = 1;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoToSave(num1);
        ObjectMapper mapper = new ObjectMapper();
        String entDtoJson = mapper.writeValueAsString(entDtoSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/enterprise/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(entDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entName").value("EnterpriseName_"+num1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_updateEnterprise() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        /**********************************************
         * Update d'une entreprise
         */

        entDtoSaved.setEntAcronym("New Acronym");
        entDtoSaved.setEntName("NewentName");
        EnterpriseDto entDtoToUpdate = entDtoSaved;
        ObjectMapper mapper = new ObjectMapper();
        String entDtoJsonToUpdate = mapper.writeValueAsString(entDtoToUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "/enterprise/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(entDtoJsonToUpdate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entName").value("NewentName"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_setAdminEnterprise() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);
        UserBMDto userBMDtoSaved = PosDtoConstructor.getUserBMDtoSaved(num1+2, userBMService);

        entDtoSaved.setEntAdminDto(userBMDtoSaved);

        EnterpriseDto enterpriseDtoToUpdate = entDtoSaved;
        ObjectMapper mapper = new ObjectMapper();
        String entDtoJsonToUpdate = mapper.writeValueAsString(enterpriseDtoToUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "/enterprise/setAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(entDtoJsonToUpdate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(entDtoSaved.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_findEnterpriseById() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/enterprise/"+entDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(entDtoSaved.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_findEnterpriseByName() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/enterprise/name/"+entDtoSaved.getEntName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(entDtoSaved.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_findEnterpriseByNiu() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/enterprise/niu/"+entDtoSaved.getEntNiu())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(entDtoSaved.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_findAllEnterprise() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "/enterprise/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_deleteEnterpriseById() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/enterprise/delete/"+entDtoSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_deleteEnterpriseByName() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/enterprise/delete/name/"+entDtoSaved.getEntName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_EnterpriseApi_deleteEnterpriseByNiu() throws Exception {

        int num1 = 2;
        EnterpriseDto entDtoSaved = PosDtoConstructor.getEnterpriseDtoSaved(num1, enterpriseService, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "/enterprise/delete/niu/"+entDtoSaved.getEntNiu())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}