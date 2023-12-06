package com.cts.ordermodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RefillOrderDetailsTest {

    @Test
    void testConstructor() {
        RefillOrderDetails actualRefillOrderDetails = new RefillOrderDetails(123L, 123L,"2023-10-10","Refill Status","2023-10-10","Refill Occurrence",10);

        assertEquals("2023-10-10", actualRefillOrderDetails.getNextRefillDate());
        assertEquals("Refill Status", actualRefillOrderDetails.getRefillStatus());
        assertEquals("Refill Occurrence", actualRefillOrderDetails.getRefillOccurrence());
        assertEquals("2023-10-10", actualRefillOrderDetails.getRefillDate());
        assertEquals(123L, actualRefillOrderDetails.getOrderId());
        assertEquals(10, actualRefillOrderDetails.getNumberOfRefills());
    }
    @Test
    void testConstructor1(){
        RefillOrderDetails refillOrderDetails=new RefillOrderDetails();
        refillOrderDetails.setOrderId(123L);
        refillOrderDetails.setRefillOrderId(123L);
        refillOrderDetails.setNumberOfRefills(10);
        refillOrderDetails.setRefillStatus("Refill Status");
        refillOrderDetails.setNextRefillDate("2020-03-01");
        refillOrderDetails.setRefillOccurrence("Refill Occurrence");
        refillOrderDetails.setRefillDate("2020-03-01");

        assertEquals("2020-03-01", refillOrderDetails.getNextRefillDate());
        assertEquals("Refill Status", refillOrderDetails.getRefillStatus());
        assertEquals("Refill Occurrence", refillOrderDetails.getRefillOccurrence());
        assertEquals("2020-03-01", refillOrderDetails.getRefillDate());
        assertEquals(123L, refillOrderDetails.getOrderId());
        assertEquals(123L, refillOrderDetails.getRefillOrderId());
        assertEquals(10, refillOrderDetails.getNumberOfRefills());
    }

    @Test
    void testConstructor2(){
        RefillOrderDetails refillOrderDetails=new RefillOrderDetails(123L, "2023-10-10",
                "Refill Status", "2023-10-10","Refill Occurrence",
                10);
        assertEquals("2023-10-10", refillOrderDetails.getNextRefillDate());
        assertEquals("Refill Status", refillOrderDetails.getRefillStatus());
        assertEquals("Refill Occurrence", refillOrderDetails.getRefillOccurrence());
        assertEquals("2023-10-10", refillOrderDetails.getRefillDate());
        assertEquals(123L, refillOrderDetails.getOrderId());
        assertEquals(10, refillOrderDetails.getNumberOfRefills());
        }
}

