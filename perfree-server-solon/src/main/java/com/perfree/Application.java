package com.perfree;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;
import org.noear.solon.scheduling.annotation.EnableAsync;


/**
 * @author Perfree
 * @description Application: 程序入口
 * @date 15:41 2023/9/28
 */
@EnableAsync
@SolonMain
@Import(scanPackages = {"com.perfree"})
public class Application {
    public static void main(String[] args) {
        Solon.start(Application.class, app->{

        });
    }
}
