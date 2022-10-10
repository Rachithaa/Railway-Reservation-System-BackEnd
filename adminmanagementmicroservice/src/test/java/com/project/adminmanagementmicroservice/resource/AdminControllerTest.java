package com.project.adminmanagementmicroservice.resource;

import com.project.adminmanagementmicroservice.model.Admin;
import com.project.adminmanagementmicroservice.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;


    Admin admin1= new Admin(1,"Rachitha","Rachi@123Capg","rachi@gmail.com","9789909876","Rotarypura Puttur");

    @Before
    public void SetUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(adminController).build();
    }




    @Test
    public void viewAdmin_Success ()throws Exception
    {
        Mockito.when(adminService.viewAdmin(admin1.getAdminEmail())).thenReturn(Optional.ofNullable(admin1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/viewadmin/"+admin1.getAdminEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.adminName").value("Rachitha"));

    }

    @Test
    public void registerAdmin_Success() throws Exception{
        Admin a1=Admin.builder()
                .adminId(1)
                .adminName("Rachitha")
                .adminPassword("Rachi@123Capg")
                .adminEmail("rachi@gmail.com")
                .adminPhone("9004561234")
                .adminAddress("Rotarypura Puttur")
                .build();
        Mockito.when(adminService.registerAdmin(a1)).thenReturn(a1);

        String content= objectWriter.writeValueAsString(a1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.post("/admin/adminregister")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.adminName").value("Rachitha"));

    }

    @Test
    public  void updateAdmin_Success() throws Exception {

        Admin updatedadmin1=Admin.builder()
                .adminId(1)
                .adminName("Rachi")
                .adminPassword("Rachi@123Capg")
                .adminEmail("hello@gmail.com")
                .adminPhone("9004561234")
                .adminAddress("Rotarypura-Puttur")
                .build();
        Mockito.when(adminService.updateAdmin(updatedadmin1.getAdminId(),updatedadmin1)).thenReturn(updatedadmin1);

        String content= objectWriter.writeValueAsString(updatedadmin1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.put("/admin/updateadmin/"+updatedadmin1.getAdminId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.adminName").value("Rachi"));

    }

    @Test
    public void deleteAdmin_success()throws Exception {
      //   willDoNothing().given(adminService).deleteAdmin(admin1.getAdminId());

        // when -  action or the behaviour that we are going test
        //   ResultActions response = mockMvc.perform(delete("/admin/deleteadmin/" + admin1.getAdminId()));

        // then - verify the output
       // response.andExpect(status().isOk()).andDo(print());
        Mockito.when(adminService.deleteAdmin(admin1.getAdminId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/deleteadmin/"+admin1.getAdminId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


}