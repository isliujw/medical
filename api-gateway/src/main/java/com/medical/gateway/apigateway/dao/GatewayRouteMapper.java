package com.medical.gateway.apigateway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.gateway.apigateway.domain.GatewayRouteDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>开放网关-路由数据访问层接口类</p>
 * @author zengdegui
 * @date 2018年9月1日
 */
@Mapper
public interface GatewayRouteMapper extends BaseMapper<GatewayRouteDO> {
	
}
