package com.perfree.security.interceptor;

import org.jetbrains.annotations.NotNull;
import org.noear.solon.Solon;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

public class CustomBeanResolver implements BeanResolver {
    @NotNull
    @Override
    public Object resolve(@NotNull EvaluationContext context, @NotNull String beanName) throws AccessException {
        return Solon.context().getBean(beanName);
    }
}
