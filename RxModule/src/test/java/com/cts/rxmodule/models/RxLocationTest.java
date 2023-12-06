package com.cts.rxmodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RxLocationTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RxLocation#RxLocation()}
     *   <li>{@link RxLocation#setCostPerPackage(long)}
     *   <li>{@link RxLocation#setId(long)}
     *   <li>{@link RxLocation#setLocationName(String)}
     *   <li>{@link RxLocation#setQuantitiesAvailable(long)}
     *   <li>{@link RxLocation#setRxLocationId(String)}
     *   <li>{@link RxLocation#setUnitsInPackage(long)}
     *   <li>{@link RxLocation#getCostPerPackage()}
     *   <li>{@link RxLocation#getId()}
     *   <li>{@link RxLocation#getLocationName()}
     *   <li>{@link RxLocation#getQuantitiesAvailable()}
     *   <li>{@link RxLocation#getRxLocationId()}
     *   <li>{@link RxLocation#getUnitsInPackage()}
     * </ul>
     */
    @Test
    void testConstructor() {
        RxLocation actualRxLocation = new RxLocation();
        actualRxLocation.setCostPerPackage(1L);
        actualRxLocation.setId(123L);
        actualRxLocation.setLocationName("Location Name");
        actualRxLocation.setQuantitiesAvailable(1L);
        actualRxLocation.setRxLocationId("42");
        actualRxLocation.setUnitsInPackage(1L);
        assertEquals(1L, actualRxLocation.getCostPerPackage());
        assertEquals(123L, actualRxLocation.getId());
        assertEquals("Location Name", actualRxLocation.getLocationName());
        assertEquals(1L, actualRxLocation.getQuantitiesAvailable());
        assertEquals("42", actualRxLocation.getRxLocationId());
        assertEquals(1L, actualRxLocation.getUnitsInPackage());
    }

    /**
     * Method under test: {@link RxLocation#RxLocation(long, String, String, long, long, long)}
     */
    @Test
    void testConstructor2() {
        RxLocation actualRxLocation = new RxLocation(123L, "42", "Location Name", 1L, 1L, 1L);

        assertEquals(1L, actualRxLocation.getCostPerPackage());
        assertEquals(1L, actualRxLocation.getUnitsInPackage());
        assertEquals("42", actualRxLocation.getRxLocationId());
        assertEquals(1L, actualRxLocation.getQuantitiesAvailable());
        assertEquals("Location Name", actualRxLocation.getLocationName());
        assertEquals(123L, actualRxLocation.getId());
    }
}

