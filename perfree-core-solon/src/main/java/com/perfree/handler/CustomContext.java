package com.perfree.handler;

import org.noear.solon.core.AppContext;
import org.noear.solon.core.handle.Context;

/**
 * @ClassName CustomContext
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 18:12
 */
public class CustomContext {

    private final AppContext context;
    private final String location;

    public CustomContext(AppContext context, String location) {
        this.context = context;
        this.location = location;
    }

    public AppContext getContext() {
        return context;
    }

    public String getLocation() {
        return location;
    }

    // 你可以在这里添加更多的方法来处理自定义逻辑
    public void processRequest() {

    }}
