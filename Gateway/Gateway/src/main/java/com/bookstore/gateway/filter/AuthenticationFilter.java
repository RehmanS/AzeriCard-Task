package com.bookstore.gateway.filter;

import com.bookstore.gateway.exception.UnauthorizedException;
import com.bookstore.gateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header!");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                boolean isValid = jwtUtil.validateToken(authHeader);
                if (!isValid) {
                    throw new UnauthorizedException("Access denied. You don't have permission to access");
                }
                exchange.getRequest().mutate()
                        .header("X-Username", "Stefan")
                        .build();
            }


            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
