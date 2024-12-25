package com.perfree.controller.auth.mailServer;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.mailServer.vo.*;
import com.perfree.convert.mailServer.MailServerConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.MailServer;
import com.perfree.service.mailServer.MailServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.servlet.http.HttpServletResponse;
import com.perfree.commons.excel.ExcelUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 邮箱服务 controller
* @author Perfree
**/
@Controller
@Api(tags = "邮箱服务相关接口")
@Mapping("api/auth/mailServer")
public class MailServerController {

    @Inject
    private MailServerService mailServerService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "邮箱服务分页列表")
    @PreAuthorize("@ss.hasPermission('admin:mailServer:query')")
    public CommonResult<PageResult<MailServerRespVO>> page(@Body MailServerPageReqVO pageVO) {
        PageResult<MailServer> mailServerPageResult = mailServerService.mailServerPage(pageVO);
        return success(MailServerConvert.INSTANCE.convertPageResultVO(mailServerPageResult));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加邮箱服务")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailServer:create')")
    public CommonResult<MailServerRespVO> add(@Body @Valid MailServerAddReqVO mailServerAddReqVO) {
        return success(MailServerConvert.INSTANCE.convertRespVO(mailServerService.add(mailServerAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新邮箱服务")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailServer:update')")
    public CommonResult<MailServerRespVO> update(@Body @Valid MailServerUpdateReqVO mailServerUpdateReqVO) {
        return success(MailServerConvert.INSTANCE.convertRespVO(mailServerService.update(mailServerUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取邮箱服务")
    public CommonResult<MailServerRespVO> get(@Param(value = "id") Integer id) {
        return CommonResult.success(MailServerConvert.INSTANCE.convertRespVO(mailServerService.get(id)));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除邮箱服务")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailServer:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return CommonResult.success(mailServerService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有邮箱服务")
    public CommonResult<List<MailServerRespVO>> listAll() {
        return CommonResult.success(MailServerConvert.INSTANCE.convertListRespVO(mailServerService.listAll()));
    }

    @Post
    @Mapping("/export")
    @ApiOperation(value = "导出邮箱服务")
    @PreAuthorize("@ss.hasPermission('admin:mailServer:export')")
    public void export(@Body MailServerExportReqVO exportReqVO, HttpServletResponse response) {
        List<MailServer> mailServerList = mailServerService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, MailServerConvert.INSTANCE.convertToExcelVOList(mailServerList), MailServerExcelVO.class, "邮箱服务数据","邮箱服务数据.xlsx");
    }
}