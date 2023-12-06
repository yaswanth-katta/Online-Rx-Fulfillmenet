package com.cts.ordermodule.controller;

import com.cts.ordermodule.models.PrescriptionDetails;
import com.cts.ordermodule.models.PrescriptionOrder;
import com.cts.ordermodule.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    void testOrderDrugs() throws Exception {
        PrescriptionDetails prescriptionDetails = new PrescriptionDetails(1L, "PARA1122", "apollo", "2023-10-28", "8", "abcd", 3);
        PrescriptionOrder order = new PrescriptionOrder(1L, 101L, "2023-10-20", "2023-11-20", "weekly", "hyderabad", "placed", prescriptionDetails);

        when(orderService.orderDrugs(Mockito.<PrescriptionOrder>any())).thenReturn(order);


        String content = (new ObjectMapper()).writeValueAsString(order);
        MockHttpServletRequestBuilder requestBuilder = post("/orderDrugs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("orderId").value(1))
                .andExpect(jsonPath("nextRefillDate").value("2023-11-20"));

//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content()
//                        .string(
//                                "{\"orderId\":1,\"memberId\":A,\"orderDate\":\"2023-10-20\",\"nextRefillDate\":\"2023-11-20\",\"refillOccurrence\":\"weekly"
//                                        + "\",\"location\":\"hyderabad\",\"orderStatus\":\"placed\"}"));
    }

    /**
     * Method under test: {@link OrderController#cancelOrder(String, long)}
     */
    @Test
    void testCancelOrder() throws Exception {
        when(orderService.cancelOrder("101",1)).thenReturn("Order is cancelled successfully");

        mockMvc.perform(delete("/cancelOrder/101/1"))
                .andExpect(status().isOk());
        verify(orderService,times(1)).cancelOrder("101",1);

    }

    @Test
    void testRequestingRefill() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderId","1");
        map.put("location","hyderabad");
        when(orderService.requestingRefill(map)).thenReturn(true);

        mockMvc.perform(post("/requestingRefill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(map)))
                .andExpect(status().isOk());


        verify(orderService,times(1)).requestingRefill(map);
    }

    @Test
    void testGetAllOrders() throws Exception {
        PrescriptionDetails prescriptionDetails = new PrescriptionDetails(1L, "PARA1122", "apollo", "2023-10-28", "8", "abcd", 3);
        PrescriptionOrder order = new PrescriptionOrder(1L, 101L, "2023-10-20", "2023-11-20", "weekly", "hyderabad", "placed", prescriptionDetails);
        List<PrescriptionOrder> list=new ArrayList<>();
        list.add(order);

        when(orderService.getAllOrders()).thenReturn(list);

        mockMvc.perform(get("/allOrders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].orderDate").value("2023-10-20"));

        verify(orderService,times(1)).getAllOrders();

    }

    @Test
    void testSearchOrderByIdOrLocation() throws Exception {
        PrescriptionDetails prescriptionDetails = new PrescriptionDetails(1L, "PARA1122", "apollo", "2023-10-28", "8", "abcd", 3);
        PrescriptionOrder order = new PrescriptionOrder(1L, 101L, "2023-10-20", "2023-11-20", "weekly", "hyderabad", "placed", prescriptionDetails);
        List<PrescriptionOrder> list=new ArrayList<>();
        list.add(order);

        when(orderService.searchOrderByIdOrLocation("hyderabad")).thenReturn(list);

        mockMvc.perform(get("/searchOrderByIdOrLocation/hyderabad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].orderDate").value("2023-10-20"));

        verify(orderService,times(1)).searchOrderByIdOrLocation("hyderabad");
    }

}

