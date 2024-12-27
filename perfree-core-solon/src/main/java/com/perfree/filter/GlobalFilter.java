package com.perfree.filter;


import cn.dev33.satoken.exception.NotLoginException;
import com.perfree.commons.enums.ResultCodeEnum;
import org.noear.solon.annotation.Component;
import org.noear.solon.auth.AuthException;
import org.noear.solon.auth.AuthStatus;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName GlobalFilter
 * @Description TODO
 * @Author Majz
 * @Date 2024/12/25 10:38
 */
@Component
public class GlobalFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(GlobalFilter.class);

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            chain.doFilter(ctx);
        } catch (AuthException e) {
            // 鉴权异常
            AuthStatus status = e.getStatus();
            ctx.render(Result.failure(status.code, status.message));
        } catch (Exception ex) {
            // 其它异常
            ctx.render(Result.failure(ResultCodeEnum.FAIL.getCode(), ex.getMessage()));
        }
        long times = System.currentTimeMillis() - start;
        log.info("请求【{}】【{}】完成，耗时:【{}ms】", ctx.path(), ctx.method(), times);
    }
}
