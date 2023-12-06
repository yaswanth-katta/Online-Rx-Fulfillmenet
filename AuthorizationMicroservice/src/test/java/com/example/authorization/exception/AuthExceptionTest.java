package com.example.authorization.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AuthExceptionTest {

    @Test
    void testConstructor() {
        AuthException actualAuthException = new AuthException("0123456789ABCDEF");
        assertNull(actualAuthException.getCause());
        assertEquals(0, actualAuthException.getSuppressed().length);
        assertEquals("0123456789ABCDEF", actualAuthException.getMessage());
        assertEquals("0123456789ABCDEF", actualAuthException.getLocalizedMessage());
    }
}

