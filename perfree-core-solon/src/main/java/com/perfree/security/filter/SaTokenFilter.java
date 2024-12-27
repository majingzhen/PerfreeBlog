package com.perfree.security.filter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.enums.ResultCodeEnum;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.scheduling.annotation.Command;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRotX;

/**
 * @ClassName SaTokenFilter
 * @Description SaToken 认证过滤器
 * @Author Majz
 * @Date 2024/12/27 14:08
 */
@Component
public class SaTokenFilter implements Filter {
    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        try{
            String path = ctx.path();
            if (isWhitelist(path)) {
                // 白名单路径，直接放行
                chain.doFilter(ctx);
            } else {
                // 非白名单路径，需要进行认证
                StpUtil.checkLogin();
                chain.doFilter(ctx);
            }
        }catch (NotLoginException e) {
            ctx.render(CommonResult.error(
                    ResultCodeEnum.AUTH_UNAUTHORIZED.getCode(),
                    "未登录或token已过期"
            ));
        }
    }

    /**
     * 判断是否为白名单路径
     */
    private boolean isWhitelist(String path) {
        // 登录相关接口
        if (path.startsWith("/api/login") ||
                path.startsWith("/api/captchaImage")) {
            return true;
        }
        // 其他白名单路径...
        return false;
    }
}
