package com.perfree.config;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.view.enjoy.EnjoyRender;

/**
 * @ClassName ViewConfig
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/27 18:22
 */
@Configuration
public class ViewConfig {

    @Bean
    public void initView() {
        // 配置视图文件位置
        EnjoyRender render = new EnjoyRender();
        render.mapping("/**", "/");  // 模板目录
        render.suffix(".html");      // 模板后缀

        // 注册视图解析器
        Solon.app().renderManager().register(render);
        
        // 配置静态资源映射
        Solon.app().staticList().add("/static/**", "/static/");
    }
}
