package com.medical.gateway.apigateway.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>路由过滤规则</p>
 * @author zengdegui
 * @date 2020年4月15日
 */
@Data
public class GatewayFilterDefinitionDTO implements Serializable {
	
	private static final long serialVersionUID = -4725370522079418572L;

	private String name;
	
    private Map<String, String> args;

}
