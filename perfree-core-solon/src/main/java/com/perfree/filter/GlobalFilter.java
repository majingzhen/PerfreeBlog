package com.perfree.filter;


import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.io.resource.NoResourceException;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.enums.ResultCodeEnum;
import com.perfree.commons.exception.ServiceException;
import com.perfree.demoModel.DemoModelException;

import org.noear.solon.annotation.Component;
import org.noear.solon.auth.AuthException;
import org.noear.solon.auth.AuthStatus;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

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
            log.error(e.getMessage(), e);
            ctx.render(CommonResult.error(status.code, status.message));
        } catch (NoResourceFoundException e) {
            ctx.redirect("/404");
        } catch (MethodArgumentNotValidException e) {
            BindingResult result = e.getBindingResult();
            Objects.requireNonNull(result.getFieldError());
            log.error(result.getFieldError().getDefaultMessage());
            ctx.render(CommonResult.error(ResultCodeEnum.FAIL.getCode(), result.getFieldError().getDefaultMessage()));
        } catch (HttpMessageConversionException | DemoModelException | AccessDeniedException e) {
            log.error(e.getMessage(), e);
            ctx.render(CommonResult.error(ResultCodeEnum.FAIL.getCode(), e.getMessage()));
        }
//        catch (BindException e) {
//           List<ObjectError> list = ((BindException) e).getAllErrors();
//            if (!list.isEmpty()) {
//                log.error(list.get(0).getDefaultMessage());
//             ctx.render(CommonResult.error(ResultCodeEnum.FAIL.getCode(), list.get(0).getDefaultMessage()));
//            }
//        }
        catch (ServiceException e) {
            log.error(e.getMessage(),e);
            ctx.render(CommonResult.error(e.getCode(), e.getMessage()));
        }
        catch (IOException e) {
            if (e.getMessage().contains("Connection reset by peer")) {
                ctx.render(CommonResult.error(ResultCodeEnum.FAIL.getCode(), e.getMessage()));
            }
            log.error(e.getMessage(), e);
            ctx.render(CommonResult.error(ResultCodeEnum.FAIL.getCode(), e.getMessage()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ctx.render(CommonResult.error(ResultCodeEnum.FAIL.getCode(), e.getMessage()));
        }
        long times = System.currentTimeMillis() - start;
        log.info("请求【{}】【{}】完成，耗时:【{}ms】", ctx.path(), ctx.method(), times);
    }
}
