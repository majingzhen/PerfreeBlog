package com.perfree.apiImpl.plugin;

import com.perfree.service.plugins.PluginsService;
import com.perfree.system.api.plugin.dto.PluginApi;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.noear.solon.annotation.Component;

@Component
public class PluginApiImpl implements PluginApi {

    private final static Logger LOGGER = LoggerFactory.getLogger(PluginApiImpl.class);

    @Inject
    private PluginsService pluginsService;

    @Override
    public void initDevPlugin(String pluginPath) {
        try {
            pluginsService.initDevPlugin(pluginPath);
        } catch (Exception e) {
            LOGGER.info("重新启动插件出错!", e);
        }
    }
}