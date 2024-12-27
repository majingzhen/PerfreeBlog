package com.perfree.security;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.solon.integration.SaTokenFilter;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;

/**
 * @ClassName SaTokenConfigure
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 22:53
 */
@Configuration
public class SaTokenConfigure {

    /**
    /**
     * Sa-Token 参数配置，参考文档：URL_ADDRESS     * Sa-Token 参数配置，参考文档：https://sa-token.cc
     */
    @Bean
    public SaTokenConfig getSaTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        // token名称 (同时也是cookie名称)
        config.setTokenName("Authorization");
        // token有效期 30天
        config.setTimeout(30 * 24 * 60 * 60);
        // token临时有效期 (指定时间内无操作就视为token过期) 默认-1代表不限制
        config.setActivityTimeout(-1);
        // 是否允许同一账号并发登录
        config.setIsConcurrent(true);
        // 在多人登录同一账号时，是否共用一个token
        config.setIsShare(false);
        // token风格
        config.setTokenStyle("uuid");
        return config;
    }
}
