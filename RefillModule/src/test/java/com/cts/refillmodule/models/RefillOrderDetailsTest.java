package com.cts.refillmodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RefillOrderDetailsTest {

    @Test
    void testConstructor() {
        RefillOrderDetails actualRefillOrderDetails = new RefillOrderDetails();
        actualRefillOrderDetails.setNextRefillDate("2020-03-01");
        actualRefillOrderDetails.setNumberOfRefills(10);
        actualRefillOrderDetails.setOrderId(123L);
        actualRefillOrderDetails.setRefillDate("2020-03-01");
        actualRefillOrderDetails.setRefillOccurrence("Refill Occurrence");
        actualRefillOrderDetails.setRefillOrderId(123L);
        actualRefillOrderDetails.setRefillStatus("Refill Status");
        assertEquals("2020-03-01", actualRefillOrderDetails.getNextRefillDate());
        assertEquals(10, actualRefillOrderDetails.getNumberOfRefills());
        assertEquals(123L, actualRefillOrderDetails.getOrderId());
        assertEquals("2020-03-01", actualRefillOrderDetails.getRefillDate());
        assertEquals("Refill Occurrence", actualRefillOrderDetails.getRefillOccurrence());
        assertEquals(123L, actualRefillOrderDetails.getRefillOrderId());
        assertEquals("Refill Status", actualRefillOrderDetails.getRefillStatus());
    }


    @Test
    void testConstructor2() {
        RefillOrderDetails actualRefillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        assertEquals("2020-03-01", actualRefillOrderDetails.getNextRefillDate());
        assertEquals("Refill Status", actualRefillOrderDetails.getRefillStatus());
        assertEquals(123L, actualRefillOrderDetails.getRefillOrderId());
        assertEquals("Refill Occurrence", actualRefillOrderDetails.getRefillOccurrence());
        assertEquals("2020-03-01", actualRefillOrderDetails.getRefillDate());
        assertEquals(123L, actualRefillOrderDetails.getOrderId());
        assertEquals(10, actualRefillOrderDetails.getNumberOfRefills());
    }
}

