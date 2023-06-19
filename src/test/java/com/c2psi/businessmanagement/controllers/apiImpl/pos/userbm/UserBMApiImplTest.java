package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
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


import java.text.SimpleDateFormat;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;
import static org.hamcrest.Matchers.hasSize;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
@AutoConfigureMockMvc
public class UserBMApiImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserBMService userBMService;

    @Test
    public void test_UserBMApi_saveUserBM() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoToSave = UserBMDtoConstructor.getUserBMDtoToSave("AdminBM", num1, null);
        ObjectMapper mapper = new ObjectMapper();
        String userBMDtoJson = mapper.writeValueAsString(userBMDtoToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(APP_ROOT + "userbm/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userBMDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmAddressDto.email")
                        .value("AdminBM"+"_"+num1+"@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmCni")
                        .value("14253626_" + num1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_updateUserBM() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        userBMDtoSaved.getBmAddressDto().setEmail("Test@gmail.com");
        userBMDtoSaved.setBmName("NewName");
        userBMDtoSaved.setBmCni("107235260");
        UserBMDto userBMDtoToUpdate = userBMDtoSaved;
        ObjectMapper mapper = new ObjectMapper();
        String userBMDtoJson = mapper.writeValueAsString(userBMDtoToUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "userbm/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userBMDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmAddressDto.email")
                        .value("Test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmName")
                        .value("NewName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmCni")
                        .value("107235260"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_switchUserBMState() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        userBMDtoSaved.setBmState(UserBMState.Deactivated);
        UserBMDto userBMDtoToUpdate = userBMDtoSaved;
        ObjectMapper mapper = new ObjectMapper();
        String userBMDtoJson = mapper.writeValueAsString(userBMDtoToUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "userbm/switchUserBMState")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userBMDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmState")
                        .value("Deactivated"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_resetPassword() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        userBMDtoSaved.setBmPassword("newPassword");
        userBMDtoSaved.setBmRepassword("newPassword");
        UserBMDto userBMDtoToUpdate = userBMDtoSaved;
        ObjectMapper mapper = new ObjectMapper();
        String userBMDtoJson = mapper.writeValueAsString(userBMDtoToUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(APP_ROOT + "userbm/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userBMDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_findUserBMByLogin() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        String login = userBMDtoSaved.getBmLogin();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "userbm/login/"+login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmLogin")
                        .value(login))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_findUserBMByCni() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        String cni = userBMDtoSaved.getBmCni();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "userbm/cni/"+cni)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmCni")
                        .value(cni))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_findUserBMByEmail() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        String email = userBMDtoSaved.getBmAddressDto().getEmail();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "userbm/email/"+email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bmAddressDto.email")
                        .value(email))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_findUserBMByFullNameAndDob() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int num1 = 1;
            UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
            String name = userBMDtoSaved.getBmName();
            String date = sdf.format(userBMDtoSaved.getBmDob());
            String surname = userBMDtoSaved.getBmSurname();
            mockMvc.perform(MockMvcRequestBuilders
                            .get(APP_ROOT + "userbm/fullname/" + name + "/" + surname + "/" + date)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.bmName")
                            .value(userBMDtoSaved.getBmName()))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }
        catch (Exception e){
        }
    }

    @Test
    public void test_UserBMApi_findUserBMById() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        Long bmId = userBMDtoSaved.getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "userbm/id/"+bmId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(bmId))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_findAllByUserBMType() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        int num2=2;
        UserBMDto userBMDtoSaved1 = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num2, null, userBMService);
        int num3=3;
        UserBMDto userBMDtoSaved2 = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num3, null, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "userbm/type/AdminBM")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_findAll() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);
        int num2=2;
        UserBMDto userBMDtoSaved1 = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num2, null, userBMService);
        int num3=3;
        UserBMDto userBMDtoSaved2 = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num3, null, userBMService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(APP_ROOT + "userbm/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_UserBMApi_deleteUserBMByLogin() throws Exception{
        int num1 = 1;
        UserBMDto userBMDtoSaved = UserBMDtoConstructor.getUserBMDtoSaved("AdminBM", num1, null, userBMService);

        String login = userBMDtoSaved.getBmLogin();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(APP_ROOT + "userbm/delete/login/"+login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }



}