package com.cts.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ValidatingDtoTest {

    @Test
    void testConstructor() {
        ValidatingDto actualValidatingDto = new ValidatingDto();
        actualValidatingDto.setValidStatus(true);
        String actualToStringResult = actualValidatingDto.toString();
        assertTrue(actualValidatingDto.isValidStatus());
        assertEquals("ValidatingDto(validStatus=true)", actualToStringResult);
    }

    @Test
    void testConstructor2() {
        ValidatingDto actualValidatingDto = new ValidatingDto(true);
        actualValidatingDto.setValidStatus(true);
        String actualToStringResult = actualValidatingDto.toString();
        assertTrue(actualValidatingDto.isValidStatus());
        assertEquals("ValidatingDto(validStatus=true)", actualToStringResult);
    }
}

