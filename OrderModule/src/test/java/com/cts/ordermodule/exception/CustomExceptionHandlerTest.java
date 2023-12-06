package com.cts.ordermodule.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;

class CustomExceptionHandlerTest {

    @Test
    void testHandleOrderException() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        ResponseEntity<String> actualHandleOrderExceptionResult = customExceptionHandler
                .handleOrderException(new OrderException("An error occurred"));
        assertEquals("An error occurred", actualHandleOrderExceptionResult.getBody());
        assertEquals(500, actualHandleOrderExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleOrderExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleOrderException3() {
        AnnotationConfigReactiveWebApplicationContext annotationConfigReactiveWebApplicationContext = new AnnotationConfigReactiveWebApplicationContext();
        annotationConfigReactiveWebApplicationContext
                .addApplicationListener((ApplicationListener<ApplicationEvent>) mock(ApplicationListener.class));

        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        customExceptionHandler.setMessageSource(annotationConfigReactiveWebApplicationContext);
        ResponseEntity<String> actualHandleOrderExceptionResult = customExceptionHandler
                .handleOrderException(new OrderException("An error occurred"));
        assertEquals("An error occurred", actualHandleOrderExceptionResult.getBody());
        assertEquals(500, actualHandleOrderExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleOrderExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleStockUnAvailableException() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        ResponseEntity<String> actualHandleStockUnAvailableExceptionResult = customExceptionHandler
                .handleStockUnAvailableException(new StockUnAvailableException("An error occurred"));
        assertEquals("An error occurred", actualHandleStockUnAvailableExceptionResult.getBody());
        assertEquals(500, actualHandleStockUnAvailableExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleStockUnAvailableExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleStockUnAvailableException3() {
        AnnotationConfigReactiveWebApplicationContext annotationConfigReactiveWebApplicationContext = new AnnotationConfigReactiveWebApplicationContext();
        annotationConfigReactiveWebApplicationContext
                .addApplicationListener((ApplicationListener<ApplicationEvent>) mock(ApplicationListener.class));

        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        customExceptionHandler.setMessageSource(annotationConfigReactiveWebApplicationContext);
        ResponseEntity<String> actualHandleStockUnAvailableExceptionResult = customExceptionHandler
                .handleStockUnAvailableException(new StockUnAvailableException("An error occurred"));
        assertEquals("An error occurred", actualHandleStockUnAvailableExceptionResult.getBody());
        assertEquals(500, actualHandleStockUnAvailableExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleStockUnAvailableExceptionResult.getHeaders().isEmpty());
    }
}

