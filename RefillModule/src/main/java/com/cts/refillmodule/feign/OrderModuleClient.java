package com.cts.refillmodule.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "OrderModule-Microservice", url = "localhost:5200/order")
public interface OrderModuleClient {
    @PostMapping(value = "/requestingRefill")
    public boolean requestingRefill(@RequestBody Map<String,String> orderDetails);

//    @PostMapping(value = "/getRefillDuesAsOfDate")
//    public
}
