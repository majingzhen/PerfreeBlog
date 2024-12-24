package com.perfree;

import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.annotation.SolonMain;
import org.noear.solon.scheduling.annotation.EnableAsync;
import org.springframework.boot.SpringApplication;


/**
 * @author Perfree
 * @description Application: 程序入口
 * @date 15:41 2023/9/28
 */
@EnableAsync
@SolonMain
public class Application {
    public static void main(String[] args) {
        Solon.start(Application.class, args);
    }
}
