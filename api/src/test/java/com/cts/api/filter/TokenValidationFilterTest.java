package com.cts.api.filter;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.session.WebSessionManager;

@ContextConfiguration(classes = {TokenValidationFilter.class})
@ExtendWith(SpringExtension.class)
class TokenValidationFilterTest {
    @MockBean
    private WebClient.Builder builder;

    @Autowired
    private TokenValidationFilter tokenValidationFilter;

//    @Test
//    @Disabled("TODO: Complete this test")
//    void testFilter() {
//
//        ServerHttpRequestDecorator request = new ServerHttpRequestDecorator(
//                new ServerHttpRequestDecorator(new ServerHttpRequestDecorator(null)));
//        MockServerHttpResponse response = new MockServerHttpResponse();
//        WebSessionManager sessionManager = mock(WebSessionManager.class);
//        DefaultServerCodecConfigurer codecConfigurer = new DefaultServerCodecConfigurer();
//        tokenValidationFilter.filter(new DefaultServerWebExchange(request, response, sessionManager, codecConfigurer,
//                new AcceptHeaderLocaleContextResolver()), null);
//    }
}

