package com.perfree.controller.auth.plugins;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.plugins.vo.InstallPluginReqVO;
import com.perfree.controller.auth.plugins.vo.PluginsPageReqVO;
import com.perfree.controller.auth.plugins.vo.PluginsRespVO;
import com.perfree.convert.plugins.PluginsConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.Plugins;
import com.perfree.plugin.commons.PluginSetting;
import com.perfree.service.plugins.PluginsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "插件相关接口")
@Mapping("api/auth/plugins")
public class PluginsController {

    @Inject
    private PluginsService pluginsService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "插件分页列表")
    @PreAuthorize("@ss.hasPermission('admin:plugin:query')")
    public CommonResult<PageResult<PluginsRespVO>> page(@Body PluginsPageReqVO pageVO) {
        PageResult<Plugins> pluginsPageResult = pluginsService.pluginsPage(pageVO);
        return success(PluginsConvert.INSTANCE.convertPageResultVO(pluginsPageResult));
    }


    @Post
    @Mapping("/installPlugin")
    @ApiOperation(value = "插件安装")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:plugin:install')")
    public CommonResult<Boolean> installPlugin(InstallPluginReqVO installPluginReqVO) {
        return success( pluginsService.installPlugin(installPluginReqVO.getFile()));
    }

    @Post
    @Mapping("/disablePlugin")
    @ApiOperation(value = "插件禁用")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:plugin:disable')")
    public CommonResult<Boolean> disablePlugin(@Param(value = "pluginId") String pluginId) {
        return success( pluginsService.disablePlugin(pluginId));
    }

    @Post
    @Mapping("/enablePlugin")
    @ApiOperation(value = "插件启用")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:plugin:enable')")
    public CommonResult<Boolean> enablePlugin(@Param(value = "pluginId") String pluginId) {
        return success( pluginsService.enablePlugin(pluginId));
    }

    @Post
    @Mapping("/uninstallPlugin")
    @ApiOperation(value = "卸载插件")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:plugin:uninstall')")
    public CommonResult<Boolean> uninstallPlugin(@Param(value = "pluginId") String pluginId) {
        return success( pluginsService.unInstallPlugin(pluginId));
    }

    @Get
    @Mapping("/getPluginSetting")
    @ApiOperation(value = "获取插件设置项")
    public CommonResult<PluginSetting> getPluginSetting(@Param(value = "pluginId") String pluginId) {
        return success( pluginsService.getPluginSetting(pluginId));
    }

}
