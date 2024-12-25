package com.perfree.controller.auth.system;

import com.perfree.cache.OptionCacheService;
import com.perfree.commons.common.CommonResult;
import com.perfree.controller.auth.option.vo.OptionRespVO;
import com.perfree.controller.auth.system.vo.LoginUserInfoRespVO;
import com.perfree.controller.auth.system.vo.MenuTreeListRespVO;
import com.perfree.convert.option.OptionConvert;
import com.perfree.service.menu.MenuService;
import com.perfree.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Get;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 系统基础接口
 * @author Perfree
 * @version 1.0.0
 * @create 2023/9/28 10:16
 **/
@Controller
@Api(tags = "系统基础接口")
@Mapping("api/auth")
public class SystemController {
    @Inject
    private UserService userService;

    @Inject
    private MenuService menuService;

    @Inject
    private OptionCacheService optionCacheService;


    @Get
    @Mapping("menuAdminList")
    @ApiOperation(value = "获取当前账号拥有的菜单")
    public CommonResult<List<MenuTreeListRespVO>> menuList(){
        return CommonResult.success(menuService.menuAdminListByLoginUser());
    }

    @Get
    @Mapping("getAllOption")
    @ApiOperation(value = "获取所有配置信息")
    public CommonResult<List<OptionRespVO>> getAllOption(){
        return CommonResult.success(OptionConvert.INSTANCE.convertCacheDTO2RespListVO(optionCacheService.getAllOption()));
    }

    @Get
    @Mapping("userInfo")
    @ApiOperation(value = "获取当前登录账号的信息")
    public CommonResult<LoginUserInfoRespVO> userInfo(){
        return CommonResult.success(userService.userInfo());
    }

    @Get
    @Mapping("logout")
    @ApiOperation(value = "退出登录")
    public CommonResult<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return CommonResult.success("退出成功");
    }
}
