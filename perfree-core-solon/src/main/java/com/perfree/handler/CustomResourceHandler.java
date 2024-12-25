package com.perfree.handler;

import org.noear.snack.core.Context;
import org.noear.snack.core.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @ClassName CustomResourceHandler
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 18:09
 */

public class CustomResourceHandler implements Handler {
    @Override
    public void handle(Context context) throws Exception {
        //TODO 自定义资源处理器
        System.out.println("CustomResourceHandler");
    }
}
