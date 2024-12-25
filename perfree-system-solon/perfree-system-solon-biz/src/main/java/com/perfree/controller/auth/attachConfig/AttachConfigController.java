package com.perfree.controller.auth.attachConfig;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.attachConfig.vo.*;
import com.perfree.convert.attachConfig.AttachConfigConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.AttachConfig;
import com.perfree.service.attachConfig.AttachConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Mapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "附件服务器配置相关接口")
@Mapping("api/auth/attachConfig")
public class AttachConfigController {

    @Inject
    private AttachConfigService attachConfigService;

    @Get
    @Mapping("/getAll")
    @ApiOperation(value = "获取所有配置")
    public CommonResult<List<AttachConfigRespVO>> getAll() {
        List<AttachConfig> attachConfigList = attachConfigService.getAll();
        return CommonResult.success(AttachConfigConvert.INSTANCE.convertRespListVO(attachConfigList));
    }

    @Post
    @Mapping("/page")
    @ApiOperation(value = "配置分页列表")
    @PreAuthorize("@ss.hasPermission('admin:attachConfig:query')")
    public CommonResult<PageResult<AttachConfigRespVO>> page(@Body AttachConfigPageReqVO pageVO) {
        PageResult<AttachConfig> attachPage = attachConfigService.attachConfigPage(pageVO);
        return success(AttachConfigConvert.INSTANCE.convertPageResultVO(attachPage));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "新增配置")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attachConfig:create')")
    public CommonResult<AttachConfigRespVO> add(@Body @Valid AttachConfigCreateVO attachConfigCreateVO) {
        AttachConfig attachConfig = attachConfigService.add(attachConfigCreateVO);
        return CommonResult.success(AttachConfigConvert.INSTANCE.convertRespVO(attachConfig));
    }


    @Put
    @Mapping("/update")
    @ApiOperation(value = "修改配置")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attachConfig:update')")
    public CommonResult<Boolean> update(@Body @Valid AttachConfigUpdateVO attachConfigUpdateVO) {
        return CommonResult.success(attachConfigService.updateAttachConfig(attachConfigUpdateVO));
    }

    @Put
    @Mapping("/updateMaster")
    @ApiOperation(value = "修改默认配置")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attachConfig:master')")
    public CommonResult<Boolean> updateMaster(@Body @Valid AttachConfigUpdateMasterVO attachConfigUpdateMasterVO) {
        return CommonResult.success(attachConfigService.updateMaster(attachConfigUpdateMasterVO));
    }


    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取配置")
    public CommonResult<AttachConfigRespVO> get(@Param(value = "id") Integer id) {
        AttachConfig attachConfig = attachConfigService.getById(id);
        return CommonResult.success(AttachConfigConvert.INSTANCE.convertRespVO(attachConfig));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除配置")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attachConfig:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return CommonResult.success(attachConfigService.del(id));
    }
}
