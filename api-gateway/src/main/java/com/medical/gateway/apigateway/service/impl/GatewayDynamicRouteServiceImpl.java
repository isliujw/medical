package com.medical.gateway.apigateway.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medical.gateway.apigateway.domain.GatewayRouteDO;
import com.medical.gateway.apigateway.domain.dto.GatewayFilterDefinitionDTO;
import com.medical.gateway.apigateway.domain.dto.GatewayPredicateDefinitionDTO;
import com.medical.gateway.apigateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GatewayDynamicRouteServiceImpl implements ApplicationRunner, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    GatewayRouteService gatewayRouteService;

    @Autowired
    RouteDefinitionRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.refresh();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    public Mono<Void> refresh() {
        this.loadRoutes();
        // 发布事件(触发默认路由刷新事件,刷新缓存路由)
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }

    /**
     * <p>动态加载路由</p>
     * @return
     */
    private Mono<Void> loadRoutes() {
        //删除redis里面的路由配置信息
        this.stringRedisTemplate.delete(RedisRouteDefinitionRepositoryImpl.GATEWAY_ROUTES);
        QueryWrapper<GatewayRouteDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayRouteDO::getStatus, 1);
        List<GatewayRouteDO> list = this.gatewayRouteService.list(queryWrapper);
        list.stream().forEach(data -> {
            RouteDefinition definition = new RouteDefinition();
            definition.setId(data.getServiceId());
            definition.setUri(URI.create(data.getUri()));
            List<GatewayPredicateDefinitionDTO> predicateVO = JSONUtil.toList(data.getPredicates(), GatewayPredicateDefinitionDTO.class);
            List<PredicateDefinition> predicates = new ArrayList<>();
            predicateVO.stream().forEach(p -> {
                PredicateDefinition predicateDefinition = new PredicateDefinition();
                predicateDefinition.setName(p.getName());
                predicateDefinition.setArgs(p.getArgs());
                predicates.add(predicateDefinition);
            });
            definition.setPredicates(predicates);

            List<GatewayFilterDefinitionDTO> filterVO = JSONUtil.toList(data.getFilters(), GatewayFilterDefinitionDTO.class);
            List<FilterDefinition> filters = new ArrayList<>();
            filterVO.stream().forEach(f -> {
                FilterDefinition filterDefinition = new FilterDefinition();
                filterDefinition.setName(f.getName());
                filterDefinition.setArgs(f.getArgs());
                filters.add(filterDefinition);
            });
            definition.setFilters(filters);
            definition.setOrder(0);
            this.repository.save(Mono.just(definition)).subscribe();
        });
        return Mono.empty();
    }



}
