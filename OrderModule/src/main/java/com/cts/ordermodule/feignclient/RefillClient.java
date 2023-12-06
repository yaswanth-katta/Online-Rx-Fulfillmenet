package com.cts.ordermodule.feignclient;

import com.cts.ordermodule.models.RefillOrderDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Refill-Microservice", url = "localhost:5300/refill")

public interface RefillClient {
    @PostMapping(value = "/setRefillData")
    public RefillOrderDetails setRefillData(@RequestBody RefillOrderDetails refillOrder);
}
