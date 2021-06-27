package com.medical.gateway.apigateway.config;

import com.medical.gateway.apigateway.service.impl.GatewayDynamicRouteServiceImpl;
import com.medical.gateway.apigateway.service.impl.RedisRouteDefinitionRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GatewayConfig {

    /**
     * <p>
     * 路由redis存储
     * </p>
     *
     * @return
     */
    @Bean
    public RouteDefinitionRepository routeDefinitionRepository() {
        RouteDefinitionRepository routeDefinitionRepository = new RedisRouteDefinitionRepositoryImpl();
        log.info("RouteDefinitionRepository [{}]", routeDefinitionRepository);
        return routeDefinitionRepository;
    }

    /**
     * <p>
     * 动态路由实现类
     * </p>
     *
     * @return
     */
    @Bean
    public GatewayDynamicRouteServiceImpl gatewayDynamicRouteServiceImpl() {
        GatewayDynamicRouteServiceImpl gatewayDynamicRouteServiceImpl = new GatewayDynamicRouteServiceImpl();
        log.info("GatewayDynamicRouteServiceImpl [{}]", gatewayDynamicRouteServiceImpl);
        return gatewayDynamicRouteServiceImpl;
    }

}
