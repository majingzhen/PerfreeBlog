package com.demo.controller.auth.pluginDemo;


import com.demo.controller.auth.pluginDemo.vo.*;
import com.demo.convert.pluginDemo.PluginDemoConvert;
import com.demo.model.PluginDemo;
import com.demo.service.pluginDemo.PluginDemoService;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.excel.ExcelUtils;
import com.perfree.security.annotation.PluginPreAuthorize;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 测试 controller
* @author Perfree
**/
@Controller
@Api(tags = "测试相关接口")
@Mapping("api/auth/pluginDemo")
public class PluginDemoController {

    @Inject
    private PluginDemoService pluginDemoService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "测试分页列表2")
    @PluginPreAuthorize("@ss.hasPermission('admin:pluginDemo:query')")
    public CommonResult<PageResult<PluginDemoRespVO>> page(@Body PluginDemoPageReqVO pageVO) {
        PageResult<PluginDemo> pluginDemoPageResult = pluginDemoService.pluginDemoPage(pageVO);
        return success(PluginDemoConvert.INSTANCE.convertPageResultVO(pluginDemoPageResult));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加测试")
    @PluginPreAuthorize("@ss.hasPermission('admin:pluginDemo:create')")
    public CommonResult<PluginDemoRespVO> add(@Body @Valid PluginDemoAddReqVO pluginDemoAddReqVO) {
        return success(PluginDemoConvert.INSTANCE.convertRespVO(pluginDemoService.add(pluginDemoAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新测试")
    @PluginPreAuthorize("@ss.hasPermission('admin:pluginDemo:update')")
    public CommonResult<PluginDemoRespVO> update(@Body @Valid PluginDemoUpdateReqVO pluginDemoUpdateReqVO) {
        return success(PluginDemoConvert.INSTANCE.convertRespVO(pluginDemoService.update(pluginDemoUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取测试")
    public CommonResult<PluginDemoRespVO> get(@Param(value = "id") Integer id) {
        return success(PluginDemoConvert.INSTANCE.convertRespVO(pluginDemoService.get(id)));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除测试")
    @PluginPreAuthorize("@ss.hasPermission('admin:pluginDemo:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(pluginDemoService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有测试")
    public CommonResult<List<PluginDemoRespVO>> listAll() {
        return success(PluginDemoConvert.INSTANCE.convertListRespVO(pluginDemoService.listAll()));
    }

    @Post
    @Mapping("/export")
    @ApiOperation(value = "导出测试")
    @PluginPreAuthorize("@ss.hasPermission('admin:pluginDemo:export')")
    public void export(@Body PluginDemoExportReqVO exportReqVO, HttpServletResponse response) {
        List<PluginDemo> pluginDemoList = pluginDemoService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, PluginDemoConvert.INSTANCE.convertToExcelVOList(pluginDemoList), PluginDemoExcelVO.class, "测试数据","测试数据.xlsx");
    }
}