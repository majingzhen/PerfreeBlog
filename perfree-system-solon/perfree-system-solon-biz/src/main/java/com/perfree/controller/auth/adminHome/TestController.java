package com.perfree.controller.auth.adminHome;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 10:26
 */
@Controller
@Mapping("test")
public class TestController {

    @Mapping("test")
    public String test(){
        return "test";
    }
}
