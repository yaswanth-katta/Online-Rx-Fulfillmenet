package com.cts.ordermodule.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StockUnAvailableExceptionTest {
    @Test
    void testConstructor(){
        StockUnAvailableException stockUnAvailableException = new StockUnAvailableException("An error occurred");
        assertNull(stockUnAvailableException.getCause());
        assertEquals(0, stockUnAvailableException.getSuppressed().length);
        assertEquals("An error occurred", stockUnAvailableException.getMessage());
        assertEquals("An error occurred", stockUnAvailableException.getLocalizedMessage());
    }
}
