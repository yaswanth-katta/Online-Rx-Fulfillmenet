package com.cts.rxmodule.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class CustomExceptionHandlerTest {

    @Test
    void testHandleCustomException() {
       CustomExceptionHandler exceptionHandler=new CustomExceptionHandler();
        ResponseEntity<String> actualHandleCustomExceptionResult = exceptionHandler
                .handleCustomException(new NoRecordFoundException("An error occurred"));
        assertEquals("An error occurred", actualHandleCustomExceptionResult.getBody());
        assertEquals(500, actualHandleCustomExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleCustomExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleCustomException3() {
        AnnotationConfigReactiveWebApplicationContext annotationConfigReactiveWebApplicationContext = new AnnotationConfigReactiveWebApplicationContext();
        annotationConfigReactiveWebApplicationContext
                .addApplicationListener((ApplicationListener<ApplicationEvent>) mock(ApplicationListener.class));

        CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();
        exceptionHandler.setMessageSource(annotationConfigReactiveWebApplicationContext);
        ResponseEntity<String> actualHandleCustomExceptionResult = exceptionHandler
                .handleCustomException(new NoRecordFoundException("An error occurred"));
        assertEquals("An error occurred", actualHandleCustomExceptionResult.getBody());
        assertEquals(500, actualHandleCustomExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleCustomExceptionResult.getHeaders().isEmpty());
    }
}

