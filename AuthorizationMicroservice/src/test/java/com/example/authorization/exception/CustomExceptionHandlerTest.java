package com.example.authorization.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CustomExceptionHandlerTest {

    @Test
    void testHandleCustomException() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        ResponseEntity<String> actualHandleCustomExceptionResult = customExceptionHandler
                .handleCustomException(new AuthException("0123456789ABCDEF"));
        assertEquals("0123456789ABCDEF", actualHandleCustomExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleCustomExceptionResult.getStatusCode());
        assertTrue(actualHandleCustomExceptionResult.getHeaders().isEmpty());
    }

}

