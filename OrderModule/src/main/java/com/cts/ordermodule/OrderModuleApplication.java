package com.cts.ordermodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderModuleApplication.class, args);
	}

}
