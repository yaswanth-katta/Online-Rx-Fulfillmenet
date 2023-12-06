package com.cts.rxmodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class RxTest {

    @Test
    void testConstructor() {
        Rx actualRx = new Rx();
        actualRx.setExpiryDate("2020-03-01");
        actualRx.setManufacturedDate("2020-03-01");
        actualRx.setRxId("42");
        ArrayList<RxLocation> rxLocationList = new ArrayList<>();
        actualRx.setRxLocation(rxLocationList);
        actualRx.setRxName("Rx Name");
        assertEquals("2020-03-01", actualRx.getExpiryDate());
        assertEquals("2020-03-01", actualRx.getManufacturedDate());
        assertEquals("42", actualRx.getRxId());
        assertSame(rxLocationList, actualRx.getRxLocation());
        assertEquals("Rx Name", actualRx.getRxName());
    }

    @Test
    void testConstructor2() {
        ArrayList<RxLocation> rxLocationList = new ArrayList<>();
        Rx actualRx = new Rx("42", "Rx Name", "2020-03-01", "2020-03-01", rxLocationList);
        actualRx.setExpiryDate("2020-03-01");
        actualRx.setManufacturedDate("2020-03-01");
        actualRx.setRxId("42");
        ArrayList<RxLocation> rxLocationList1 = new ArrayList<>();
        actualRx.setRxLocation(rxLocationList1);
        actualRx.setRxName("Rx Name");
        assertEquals("2020-03-01", actualRx.getExpiryDate());
        assertEquals("2020-03-01", actualRx.getManufacturedDate());
        assertEquals("42", actualRx.getRxId());
        List<RxLocation> rxLocation = actualRx.getRxLocation();
        assertSame(rxLocationList1, rxLocation);
        assertEquals(rxLocationList, rxLocation);
        assertEquals("Rx Name", actualRx.getRxName());
    }
}

