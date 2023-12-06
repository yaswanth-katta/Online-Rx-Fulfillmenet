package com.cts.refillmodule.controller;

import com.cts.refillmodule.exception.RefillException;
import com.cts.refillmodule.models.RefillOrderDetails;
import com.cts.refillmodule.service.RefillService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200",allowedHeaders ="*")
public class RefillController {
    @Autowired
    private RefillService refillService;

    @PostMapping(value = "/requestAdhocRefill")
    public RefillOrderDetails requestAdhocRefill(@RequestBody ObjectNode objectNode) throws RefillException
    {
        return refillService.requestAdhocRefill(objectNode);
    }
    @ApiOperation(value = "Create the Item based on input provided", response = RefillOrderDetails.class)
    @GetMapping(value = "/viewRefillStatus/{orderId}")
    public RefillOrderDetails viewRefillStatus(@PathVariable long orderId) throws RefillException {
        return refillService.viewRefillStatus(orderId);
    }

    @GetMapping(value = "/getRefillDues")
    public List<RefillOrderDetails> getRefillDuesAsOfDate() throws RefillException {
        return refillService.getRefillDuesOfDate();

    }




}
