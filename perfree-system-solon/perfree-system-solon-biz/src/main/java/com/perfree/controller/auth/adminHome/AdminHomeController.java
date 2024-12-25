package com.perfree.controller.auth.adminHome;

import com.perfree.commons.common.CommonResult;
import com.perfree.controller.auth.adminHome.vo.HomeStatisticRespVO;
import com.perfree.controller.auth.adminHome.vo.ServerInfoRespVO;
import com.perfree.service.adminHome.AdminHomeService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import static com.perfree.commons.common.CommonResult.success;


@Mapping("api/auth/adminHome")
@Controller
public class AdminHomeController {

    @Inject
    private AdminHomeService homeService;


    @Get
    @Mapping("/getServerInfo")
    @ApiOperation(value = "获取系统服务信息")
    public CommonResult<ServerInfoRespVO> getServerInfo() {
        return success(homeService.getServerInfo());
    }

    @Get
    @Mapping("/getHomeStatistic")
    @ApiOperation(value = "获取首页统计信息")
    public CommonResult<HomeStatisticRespVO> getHomeStatistic() {
        return success(homeService.getHomeStatistic());
    }

}
