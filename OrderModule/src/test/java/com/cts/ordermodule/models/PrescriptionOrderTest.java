package com.cts.ordermodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class PrescriptionOrderTest {

    @Test
    void testConstructor() {
        PrescriptionOrder actualPrescriptionOrder = new PrescriptionOrder();
        actualPrescriptionOrder.setLocation("Location");
        actualPrescriptionOrder.setMemberId(123L);
        actualPrescriptionOrder.setNextRefillDate("2020-03-01");
        actualPrescriptionOrder.setOrderDate("2020-03-01");
        actualPrescriptionOrder.setOrderId(123L);
        actualPrescriptionOrder.setOrderStatus("Order Status");
        PrescriptionDetails prescriptionDetails = new PrescriptionDetails(123L, "42", "Insurance Provider", "2020-03-01",
                "Dosage Definition Fora Day", "Doctor Details", 10);

        actualPrescriptionOrder.setPrescriptionDetails(prescriptionDetails);
        actualPrescriptionOrder.setRefillOccurrence("Refill Occurrence");
        assertEquals("Location", actualPrescriptionOrder.getLocation());
        assertEquals(123L, actualPrescriptionOrder.getMemberId());
        assertEquals("2020-03-01", actualPrescriptionOrder.getNextRefillDate());
        assertEquals("2020-03-01", actualPrescriptionOrder.getOrderDate());
        assertEquals(123L, actualPrescriptionOrder.getOrderId());
        assertEquals("Order Status", actualPrescriptionOrder.getOrderStatus());
        assertSame(prescriptionDetails, actualPrescriptionOrder.getPrescriptionDetails());
        assertEquals("Refill Occurrence", actualPrescriptionOrder.getRefillOccurrence());
    }

    @Test
    void testConstructor2() {
        PrescriptionDetails prescriptionDetails = new PrescriptionDetails(123L, "42", "Insurance Provider", "2020-03-01",
                "Dosage Definition Fora Day", "Doctor Details", 10);

        PrescriptionOrder actualPrescriptionOrder = new PrescriptionOrder(123L, 123L, "2020-03-01", "2020-03-01",
                "Refill Occurrence", "Location", "Order Status", prescriptionDetails);

        assertEquals("Location", actualPrescriptionOrder.getLocation());
        assertEquals("Refill Occurrence", actualPrescriptionOrder.getRefillOccurrence());
        assertSame(prescriptionDetails, actualPrescriptionOrder.getPrescriptionDetails());
        assertEquals("Order Status", actualPrescriptionOrder.getOrderStatus());
        assertEquals(123L, actualPrescriptionOrder.getMemberId());
        assertEquals("2020-03-01", actualPrescriptionOrder.getOrderDate());
        assertEquals(123L, actualPrescriptionOrder.getOrderId());
        assertEquals("2020-03-01", actualPrescriptionOrder.getNextRefillDate());
    }
}

