package com.perfree.security.solon;

import lombok.Data;

/**
 * @ClassName SecurityProperties
 * @Description 安全Properties
 * @Author Majz
 * @Date 2024/12/25 20:02
 */
@Data
public class SecurityProperties {

    /**
     * IP黑名单
     */
    private String[] ips = new String[]{};

    /**
     * 放行白名单
     */
    private String[] whites = new String[]{};
}
