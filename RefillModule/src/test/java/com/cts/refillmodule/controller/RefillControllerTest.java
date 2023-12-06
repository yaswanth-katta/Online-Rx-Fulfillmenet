package com.cts.refillmodule.controller;

import com.cts.refillmodule.models.RefillOrderDetails;
import com.cts.refillmodule.service.RefillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RefillController.class)
class RefillControllerTest {

    @Autowired
    private RefillController refillController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefillService refillService;

    @Test
    public void testRequestAdhocRefill1() throws Exception {

        RefillOrderDetails refillOrderDetails=new RefillOrderDetails(1L,1L,"2023-10-20","placed","2023-11-20","weekly",3);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("orderId", "1"); // Set a string
        objectNode.put("location", "hyderabad");
//        objectNode.put("key3", true); // Set a boolean

        when(refillService.requestAdhocRefill(objectNode)).thenReturn(refillOrderDetails);

        mockMvc.perform(post("/requestAdhocRefill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(objectNode)))
                .andExpect(status().isOk());

        // Verify that the service method was called with the expected parameter
        verify(refillService,times(1)).requestAdhocRefill(objectNode);

    }

    @Test
    void testViewRefillStatus() throws Exception {
        RefillOrderDetails refillOrderDetails=new RefillOrderDetails(1L,1L,"2023-10-20","placed","2023-11-20","weekly",3);

        when(refillController.viewRefillStatus(1)).thenReturn(refillOrderDetails);

        mockMvc.perform(get("/viewRefillStatus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderId").value(1))
                .andExpect(jsonPath("refillDate").value("2023-10-20"));

        verify(refillService,times(1)).viewRefillStatus(1);

    }

    @Test
    void testGetRefillDuesAsOfDate() throws Exception {
        RefillOrderDetails refillOrderDetails=new RefillOrderDetails(1L,1L,"2023-10-20","placed","2023-11-20","weekly",3);
        List<RefillOrderDetails> list=new ArrayList<>();

        list.add(refillOrderDetails);
        when(refillController.getRefillDuesAsOfDate()).thenReturn(list);

        mockMvc.perform(get("/getRefillDues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].refillDate").value("2023-10-20"));

        verify(refillService,times(1)).getRefillDuesOfDate();
    }


}

