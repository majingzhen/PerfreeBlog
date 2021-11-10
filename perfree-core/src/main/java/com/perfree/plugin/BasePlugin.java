package com.perfree.plugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * @description 插件主类基类
 * @author Perfree
 * @date 2021/11/9 14:25
 */
public abstract class BasePlugin extends Plugin{
    public BasePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}