package com.cts.rxmodule.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NoRecordFoundExceptionTest {

    @Test
    void testConstructor() {
        NoRecordFoundException actualNoRecordFoundException = new NoRecordFoundException("An error occurred");
        assertNull(actualNoRecordFoundException.getCause());
        assertEquals(0, actualNoRecordFoundException.getSuppressed().length);
        assertEquals("An error occurred", actualNoRecordFoundException.getMessage());
        assertEquals("An error occurred", actualNoRecordFoundException.getLocalizedMessage());
    }
}

