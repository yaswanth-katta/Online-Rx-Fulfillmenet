package com.cts.rxmodule.service;

import com.cts.rxmodule.exceptions.NoRecordFoundException;
import com.cts.rxmodule.models.ReviewOnDrugs;
import com.cts.rxmodule.models.Rx;
import com.cts.rxmodule.models.RxLocation;
import com.cts.rxmodule.repository.ReviewOnDrugsRepo;
import com.cts.rxmodule.repository.RxRepo;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RxService.class})
@ExtendWith(SpringExtension.class)
class RxServiceTest {
    @MockBean
    private ReviewOnDrugsRepo reviewOnDrugsRepo;

    @MockBean
    private RxRepo rxRepo;

    @Autowired
    private RxService rxService;


    @Test
    void testAddRxDetails() throws NoRecordFoundException {
        when(rxRepo.save((Rx) any())).thenReturn(new Rx());
        when(rxRepo.findById((String) any())).thenReturn(Optional.of(new Rx()));
        assertThrows(NoRecordFoundException.class, () -> rxService.addRxDetails(new Rx()));
        verify(rxRepo).findById((String) any());
    }


    @Test
    void testAddRxDetails2() throws NoRecordFoundException {
        Rx rx = new Rx();
        when(rxRepo.save((Rx) any())).thenReturn(rx);
        when(rxRepo.findById((String) any())).thenReturn(Optional.empty());
        assertSame(rx, rxService.addRxDetails(new Rx()));
        verify(rxRepo).save((Rx) any());
        verify(rxRepo).findById((String) any());
    }


    @Test
    void testAddRxDetails3() throws NoRecordFoundException {
        when(rxRepo.save((Rx) any())).thenReturn(new Rx());
        when(rxRepo.findById((String) any())).thenReturn(Optional.of(new Rx()));
        assertThrows(NoRecordFoundException.class, () -> rxService.addRxDetails(null));
    }


    @Test
    void testSearchById() throws NoRecordFoundException {
        when(rxRepo.searchByIdOrName((String) any())).thenReturn(new ArrayList<>());
        assertThrows(NoRecordFoundException.class, () -> rxService.searchById("Search Keyword"));
        verify(rxRepo).searchByIdOrName((String) any());
    }

    @Test
    void testSearchById2() throws NoRecordFoundException {
        ArrayList<Rx> rxList = new ArrayList<>();
        rxList.add(new Rx());
        when(rxRepo.searchByIdOrName((String) any())).thenReturn(rxList);
        List<Rx> actualSearchByIdResult = rxService.searchById("Search Keyword");
        assertSame(rxList, actualSearchByIdResult);
        assertEquals(1, actualSearchByIdResult.size());
        verify(rxRepo, atLeast(1)).searchByIdOrName((String) any());
    }


    @Test
    void testGetDispatchableRxStock() throws NoRecordFoundException {
        Rx rx = new Rx();
        when(rxRepo.getDispatchableRxStock((String) any(), (String) any())).thenReturn(Optional.of(rx));
        assertSame(rx, rxService.getDispatchableRxStock("42", "Location Name"));
        verify(rxRepo).getDispatchableRxStock((String) any(), (String) any());
    }

    @Test
    void testGetDispatchableRxStock1() throws NoRecordFoundException {
        when(rxRepo.getDispatchableRxStock((String) any(), (String) any())).thenReturn(Optional.empty());
        assertThrows(NoRecordFoundException.class, () -> rxService.getDispatchableRxStock("42", "Location Name"));
        verify(rxRepo).getDispatchableRxStock((String) any(), (String) any());
    }


    @Test
    void testIsStockAvailable3() throws NoRecordFoundException {
        when(rxRepo.getDispatchableRxStock((String) any(), (String) any())).thenReturn(Optional.empty());
        assertThrows(NoRecordFoundException.class, () -> rxService.isStockAvailable(new HashMap<>()));
        verify(rxRepo).getDispatchableRxStock((String) any(), (String) any());
    }


    @Test
    void testIsStockAvailable5() throws NoRecordFoundException {
        ArrayList<RxLocation> rxLocationList = new ArrayList<>();
        rxLocationList.add(new RxLocation(123L, "42", "Location Name", 1L, 1L, 1L));
        Optional<Rx> ofResult = Optional.of(new Rx("42", "rxId", "2020-03-01", "2020-03-01", rxLocationList));
        when(rxRepo.getDispatchableRxStock((String) any(), (String) any())).thenReturn(ofResult);
        assertTrue(rxService.isStockAvailable(new HashMap<>()));
        verify(rxRepo).getDispatchableRxStock((String) any(), (String) any());
    }


    @Test
    void testIsStockAvailable6() throws NoRecordFoundException {
        ArrayList<RxLocation> rxLocationList = new ArrayList<>();
        rxLocationList.add(new RxLocation());
        Optional<Rx> ofResult = Optional.of(new Rx("42", "rxId", "2020-03-01", "2020-03-01", rxLocationList));
        when(rxRepo.getDispatchableRxStock((String) any(), (String) any())).thenReturn(ofResult);
        assertFalse(rxService.isStockAvailable(new HashMap<>()));
        verify(rxRepo).getDispatchableRxStock((String) any(), (String) any());
    }

    @Test
    void testGetAllRxDetails() throws NoRecordFoundException {
        when(rxRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoRecordFoundException.class, () -> rxService.getAllRxDetails());
        verify(rxRepo).findAll();
    }


    @Test
    void testGetAllRxDetails2() throws NoRecordFoundException {
        ArrayList<Rx> rxList = new ArrayList<>();
        rxList.add(new Rx());
        when(rxRepo.findAll()).thenReturn(rxList);
        List<Rx> actualAllRxDetails = rxService.getAllRxDetails();
        assertSame(rxList, actualAllRxDetails);
        assertEquals(1, actualAllRxDetails.size());
        verify(rxRepo).findAll();
    }


    @Test
    void testWriteReviewOnDrugs() {
        ReviewOnDrugs reviewOnDrugs = new ReviewOnDrugs(123, "jane.doe@example.org", "Rx Name", "Comments", 1);

        when(reviewOnDrugsRepo.save((ReviewOnDrugs) any())).thenReturn(reviewOnDrugs);
        assertSame(reviewOnDrugs,
                rxService.writeReviewOnDrugs(new ReviewOnDrugs(123, "jane.doe@example.org", "Rx Name", "Comments", 1)));
        verify(reviewOnDrugsRepo).save((ReviewOnDrugs) any());
    }


    @Test
    void testGetAllReviews() {
        ArrayList<ReviewOnDrugs> reviewOnDrugsList = new ArrayList<>();
        when(reviewOnDrugsRepo.findAll()).thenReturn(reviewOnDrugsList);
        List<ReviewOnDrugs> actualAllReviews = rxService.getAllReviews();
        assertSame(reviewOnDrugsList, actualAllReviews);
        assertTrue(actualAllReviews.isEmpty());
        verify(reviewOnDrugsRepo).findAll();
    }

}

