package com.cts.ordermodule.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderExceptionTest {
    @Test
    void testConstructor(){
        OrderException orderException = new OrderException("An error occurred");
        assertNull(orderException.getCause());
        assertEquals(0, orderException.getSuppressed().length);
        assertEquals("An error occurred", orderException.getMessage());
        assertEquals("An error occurred", orderException.getLocalizedMessage());
    }
}
