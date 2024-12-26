package com.perfree.security.impl;

import cn.hutool.core.util.ArrayUtil;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Inject;
import org.noear.solon.auth.AuthProcessor;
import org.noear.solon.auth.AuthUtil;
import org.noear.solon.auth.annotation.Logical;
import org.noear.solon.boot.web.SessionStateBase;
import org.noear.solon.core.handle.SessionState;

/**
 * @ClassName AuthProcessorImpl
 * @Description 鉴权处理器
 * @Author Majz
 * @Date 2024/12/25 20:03
 */
public class AuthProcessorImpl implements AuthProcessor {

    private String[] ipBlackList;
    public void setIpBlackList(String[] ipBlackList) {
        this.ipBlackList = ipBlackList;
    }
    @Override
    public boolean verifyIp(String ip) {
        return !ArrayUtil.contains(ipBlackList, ip);
    }

    @Override
    public boolean verifyLogined() {
        return AuthUtil.verifyLogined();
    }

    @Override
    public boolean verifyPath(String path, String method) {
        return false;
    }

    @Override
    public boolean verifyPermissions(String[] permissions, Logical logical) {
        return false;
    }

    @Override
    public boolean verifyRoles(String[] roles, Logical logical) {
        return false;
    }
}
