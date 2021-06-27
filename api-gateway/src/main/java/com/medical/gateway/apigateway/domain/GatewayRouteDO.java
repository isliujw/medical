package com.medical.gateway.apigateway.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "gateway_route", autoResultMap = true)
public class GatewayRouteDO implements Serializable {

    private Integer id;


    private String serviceId;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 路由地址
     */
    private String uri;

    /**
     * 路由断言
     */
    private String predicates;

    /**
     * 路由过滤规则
     */
    private String filters;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

}
