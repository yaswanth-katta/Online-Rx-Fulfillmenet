package com.cts.rxmodule.controller;

import com.cts.rxmodule.models.ReviewOnDrugs;
import com.cts.rxmodule.models.Rx;
import com.cts.rxmodule.models.RxLocation;
import com.cts.rxmodule.service.RxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RxController.class)
class RxControllerTest {
    @Autowired
    private RxController rxController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RxService rxService;

    @Test
    void testAddRxDetails() throws Exception {
        List<RxLocation> rxLocationList=new ArrayList<>();
        Rx actualRx = new Rx("42", "Rx Name", "2020-03-01", "2020-03-01", rxLocationList);

       Mockito.when(rxService.addRxDetails(actualRx)).thenReturn(actualRx);

        mockMvc.perform(post("/addRxDetails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actualRx)))
                .andExpect(status().isOk());

//        verify(rxService,times(1)).addRxDetails(actualRx);

    }

    @Test
    void testSearchById() throws Exception {
        List<RxLocation> rxLocationList=new ArrayList<>();
        Rx actualRx = new Rx("42", "Rx Name", "2020-03-01", "2020-03-01", rxLocationList);
        List<Rx> list=new ArrayList<>();
        list.add(actualRx);
        Mockito.when(rxService.searchById("42")).thenReturn(list);

        mockMvc.perform(get("/searchRxById/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rxId").value("42"))
                .andExpect(jsonPath("$[0].rxName").value("Rx Name"));

        verify(rxService,times(1)).searchById("42");


    }

    @Test
    void testGetDispatchableRxStock() throws Exception {
        RxLocation location=new RxLocation(1L,"HYD123","hyderabad",3L,1000L,3L);
        List<RxLocation> rxLocationList=new ArrayList<>();
        rxLocationList.add(location);

        Rx actualRx = new Rx("42", "Rx Name", "2020-03-01", "2020-03-01", rxLocationList);

        Mockito.when(rxService.getDispatchableRxStock("42","hyderabad")).thenReturn(actualRx);

        mockMvc.perform(get("/getDispatchableRxStock/42/hyderabad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("rxId").value("42"))
                .andExpect(jsonPath("rxName").value("Rx Name"));

        verify(rxService,times(1)).getDispatchableRxStock("42","hyderabad");
    }

    @Test
    void testIsStockAvailable() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("rxId","42");
        map.put("location","hyderabad");

        Mockito.when(rxService.isStockAvailable(map)).thenReturn(true);

        mockMvc.perform(post("/isDrugsAvailable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(map)))
                .andExpect(status().isOk());

        verify(rxService,times(1)).isStockAvailable(map);
    }

    @Test
    void testGetAllOrders() throws Exception {
        List<RxLocation> rxLocationList=new ArrayList<>();
        Rx actualRx = new Rx("42", "Rx Name", "2020-03-01", "2020-03-01", rxLocationList);
        List<Rx> list=new ArrayList<>();
        list.add(actualRx);

        Mockito.when(rxService.getAllRxDetails()).thenReturn(list);

        mockMvc.perform(get("/getAllRxDetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rxId").value("42"))
                .andExpect(jsonPath("$[0].rxName").value("Rx Name"));

        verify(rxService,times(1)).getAllRxDetails();

    }

    @Test
    void testReviewOnDrugs() throws Exception {
        ReviewOnDrugs reviewOnDrugs=new ReviewOnDrugs(1,"review@gmail.com","CETRI123","abc",5);
        Mockito.when(rxService.writeReviewOnDrugs(reviewOnDrugs)).thenReturn(reviewOnDrugs);

        mockMvc.perform(post("/reviewOnDrugs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reviewOnDrugs)))
                .andExpect(status().isOk());

        verify(rxService,times(1)).writeReviewOnDrugs(reviewOnDrugs);

    }

    @Test
    void testGetAllReviews() throws Exception {
        ReviewOnDrugs reviewOnDrugs=new ReviewOnDrugs(1,"review@gmail.com","CETRI123","abc",5);
        Mockito.when(rxService.getAllReviews()).thenReturn(List.of(reviewOnDrugs));

        mockMvc.perform(get("/getAllReviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewId").value(1))
                .andExpect(jsonPath("$[0].email").value("review@gmail.com"));

        verify(rxService,times(1)).getAllReviews();
    }



}

