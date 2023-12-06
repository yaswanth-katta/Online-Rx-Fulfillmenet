package com.cts.ordermodule.controller;

import com.cts.ordermodule.exception.OrderException;
import com.cts.ordermodule.exception.StockUnAvailableException;
import com.cts.ordermodule.models.PrescriptionOrder;
import com.cts.ordermodule.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:4200",allowedHeaders ="*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/orderDrugs")
    public PrescriptionOrder orderDrugs(@RequestBody PrescriptionOrder prescriptionOrder) throws StockUnAvailableException {
        return orderService.orderDrugs(prescriptionOrder);
    }
    @DeleteMapping(value = "/cancelOrder/{memberId}/{orderId}")
    public String cancelOrder(@PathVariable String memberId,@PathVariable long orderId) throws OrderException {
        return orderService.cancelOrder(memberId,orderId);
    }

    @PostMapping(value = "/requestingRefill")
    public Boolean requestingRefill(@RequestBody Map<String,String> refillDetails) throws OrderException {
        return orderService.requestingRefill(refillDetails);
    }

    @GetMapping(value="/allOrders")
    public List<PrescriptionOrder> getAllOrders() throws OrderException {
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/searchOrderByIdOrLocation/{searchKey}")
    public List<PrescriptionOrder> searchOrderByIdOrLocation(@PathVariable String searchKey){
        return orderService.searchOrderByIdOrLocation(searchKey);
    }
}

