package com.cts.refillmodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RefillOrderLineItemTest {
    @Test
    void testConstructor() {
        RefillOrderLineItem actualRefillOrderLineItem = new RefillOrderLineItem();
        actualRefillOrderLineItem.setQuantityConsiderForRefill(1L);
        actualRefillOrderLineItem.setRefillOrderLineId(123L);
        assertEquals(1L, actualRefillOrderLineItem.getQuantityConsiderForRefill());
        assertEquals(123L, actualRefillOrderLineItem.getRefillOrderLineId());
    }

    /**
     * Method under test: {@link RefillOrderLineItem#RefillOrderLineItem(long, long)}
     */
    @Test
    void testConstructor2() {
        RefillOrderLineItem actualRefillOrderLineItem = new RefillOrderLineItem(123L, 1L);

        assertEquals(1L, actualRefillOrderLineItem.getQuantityConsiderForRefill());
        assertEquals(123L, actualRefillOrderLineItem.getRefillOrderLineId());
    }
}

