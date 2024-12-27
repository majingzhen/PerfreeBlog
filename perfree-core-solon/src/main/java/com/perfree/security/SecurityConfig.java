package com.perfree.security;

import com.perfree.security.filter.JwtAuthorizationFilter;
import com.perfree.security.impl.AuthProcessorImpl;
import com.perfree.security.service.SecurityFrameworkService;
import com.perfree.security.service.SecurityFrameworkServiceImpl;
import com.perfree.security.solon.SecurityProperties;
import com.perfree.system.api.permission.PermissionApi;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.noear.solon.annotation.Condition;
import org.noear.solon.annotation.Inject;
import org.noear.solon.auth.AuthAdapter;
import org.noear.solon.auth.AuthRule;
import org.noear.solon.auth.impl.AuthRuleImpl;
import org.noear.solon.core.handle.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

import static java.time.zone.ZoneRulesProvider.getRules;
import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

/**
 * @author Perfree
 * @description Security配置
 * @date 15:37 2023/9/28
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalAuthentication
@EnableMethodSecurity
public class SecurityConfig {

    private final CorsFilter corsFilter;

    @Inject
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean("ss")
    public SecurityFrameworkService securityFrameworkService(PermissionApi permissionApi) {
        return new SecurityFrameworkServiceImpl(permissionApi);
    }

    @org.noear.solon.annotation.Bean
    public SecurityProperties securityProperties(@Inject("${perfree.security}") SecurityProperties securityProperties) {
        return securityProperties;
    }
    /**
     * 鉴权处理
     * @return
     */
    @org.noear.solon.annotation.Bean
    @Condition(onClass = SecurityProperties.class)
    public AuthAdapter authAdapter(SecurityProperties securityProperties) {
        AuthProcessorImpl authProcessor = new AuthProcessorImpl();
        authProcessor.setIpBlackList(securityProperties.getIps());
        return new AuthAdapter()
                // 添加规则
                .addRules(getRules(securityProperties))
                // 设定鉴权处理器
                .processor(authProcessor)
                // 设定默认的验证失败处理
                .failure(Context::render);
    }

    /**
     * 定义规则
     * @return
     */
    private List<AuthRule> getRules(SecurityProperties securityProperties) {
        List<AuthRule> list = new ArrayList<>();

        // 所有请求，校验 IP
        AuthRuleImpl ipRule = new AuthRuleImpl();
        AuthRule verifyIp = ipRule.include("/**").verifyIp();
        list.add(verifyIp);

        // 所有请求，排除白名单，校验权限字符（校验登录）
        AuthRuleImpl loginRule = new AuthRuleImpl();
        loginRule.include("/api/auth/**");
        for (String path : securityProperties.getWhites()) {
            loginRule.exclude(path);
        }
        AuthRule verifyPermissions = loginRule.verifyPermissions();
        list.add(verifyPermissions);

        return list;
    }

    @Bean
    public HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/**").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(IF_REQUIRED))
                //  配置跨域
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                //  将配置交由JWT
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
