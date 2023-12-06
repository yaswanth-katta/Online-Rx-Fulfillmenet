package com.cts.ordermodule.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "RxModule-Microservice", url = "localhost:5100/drugs")
public interface RxModuleClient {
    @PostMapping(value = "/isDrugsAvailable")
    public boolean isStockAvailable(@RequestBody Map<String,String> orderDetails);
}
