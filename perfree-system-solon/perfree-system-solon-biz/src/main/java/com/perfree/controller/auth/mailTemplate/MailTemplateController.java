package com.perfree.controller.auth.mailTemplate;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.mailTemplate.vo.*;
import com.perfree.convert.mailTemplate.MailTemplateConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.MailTemplate;
import com.perfree.service.mailTemplate.MailTemplateService;
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
* @description 邮件模板 controller
* @author Perfree
**/
@Controller
@Api(tags = "邮件模板相关接口")
@Mapping("api/auth/mailTemplate")
public class MailTemplateController {

    @Inject
    private MailTemplateService mailTemplateService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "邮件模板分页列表")
    @PreAuthorize("@ss.hasPermission('admin:mailTemplate:query')")
    public CommonResult<PageResult<MailTemplateRespVO>> page(@Body MailTemplatePageReqVO pageVO) {
        PageResult<MailTemplate> mailTemplatePageResult = mailTemplateService.mailTemplatePage(pageVO);
        return success(MailTemplateConvert.INSTANCE.convertPageResultVO(mailTemplatePageResult));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加邮件模板")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailTemplate:create')")
    public CommonResult<MailTemplateRespVO> add(@Body @Valid MailTemplateAddReqVO mailTemplateAddReqVO) {
        return success(MailTemplateConvert.INSTANCE.convertRespVO(mailTemplateService.add(mailTemplateAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新邮件模板")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailTemplate:update')")
    public CommonResult<MailTemplateRespVO> update(@Body @Valid MailTemplateUpdateReqVO mailTemplateUpdateReqVO) {
        return success(MailTemplateConvert.INSTANCE.convertRespVO(mailTemplateService.update(mailTemplateUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取邮件模板")
    public CommonResult<MailTemplateRespVO> get(@Param(value = "id") Integer id) {
        return CommonResult.success(MailTemplateConvert.INSTANCE.convertRespVO(mailTemplateService.get(id)));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除邮件模板")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:mailTemplate:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return CommonResult.success(mailTemplateService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有邮件模板")
    public CommonResult<List<MailTemplateRespVO>> listAll() {
        return CommonResult.success(MailTemplateConvert.INSTANCE.convertListRespVO(mailTemplateService.listAll()));
    }

    @Post
    @Mapping("/export")
    @ApiOperation(value = "导出邮件模板")
    @PreAuthorize("@ss.hasPermission('admin:mailTemplate:export')")
    public void export(@Body MailTemplateExportReqVO exportReqVO, HttpServletResponse response) {
        List<MailTemplate> mailTemplateList = mailTemplateService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, MailTemplateConvert.INSTANCE.convertToExcelVOList(mailTemplateList), MailTemplateExcelVO.class, "邮件模板数据","邮件模板数据.xlsx");
    }

    @Post
    @Mapping("/testMail")
    @ApiOperation(value = "发送测试邮件")
    @PreAuthorize("@ss.hasPermission('admin:mailTemplate:testMail')")
    public CommonResult<Boolean> testMail(@Body @Valid MailTemplateTestReqVO mailTemplateTestReqVO) {
        return CommonResult.success(mailTemplateService.testMail(mailTemplateTestReqVO));
    }
}