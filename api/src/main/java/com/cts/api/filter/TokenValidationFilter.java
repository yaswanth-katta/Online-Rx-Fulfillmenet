package com.cts.api.filter;

import com.cts.api.dto.ValidatingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenValidationFilter implements GatewayFilter {
	
	private final WebClient.Builder authClient;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
		ServerHttpRequest request=exchange.getRequest();
		String jwt=request.getHeaders()
					.getOrEmpty("Authorization")
					.get(0)
					.substring(7);
		log.info(jwt);
		
		return authClient
				.baseUrl("http://localhost:5400/auth")
				.defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(jwt))
				.build()
				.get()
				.uri("/validateToken")
				.retrieve()
				.bodyToMono(ValidatingDto.class)
				.then(chain.filter(exchange));
		
	}
}
