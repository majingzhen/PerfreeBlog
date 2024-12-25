package com.perfree.demoModel;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.noear.solon.annotation.Inject;
import org.springframework.beans.factory.annotation.Value;
import org.noear.solon.annotation.Component;

@Aspect
@Component
public class DemoModeAspect {

    @Inject("${perfree.demoModel}")
    private Boolean demoModel;

    @Before("@annotation(demoMode)")
    public void checkDemoMode(DemoMode demoMode) throws Exception {
       if (demoModel) {
           throw new DemoModelException(demoMode.message());
       }
    }
}
