package com.perfree.controller.auth.mailLog;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.mailLog.vo.*;
import com.perfree.convert.mailLog.MailLogConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.MailLog;
import com.perfree.service.mailLog.MailLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import com.perfree.commons.excel.ExcelUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 邮件日志 controller
* @author Perfree
**/
@Controller
@Api(tags = "邮件日志相关接口")
@Mapping("api/auth/mailLog")
public class MailLogController {

    @Inject
    private MailLogService mailLogService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "邮件日志分页列表")
    @PreAuthorize("@ss.hasPermission('admin:mailLog:query')")
    public CommonResult<PageResult<MailLogRespVO>> page(@Body MailLogPageReqVO pageVO) {
        PageResult<MailLog> mailLogPageResult = mailLogService.mailLogPage(pageVO);
        return success(MailLogConvert.INSTANCE.convertPageResultVO(mailLogPageResult));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加邮件日志")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailLog:create')")
    public CommonResult<MailLogRespVO> add(@Body @Valid MailLogAddReqVO mailLogAddReqVO) {
        return success(MailLogConvert.INSTANCE.convertRespVO(mailLogService.add(mailLogAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新邮件日志")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailLog:update')")
    public CommonResult<MailLogRespVO> update(@Body @Valid MailLogUpdateReqVO mailLogUpdateReqVO) {
        return success(MailLogConvert.INSTANCE.convertRespVO(mailLogService.update(mailLogUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取邮件日志")
    public CommonResult<MailLogRespVO> get(@Param(value = "id") Integer id) {
        return CommonResult.success(MailLogConvert.INSTANCE.convertRespVO(mailLogService.get(id)));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除邮件日志")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailLog:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return CommonResult.success(mailLogService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有邮件日志")
    public CommonResult<List<MailLogRespVO>> listAll() {
        return CommonResult.success(MailLogConvert.INSTANCE.convertListRespVO(mailLogService.listAll()));
    }

    @Post
    @Mapping("/export")
    @ApiOperation(value = "导出邮件日志")
    @PreAuthorize("@ss.hasPermission('admin:mailLog:export')")
    public void export(@Body MailLogExportReqVO exportReqVO, HttpServletResponse response) {
        List<MailLog> mailLogList = mailLogService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, MailLogConvert.INSTANCE.convertToExcelVOList(mailLogList), MailLogExcelVO.class, "邮件日志数据","邮件日志数据.xlsx");
    }
}