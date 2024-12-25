package com.perfree.commons.utils;

import com.perfree.commons.directive.TemplateDirective;
import lombok.Setter;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.AppContext;
import org.springframework.aop.framework.AopContext;

import java.util.Map;


/**
 * @ClassName SolonBeanUtil
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 17:46
 */
@Component
public class SolonBeanUtil {

    @Setter
    private static AppContext context;

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> T getBean(String beanName) {
        return context.getBean(beanName);
    }
}
