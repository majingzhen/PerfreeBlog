package com.perfree.security;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.perfree.security.vo.LoginUserVO;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * @author Perfree
 * @description Security工具类
 * @date 15:39 2023/9/28
 */
public class SecurityFrameworkUtils {


    /**
     * 退出登录
     */
    public static void logout() {
        StpUtil.logout();
    }

    /**
     * 是否登录
     * @return
     */
    public static boolean isLogin(){
        return StpUtil.isLogin();
    }

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    @Nullable
    public static LoginUserVO getLoginUser() {
        try {
            int loginId = StpUtil.getLoginIdAsInt();
            return LoginUserVO.builder()
                    .id(loginId)
                    .account(StpUtil.getLoginIdAsString())
                    .build();
        }catch (NotLoginException e) {
            return null;
        }
    }

    public static Integer getLoginUserId() {
        try {
            return StpUtil.getLoginIdAsInt();
        }catch (NotLoginException e) {
            return null;
        }
    }
}
