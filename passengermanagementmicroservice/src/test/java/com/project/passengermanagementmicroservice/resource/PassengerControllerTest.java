package com.project.passengermanagementmicroservice.resource;


import com.project.passengermanagementmicroservice.model.Passenger;
import com.project.passengermanagementmicroservice.service.PassengerService;
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

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PassengerControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerController passengerController;

    Passenger passenger= new Passenger(1,"Rachitha","Rachi@123Capg","rachi@gmail.com","9789909876","Rotarypura Puttur");


    @Before
    public void SetUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(passengerController).build();
    }

    @Test
    public void registerPassenger_Success() throws Exception
    {
        Passenger passenger1=Passenger.builder()
                .passengerId(1)
                .passengerName("Rachitha")
                .passengerPassword("Rachi@123Capg")
                .passengerEmail("rachi@gmail.com")
                .passengerPhone("9004561234")
                .passengerAddress("Rotarypura Puttur")
                .build();
        Mockito.when(passengerService.registerPassenger(passenger1)).thenReturn(passenger1);

        String content= objectWriter.writeValueAsString(passenger1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.post("/passenger/passengerregister")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.passengerName").value("Rachitha"));

    }

    @Test
    public void viewPassenger_Success() throws Exception {
        Mockito.when(passengerService.viewPassenger(passenger.getPassengerEmail())).thenReturn(Optional.ofNullable(passenger));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/passenger/viewpassenger/"+passenger.getPassengerId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.passengerName").value("Rachitha"));
    }


    @Test
    public void deletePassenger_Success() throws Exception {
        Mockito.when(passengerService.deletePassenger(passenger.getPassengerId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/passenger/deletepassenger/"+passenger.getPassengerId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updatePassenger_Success() throws Exception {
        Passenger updatedpassenger=Passenger.builder()
                .passengerName("Rachitha")
                .passengerPassword("Rachi@123Capg")
                .passengerEmail("rachi@gmail.com")
                .passengerPhone("9004561234")
                .passengerAddress("Rotarypura Puttur")
                .build();
        Mockito.when(passengerService.updatePassenger(updatedpassenger.getPassengerId(),updatedpassenger)).thenReturn(updatedpassenger);

        String content= objectWriter.writeValueAsString(updatedpassenger);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.put("/passenger/updatepassenger/"+updatedpassenger.getPassengerId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.passengerName").value("Rachitha"));
    }
}