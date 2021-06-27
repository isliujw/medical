package com.medical.gateway.apigateway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.gateway.apigateway.dao.GatewayRouteMapper;
import com.medical.gateway.apigateway.domain.GatewayRouteDO;
import com.medical.gateway.apigateway.service.GatewayRouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * <p>网关路由业务层接口实现类</p>
 * @author zengdegui
 * @date 2020年3月23日
 */
@Service
@Transactional
public class GatewayRouteServiceImpl extends ServiceImpl<GatewayRouteMapper, GatewayRouteDO> implements GatewayRouteService {


}
