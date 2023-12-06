package com.example.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationMicroserviceApplication.class, args);
	}
}

/**
 * @Bean
 *        public Docket productApi()
 *    {
 *
 * 		return new Docket(DocumentationType.SWAGGER_2)
 * 				.select()
 * 				.apis(RequestHandlerSelectors.basePackage("com.example.authorization"))
 * 				.build();
 *    }
 * */
