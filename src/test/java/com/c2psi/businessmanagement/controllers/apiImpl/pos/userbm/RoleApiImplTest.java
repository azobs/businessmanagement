package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
@AutoConfigureMockMvc
public class RoleApiImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private UserBMService userBMService;

    @Test
    public void test_saveRole() throws Exception{
        int num1 = 1;
        Assert.assertNotNull(this.enterpriseService);
        Assert.assertNotNull(userBMService);
        RoleDto roleDtoToSave = UserBMDtoConstructor.getRoleDtoJsonToSave(num1, enterpriseService, userBMService);

        ObjectMapper mapper = new ObjectMapper();
        String roleDtoJson = mapper.writeValueAsString(roleDtoToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "/role/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleName").value("Manager"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}