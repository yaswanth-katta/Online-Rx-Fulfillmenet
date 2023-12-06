package com.cts.refillmodule.service;

import com.cts.refillmodule.exception.RefillException;
import com.cts.refillmodule.feign.OrderModuleClient;
import com.cts.refillmodule.models.RefillOrderDetails;
import com.cts.refillmodule.repository.RefillOrderRepo;
import com.fasterxml.jackson.databind.node.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RefillService.class})
@ExtendWith(SpringExtension.class)
class RefillServiceTest {
    @MockBean
    private OrderModuleClient orderModuleClient;

    @MockBean
    private RefillOrderRepo refillOrderRepo;

    @Autowired
    private RefillService refillService;

    @Test
    void testRequestAdhocRefill() throws RefillException, InterruptedException {
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(true);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(refillOrderDetails);
        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);

        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(MissingNode.getInstance());

        RefillOrderDetails actualRequestAdhocRefillResult = refillService.requestAdhocRefill(objectNode);
        assertSame(refillOrderDetails, actualRequestAdhocRefillResult);
        assertEquals("In Progress", actualRequestAdhocRefillResult.getRefillStatus());

        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(refillOrderRepo).save((RefillOrderDetails) any());
        verify(objectNode, atLeast(1)).get((String) any());
    }


    @Test
    void testRequestAdhocRefill1() throws RefillException {
        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(false);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(MissingNode.getInstance());
        assertThrows(RefillException.class, () -> refillService.requestAdhocRefill(objectNode));
        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(objectNode, atLeast(1)).get((String) any());
    }


    @Test
    void testRequestAdhocRefill2() throws RefillException {
        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(true);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "weekly", 10);

        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);
        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(MissingNode.getInstance());
        RefillOrderDetails actualRequestAdhocRefillResult = refillService.requestAdhocRefill(objectNode);
        assertSame(refillOrderDetails, actualRequestAdhocRefillResult);
        assertEquals("In Progress", actualRequestAdhocRefillResult.getRefillStatus());
        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(refillOrderRepo).save((RefillOrderDetails) any());
        verify(objectNode, atLeast(1)).get((String) any());
    }


    @Test
    void testRequestAdhocRefill3() throws RefillException {
        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(true);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 0);

        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);
        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(MissingNode.getInstance());
        RefillOrderDetails actualRequestAdhocRefillResult = refillService.requestAdhocRefill(objectNode);
        assertSame(refillOrderDetails, actualRequestAdhocRefillResult);
        assertEquals("No Refills", actualRequestAdhocRefillResult.getNextRefillDate());
        assertEquals("In Progress", actualRequestAdhocRefillResult.getRefillStatus());
        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(refillOrderRepo).save((RefillOrderDetails) any());
        verify(objectNode, atLeast(1)).get((String) any());
    }


    @Test
    void testRequestAdhocRefill4() throws RefillException {
        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(true);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);
        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(42L)));
        RefillOrderDetails actualRequestAdhocRefillResult = refillService.requestAdhocRefill(objectNode);
        assertSame(refillOrderDetails, actualRequestAdhocRefillResult);
        assertEquals("In Progress", actualRequestAdhocRefillResult.getRefillStatus());
        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(refillOrderRepo).save((RefillOrderDetails) any());
        verify(objectNode, atLeast(1)).get((String) any());
    }


    @Test
    void testRequestAdhocRefill5() throws RefillException, UnsupportedEncodingException {
        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(true);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);
        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(new BinaryNode("AAAAAAAA".getBytes("UTF-8")));
        RefillOrderDetails actualRequestAdhocRefillResult = refillService.requestAdhocRefill(objectNode);
        assertSame(refillOrderDetails, actualRequestAdhocRefillResult);
        assertEquals("In Progress", actualRequestAdhocRefillResult.getRefillStatus());
        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(refillOrderRepo).save((RefillOrderDetails) any());
        verify(objectNode, atLeast(1)).get((String) any());
    }


    @Test
    void testRequestAdhocRefill6() throws RefillException {
        when(orderModuleClient.requestingRefill((Map<String, String>) any())).thenReturn(true);
        when(refillOrderRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);
        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(BooleanNode.getFalse());
        RefillOrderDetails actualRequestAdhocRefillResult = refillService.requestAdhocRefill(objectNode);
        assertSame(refillOrderDetails, actualRequestAdhocRefillResult);
        assertEquals("In Progress", actualRequestAdhocRefillResult.getRefillStatus());
        verify(orderModuleClient).requestingRefill((Map<String, String>) any());
        verify(refillOrderRepo).findByOrderId(anyLong());
        verify(refillOrderRepo).save((RefillOrderDetails) any());
        verify(objectNode, atLeast(1)).get((String) any());
    }

    @Test
    void testRequestAdhocRefill7() throws RefillException, InterruptedException {
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        OrderModuleClient orderModuleClient = Mockito.mock(OrderModuleClient.class);

        RefillOrderRepo refillOrderRepo = Mockito.mock(RefillOrderRepo.class);
        RefillService yourClass = new RefillService(orderModuleClient, refillOrderRepo);

        when(refillOrderRepo.findByOrderId(anyLong())).thenReturn(refillOrderDetails);

        ObjectNode objectNode = mock(ObjectNode.class);
        when(objectNode.get((String) any())).thenReturn(MissingNode.getInstance());
        Mockito.when(orderModuleClient.requestingRefill(Mockito.anyMap())).thenReturn(true);
        RefillOrderDetails refillOrder = yourClass.requestAdhocRefill(objectNode);
        Mockito.verify(orderModuleClient, Mockito.times(1)).requestingRefill(Mockito.anyMap());


        assertEquals("In Progress", refillOrder.getRefillStatus());


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Verify that the refillOrder status has been updated in the run method
                assertEquals("Refilled", refillOrder.getRefillStatus());
            }
        }, 6000);

        Thread.sleep(7000); // Adjust the delay as needed
    }


    @Test
    void testViewRefillStatus() throws RefillException {
        RefillOrderDetails refillOrderDetails = new RefillOrderDetails(123L, 123L, "2020-03-01", "Refill Status",
                "2020-03-01", "Refill Occurrence", 10);

        when(refillOrderRepo.findById((Long) any())).thenReturn(Optional.of(refillOrderDetails));
        assertSame(refillOrderDetails, refillService.viewRefillStatus(123L));
        verify(refillOrderRepo).findById((Long) any());
    }

    @Test
    void testViewRefillStatus1() throws RefillException {
        when(refillOrderRepo.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(RefillException.class, () -> refillService.viewRefillStatus(123L));
        verify(refillOrderRepo).findById((Long) any());
    }

    @Test
    void testGetRefillDuesOfDate() throws RefillException {
        when(refillOrderRepo.getDueRefillOrders()).thenReturn(new ArrayList<>());
        assertThrows(RefillException.class, () -> refillService.getRefillDuesOfDate());
        verify(refillOrderRepo).getDueRefillOrders();
    }

    @Test
    void testGetRefillDuesOfDate2() throws RefillException {
        ArrayList<RefillOrderDetails> refillOrderDetailsList = new ArrayList<>();
        refillOrderDetailsList.add(new RefillOrderDetails(123L, 123L, "2020-03-01", "There is No Due Refill Available",
                "2020-03-01", "There is No Due Refill Available", 10));
        when(refillOrderRepo.getDueRefillOrders()).thenReturn(refillOrderDetailsList);
        List<RefillOrderDetails> actualRefillDuesOfDate = refillService.getRefillDuesOfDate();
        assertSame(refillOrderDetailsList, actualRefillDuesOfDate);
        assertEquals(1, actualRefillDuesOfDate.size());
        verify(refillOrderRepo, atLeast(1)).getDueRefillOrders();
    }

    @Test
    void testCalculateNextRefillDate() {
        assertEquals("2020-04-01", refillService.calculateNextRefillDate("2020-03-01", 10, "Refill Occurrence"));
        assertEquals("No Refills", refillService.calculateNextRefillDate("2020-03-01", 0, "Refill Occurrence"));
        assertEquals("2020-03-08", refillService.calculateNextRefillDate("2020-03-01", 10, "weekly"));
    }


}
