package com.cts.refillmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients

public class RefillModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefillModuleApplication.class, args);
	}

	/**
	 * @Bean
	 *                public Docket api() {
	 * 			return new Docket(DocumentationType.SWAGGER_2).select()
	 * 					.apis(RequestHandlerSelectors.basePackage("com.cts.RefillModule"))
	 * 					.paths(PathSelectors.regex("/.*"))
	 * 					.build().apiInfo(apiInfoMetaData());
	 *        }
	 * 	private ApiInfo apiInfoMetaData() {
	 *
	 * 		return new ApiInfoBuilder().title("NAME OF SERVICE")
	 * 				.description("API Endpoint Decoration")
	 * 				.contact(new Contact("Dev-Team", "https://www.dev-team.com/", "dev-team@gmail.com"))
	 * 				.license("Apache 2.0")
	 * 				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
	 * 				.version("1.0.0")
	 * 				.build();    * 	}
	 * */
}
