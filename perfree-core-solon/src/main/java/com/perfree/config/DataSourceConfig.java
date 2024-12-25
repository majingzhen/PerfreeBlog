package com.perfree.config;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

/**
 * @ClassName DataSourceConfig
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 17:56
 */
@Configuration
public class DataSourceConfig {
    @Bean
    public HikariDataSource hikariDataSource(@Inject("perfree") HikariDataSource hikariDataSource) {
        return hikariDataSource;
    }
}
