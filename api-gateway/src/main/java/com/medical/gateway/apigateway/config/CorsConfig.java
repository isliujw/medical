package com.medical.gateway.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * <p>跨域相关配置</p>
 * 
 * @author zengdegui
 * @date 2019年6月12日
 */
@Configuration
public class CorsConfig {

	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			ServerHttpResponse response = ctx.getResponse();

			if (!CorsUtils.isCorsRequest(request)) {
				return chain.filter(ctx);
			}

			HttpHeaders requestHeaders = request.getHeaders();
			HttpHeaders headers = response.getHeaders();
			HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
			
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
			String accessControlRequestHeaders = requestHeaders.getAccessControlRequestHeaders().stream().collect(Collectors.joining(","));
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, accessControlRequestHeaders);
			if (requestMethod != null) {
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
			}
			if (request.getMethod() == HttpMethod.OPTIONS) {
				response.setStatusCode(HttpStatus.OK);
				return Mono.empty();
			}
			return chain.filter(ctx);
		};
	}
}