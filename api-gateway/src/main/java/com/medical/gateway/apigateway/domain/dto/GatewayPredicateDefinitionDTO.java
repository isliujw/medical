package com.medical.gateway.apigateway.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>路由断言</p>
 * @author zengdegui
 * @date 2020年4月15日
 */
@Data
public class GatewayPredicateDefinitionDTO implements Serializable {
	
	private static final long serialVersionUID = 7304014187681107749L;

	private String name;
	
    private Map<String, String> args;

}
