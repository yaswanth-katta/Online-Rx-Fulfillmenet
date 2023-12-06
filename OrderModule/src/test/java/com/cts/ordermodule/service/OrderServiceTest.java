package com.cts.ordermodule.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cts.ordermodule.exception.OrderException;
import com.cts.ordermodule.exception.StockUnAvailableException;
import com.cts.ordermodule.feignclient.RefillClient;
import com.cts.ordermodule.feignclient.RxModuleClient;
import com.cts.ordermodule.models.PrescriptionDetails;
import com.cts.ordermodule.models.PrescriptionOrder;
import com.cts.ordermodule.models.RefillOrderDetails;
import com.cts.ordermodule.repository.OrderRepo;
import com.cts.ordermodule.repository.RefillRepo;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderService.class})
@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @MockBean
    private OrderRepo orderRepo;

    @Autowired
    private OrderService orderService;

    @MockBean
    private RefillClient refillClient;

    @MockBean
    private RefillRepo refillRepo;

    @MockBean
    private RxModuleClient rxModuleClient;


    @Test
    void testOrderDrugs() throws StockUnAvailableException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder
                .setPrescriptionDetails(new PrescriptionDetails(123L, "42", "rxId", "2020-03-01", "rxId", "rxId", 10));
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(prescriptionOrder);
        when(refillRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);

        PrescriptionOrder prescriptionOrder1 = new PrescriptionOrder();
        prescriptionOrder1.setRefillOccurrence("rxId");
        prescriptionOrder1
                .setPrescriptionDetails(new PrescriptionDetails(123L, "42", "rxId", "2020-03-01", "rxId", "rxId", 10));
        assertSame(prescriptionOrder, orderService.orderDrugs(prescriptionOrder1));

        verify(orderRepo).save((PrescriptionOrder) any());
        verify(refillRepo).save((RefillOrderDetails) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
        assertEquals("In Progress", prescriptionOrder1.getOrderStatus());
    }

    @Test
    void testOrderDrugs1() throws StockUnAvailableException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder
                .setPrescriptionDetails(new PrescriptionDetails(123L, "42", "rxId", "2020-03-01", "rxId", "rxId", 10));
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(prescriptionOrder);
        when(refillRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(false);

        PrescriptionOrder prescriptionOrder1 = new PrescriptionOrder();
        prescriptionOrder1.setRefillOccurrence("rxId");
        prescriptionOrder1
                .setPrescriptionDetails(new PrescriptionDetails(123L, "42", "rxId", "2020-03-01", "rxId", "rxId", 10));
        assertThrows(StockUnAvailableException.class, () -> orderService.orderDrugs(prescriptionOrder1));
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
    }

    @Test
    void testOrderDrugs2() throws StockUnAvailableException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder
                .setPrescriptionDetails(new PrescriptionDetails(123L, "42", "rxId", "2020-03-01", "rxId", "rxId", 10));
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(prescriptionOrder);
        when(refillRepo.save((RefillOrderDetails) any())).thenReturn(
                new RefillOrderDetails(123L, "2020-03-01", "Refill Status", "2020-03-01", "Refill Occurrence", 10));
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);
        PrescriptionOrder prescriptionOrder1 = mock(PrescriptionOrder.class);
        doNothing().when(prescriptionOrder1).setNextRefillDate((String) any());
        when(prescriptionOrder1.getLocation()).thenReturn("Location");
        when(prescriptionOrder1.getOrderDate()).thenReturn("2020-03-01");
        when(prescriptionOrder1.getRefillOccurrence()).thenReturn("Refill Occurrence");
        doNothing().when(prescriptionOrder1).setOrderDate((String) any());
        doNothing().when(prescriptionOrder1).setOrderStatus((String) any());
        when(prescriptionOrder1.getPrescriptionDetails()).thenReturn(new PrescriptionDetails(123L, "42",
                "Insurance Provider", "2020-03-01", "Dosage Definition Fora Day", "Doctor Details", -1));
        doNothing().when(prescriptionOrder1).setPrescriptionDetails((PrescriptionDetails) any());
        doNothing().when(prescriptionOrder1).setRefillOccurrence((String) any());
        prescriptionOrder1.setRefillOccurrence("rxId");
        prescriptionOrder1
                .setPrescriptionDetails(new PrescriptionDetails(123L, "42", "rxId", "2020-03-01", "rxId", "rxId", 10));
        assertSame(prescriptionOrder, orderService.orderDrugs(prescriptionOrder1));
        verify(orderRepo).save((PrescriptionOrder) any());
        verify(refillRepo).save((RefillOrderDetails) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
        verify(prescriptionOrder1, atLeast(1)).getPrescriptionDetails();
        verify(prescriptionOrder1).getLocation();
        verify(prescriptionOrder1).getOrderDate();
        verify(prescriptionOrder1).getRefillOccurrence();
        verify(prescriptionOrder1).setNextRefillDate((String) any());
        verify(prescriptionOrder1).setOrderDate((String) any());
        verify(prescriptionOrder1).setOrderStatus((String) any());
        verify(prescriptionOrder1).setPrescriptionDetails((PrescriptionDetails) any());
        verify(prescriptionOrder1).setRefillOccurrence((String) any());
    }


    @Test
    void testCancelOrder() throws OrderException {
        doNothing().when(orderRepo).deleteById((Long) any());
        when(orderRepo.findById((Long) any())).thenReturn(Optional.of(new PrescriptionOrder()));
        doNothing().when(refillRepo).deleteByOrderId(anyLong());
        String actualCancelOrderResult = orderService.cancelOrder("42", 123L);
        assertEquals("Order is cancelled successfully", actualCancelOrderResult);
//        assertEquals(200, actualCancelOrderResult);
//        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
        verify(orderRepo).findById((Long) any());
        verify(orderRepo).deleteById((Long) any());
        verify(refillRepo).deleteByOrderId(anyLong());
    }


    @Test
    void testCancelOrder2() throws OrderException {
        doNothing().when(orderRepo).deleteById((Long) any());
        when(orderRepo.findById((Long) any())).thenReturn(Optional.empty());
        doNothing().when(refillRepo).deleteByOrderId(anyLong());
        assertThrows(OrderException.class, () -> orderService.cancelOrder("42", 123L));
        verify(orderRepo).findById((Long) any());
    }

    @Test
    void testCancelOrder3() throws OrderException {
        doNothing().when(orderRepo).deleteById((Long) any());
        when(orderRepo.findById((Long) any())).thenReturn(Optional.of(new PrescriptionOrder()));
        doNothing().when(refillRepo).deleteByOrderId(anyLong());
        assertEquals("Order is cancelled successfully", orderService.cancelOrder("42", 123L));
        verify(orderRepo).findById((Long) any());
        verify(orderRepo).deleteById((Long) any());
        verify(refillRepo).deleteByOrderId(anyLong());
    }

    /**
     * Method under test: {@link OrderService#cancelOrder(String, long)}
     */
    @Test
    void testCancelOrder4() throws OrderException {
        doNothing().when(orderRepo).deleteById((Long) any());
        when(orderRepo.findById((Long) any())).thenReturn(Optional.empty());
        doNothing().when(refillRepo).deleteByOrderId(anyLong());
        assertThrows(OrderException.class, () -> orderService.cancelOrder("42", 123L));
        verify(orderRepo).findById((Long) any());
    }

    @Test
    void testRequestingRefill() throws OrderException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder.setRefillOccurrence("orderId");
        prescriptionOrder.setPrescriptionDetails(
                new PrescriptionDetails(123L, "42", "orderId", "2020-03-01", "orderId", "orderId", 10));
        Optional<PrescriptionOrder> ofResult = Optional.of(prescriptionOrder);
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(new PrescriptionOrder());
        when(orderRepo.findById((Long) any())).thenReturn(ofResult);
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", "42");
        assertTrue(orderService.requestingRefill(stringStringMap));
        verify(orderRepo).save((PrescriptionOrder) any());
        verify(orderRepo).findById((Long) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
    }


    @Test
    void testRequestingRefill1() throws OrderException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder.setRefillOccurrence("weekly");
        prescriptionOrder.setPrescriptionDetails(
                new PrescriptionDetails(123L, "42", "orderId", "2020-03-01", "orderId", "orderId", 10));
        Optional<PrescriptionOrder> ofResult = Optional.of(prescriptionOrder);
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(new PrescriptionOrder());
        when(orderRepo.findById((Long) any())).thenReturn(ofResult);
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", "42");
        assertTrue(orderService.requestingRefill(stringStringMap));
        verify(orderRepo).save((PrescriptionOrder) any());
        verify(orderRepo).findById((Long) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
    }


    @Test
    void testRequestingRefill2() throws OrderException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder.setRefillOccurrence("orderId");
        prescriptionOrder.setPrescriptionDetails(
                new PrescriptionDetails(123L, "42", "orderId", "2020-03-01", "orderId", "orderId", 1));
        Optional<PrescriptionOrder> ofResult = Optional.of(prescriptionOrder);
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(new PrescriptionOrder());
        when(orderRepo.findById((Long) any())).thenReturn(ofResult);
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", "42");
        assertTrue(orderService.requestingRefill(stringStringMap));
        verify(orderRepo).save((PrescriptionOrder) any());
        verify(orderRepo).findById((Long) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
    }


    @Test
    void testRequestingRefill3() throws OrderException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder.setRefillOccurrence("orderId");
        prescriptionOrder.setPrescriptionDetails(
                new PrescriptionDetails(123L, "42", "orderId", "2020-03-01", "orderId", "orderId", 0));
        Optional<PrescriptionOrder> ofResult = Optional.of(prescriptionOrder);
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(new PrescriptionOrder());
        when(orderRepo.findById((Long) any())).thenReturn(ofResult);
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", "42");
        assertThrows(OrderException.class, () -> orderService.requestingRefill(stringStringMap));
        verify(orderRepo).findById((Long) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
    }

    @Test
    void testRequestingRefill4() throws OrderException {
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(new PrescriptionOrder());
        when(orderRepo.findById((Long) any())).thenReturn(Optional.empty());
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(true);

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", "42");
        assertThrows(OrderException.class, () -> orderService.requestingRefill(stringStringMap));
        verify(orderRepo).findById((Long) any());
    }


    @Test
    void testRequestingRefill5() throws OrderException {
        PrescriptionOrder prescriptionOrder = new PrescriptionOrder();
        prescriptionOrder.setRefillOccurrence("orderId");
        prescriptionOrder.setPrescriptionDetails(
                new PrescriptionDetails(123L, "42", "orderId", "2020-03-01", "orderId", "orderId", 10));
        Optional<PrescriptionOrder> ofResult = Optional.of(prescriptionOrder);
        when(orderRepo.save((PrescriptionOrder) any())).thenReturn(new PrescriptionOrder());
        when(orderRepo.findById((Long) any())).thenReturn(ofResult);
        when(rxModuleClient.isStockAvailable((Map<String, String>) any())).thenReturn(false);

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", "42");
        assertThrows(OrderException.class, () -> orderService.requestingRefill(stringStringMap));
        verify(orderRepo).findById((Long) any());
        verify(rxModuleClient).isStockAvailable((Map<String, String>) any());
    }


    @Test
    void testGetAllOrders() throws OrderException {
        when(orderRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(OrderException.class, () -> orderService.getAllOrders());
        verify(orderRepo).findAll();
    }


    @Test
    void testGetAllOrders1() throws OrderException {
        ArrayList<PrescriptionOrder> prescriptionOrderList = new ArrayList<>();
        prescriptionOrderList.add(new PrescriptionOrder());
        when(orderRepo.findAll()).thenReturn(prescriptionOrderList);
        List<PrescriptionOrder> actualAllOrders = orderService.getAllOrders();
        assertSame(prescriptionOrderList, actualAllOrders);
        assertEquals(1, actualAllOrders.size());
        verify(orderRepo).findAll();
    }


    @Test
    void testCalculateNextRefillDate() {
        assertEquals("2020-04-01", orderService.calculateNextRefillDate("2020-03-01", 10, "Refill Occurrence"));
        assertEquals("No Refills", orderService.calculateNextRefillDate("2020-03-01", 0, "Refill Occurrence"));
        assertEquals("2020-03-08", orderService.calculateNextRefillDate("2020-03-01", 10, "weekly"));
    }


    @Test
    void testSearchOrderByIdOrLocation() {
        ArrayList<PrescriptionOrder> prescriptionOrderList = new ArrayList<>();
        when(orderRepo.searchOrderByIdOrName((String) any())).thenReturn(prescriptionOrderList);
        List<PrescriptionOrder> actualSearchOrderByIdOrLocationResult = orderService
                .searchOrderByIdOrLocation("Search Key");
        assertSame(prescriptionOrderList, actualSearchOrderByIdOrLocationResult);
        assertTrue(actualSearchOrderByIdOrLocationResult.isEmpty());
        verify(orderRepo).searchOrderByIdOrName((String) any());
    }


}

