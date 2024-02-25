package com.bookstore.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CustomWebFilter implements WebFilter {

    private final static List<String> gateway404Headers = List.of("transfer-encoding", "Content-Type", "Content-Length");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getResponse().beforeCommit(() -> {
            if(isBlockedByGateway(exchange.getResponse())) {
                addCors(exchange);
            }
            return Mono.empty();
        });
        return chain.filter(exchange);
    }

    private boolean isBlockedByGateway(ServerHttpResponse response) {
        return response.getStatusCode()== HttpStatus.NOT_FOUND && response.getHeaders().size()==3
                && checkHeaders(response.getHeaders());
    }

    private boolean checkHeaders(HttpHeaders headers) {
        AtomicBoolean allHeadersValid = new AtomicBoolean(true);
        headers.forEach((header, headerUtils) -> {
            allHeadersValid.set(allHeadersValid.get() && gateway404Headers.contains(header));
        });
        return allHeadersValid.get();
    }

    public void addCors(ServerWebExchange exchange) {
        if (CorsUtils.isCorsRequest(exchange.getRequest())) {
            HttpHeaders headers = exchange.getResponse().getHeaders();
            List<String> headerslist = exchange.getRequest().getHeaders().get("Origin");
            if(headerslist != null && headerslist.size() == 1) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, headerslist.get(0));
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            }
        }
    }
}