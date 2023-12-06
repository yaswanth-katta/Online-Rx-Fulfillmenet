package com.cts.refillmodule.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class RefillExceptionTest {

    @Test
    void testConstructor() {
        RefillException actualRefillException = new RefillException("An error occurred");
        assertNull(actualRefillException.getCause());
        assertEquals(0, actualRefillException.getSuppressed().length);
        assertEquals("An error occurred", actualRefillException.getMessage());
        assertEquals("An error occurred", actualRefillException.getLocalizedMessage());
    }
}

