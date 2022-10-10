package com.project.trainmanagementmicroservice;

import com.project.trainmanagementmicroservice.model.Route;
import com.project.trainmanagementmicroservice.model.Train;
import com.project.trainmanagementmicroservice.model.TrainClasses;
import com.project.trainmanagementmicroservice.resource.TrainController;
import com.project.trainmanagementmicroservice.service.TrainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class TrainControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private TrainService trainService;

    @InjectMocks
    private TrainController trainController;

    Route route1= new Route(1,"Puttur-JN","00:00:00","12:15:00",104);
    Route route2= new Route(2,"Mangalore-JN","13:00:00","13:30:00",56);
    Route route3= new Route(3,"Udupi-JN","13:00:00","00:00:00", 0);

    TrainClasses trainClasses1=new TrainClasses("AC Coach",100.76,40);
    TrainClasses trainClasses2=new TrainClasses("General 2A",100.87,40);
    TrainClasses trainClasses3=new TrainClasses("Ladies 2B",100.78,40);

    List<Route> routes1= new ArrayList(Arrays.asList(route1,route2,route3));
    List<Route> routes2= new ArrayList(Arrays.asList(route1,route2));

    List<TrainClasses> trainClassesList1=new ArrayList(Arrays.asList(trainClasses1,trainClasses2));
    List<TrainClasses> trainClassesList2=new ArrayList(Arrays.asList(trainClasses2,trainClasses3));

    //String[] runningdays1={"Mon", "Tue", "Sat", "Sun"};

    //String[] runningdays2={"Mon", "Wed", "Fri"};

    Train train1= new Train(12678,"Udupi Express","Puttur-JN","Udupi-JN",2,routes1, new String[]{"Mon", "Tue", "Sat", "Sun"},80,trainClassesList1);
    Train train2= new Train(12878,"Udupi Express","Mangalore-JN","Udupi-JN",2,routes2, new String[]{"Mon", "Wed", "Fri"},80,trainClassesList2);


    @Before
    public void SetUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(trainController).build();
    }


    @Test
   public void listAllTrain_Success() throws Exception{
        List<Train> records= new ArrayList(Arrays.asList(train1,train2));
        when(trainService.listAllTrain()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/trains/alllist")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].source").value("Puttur-JN"))
                .andExpect(jsonPath("$[1].source").value("Mangalore-JN"));
    }

    @Test
    public void listTrain_Success ()throws Exception
    {
        when(trainService.listTrain(train2.getTrainId())).thenReturn(Optional.ofNullable(train2));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trains/listTrain/"+train2.getTrainId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$",notNullValue()))
                        .andExpect(jsonPath("$.source").value("Mangalore-JN"));

    }

    @Test
    public void addTrain_Success() throws Exception{
        Train t1=Train.builder()
                .trainId(12674)
                .trainName("Belgavi Express")
                .source("Puttur JN")
                .destination("Belgavi JN")
                .pricePerKms(2)
                .route(routes1)
                .daysOfRunning(new String[]{"Mon", "Wed", "Fri"})
                .totalNumOfSeats(80)
                .trainClasses(trainClassesList1)
                .build();
        when(trainService.addTrain(t1)).thenReturn(t1);

        String content= objectWriter.writeValueAsString(t1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.post("/trains/addTrain")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.source").value("Puttur JN"));

    }

   @Test
    public  void updateTrain() throws Exception {

        Train updatedt1=Train.builder()
                .trainId(12674)
                .trainName("Batkal Express")
                .source("Puttur JN")
                .destination("Batkal JN")
                .pricePerKms(2)
                .route(routes1)
                .daysOfRunning(new String[]{"Mon", "Wed", "Fri"})
                .totalNumOfSeats(80)
                .trainClasses(trainClassesList1)
                .build();
        when(trainService.updateTrain(updatedt1.getTrainId(),updatedt1)).thenReturn(updatedt1);

       String content= objectWriter.writeValueAsString(updatedt1);
       MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.put("/trains/update/"+updatedt1.getTrainId())
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(content);

       mockMvc.perform(mockHttpServletRequestBuilder)
               .andExpect(status().isOk())
               .andExpect(jsonPath("$",notNullValue()))
               .andExpect(jsonPath("$.source").value("Puttur JN"));

    }

    @Test
    public void deleteTrain_success()throws Exception {
        /*   mockMvc.perform(MockMvcRequestBuilders
                        .delete("/trains/delete/"+train2.getTrainId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
*/
        /*  willDoNothing().given(trainService).deleteTrain(train2.getTrainId());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/trains/delete/" + train2.getTrainId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());*/
        when(trainService.deleteTrain(train2.getTrainId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/trains/delete/"+train2.getTrainId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void searchTrain_success()throws Exception {
        List<Train> records= new ArrayList(Arrays.asList(train1));
        when(trainService.searchTrain("Mangalore-JN","Udupi-JN","05-09-2022")).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trains/search/Mangalore-JN/Udupi-JN/05-09-2022")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].source").value("Puttur-JN"));
    }
}