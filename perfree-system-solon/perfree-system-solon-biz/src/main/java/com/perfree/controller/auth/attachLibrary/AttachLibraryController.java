package com.perfree.controller.auth.attachLibrary;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.attachLibrary.vo.*;
import com.perfree.convert.attachLibrary.AttachLibraryConvert;
import com.perfree.model.AttachLibrary;
import com.perfree.service.attachLibrary.AttachLibraryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.noear.solon.annotation.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import com.perfree.commons.excel.ExcelUtils;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 附件库 controller
* @author Perfree
**/
@Controller
@Tag(name = "附件库相关接口")
@Mapping("api/auth/attachLibrary")
public class AttachLibraryController {

    @Inject
    private AttachLibraryService attachLibraryService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "附件库分页列表")
    public CommonResult<PageResult<AttachLibraryRespVO>> page(@Body AttachLibraryPageReqVO pageVO) {
        PageResult<AttachLibraryRespVO> attachLibraryPageResult = attachLibraryService.attachLibraryPage(pageVO);
        return success(attachLibraryPageResult);
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加附件库")
    @PreAuthorize("@ss.hasPermission('admin:attachLibrary:create')")
    public CommonResult<AttachLibraryRespVO> add(@Body @Valid AttachLibraryAddReqVO attachLibraryAddReqVO) {
        return success(AttachLibraryConvert.INSTANCE.convertRespVO(attachLibraryService.add(attachLibraryAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新附件库")
    @PreAuthorize("@ss.hasPermission('admin:attachLibrary:update')")
    public CommonResult<AttachLibraryRespVO> update(@Body @Valid AttachLibraryUpdateReqVO attachLibraryUpdateReqVO) {
        return success(AttachLibraryConvert.INSTANCE.convertRespVO(attachLibraryService.update(attachLibraryUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取附件库")
    public CommonResult<AttachLibraryRespVO> get(@Param(value = "id") Integer id) {
        return success(attachLibraryService.get(id));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除附件库")
    @PreAuthorize("@ss.hasPermission('admin:attachLibrary:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(attachLibraryService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有附件库")
    public CommonResult<List<AttachLibraryRespVO>> listAll() {
        return success(AttachLibraryConvert.INSTANCE.convertListRespVO(attachLibraryService.listAll()));
    }

    @Post
    @Mapping("/export")
    @ApiOperation(value = "导出附件库")
    @PreAuthorize("@ss.hasPermission('admin:attachLibrary:export')")
    public void export(@Body AttachLibraryExportReqVO exportReqVO, HttpServletResponse response) {
        List<AttachLibrary> attachLibraryList = attachLibraryService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, AttachLibraryConvert.INSTANCE.convertToExcelVOList(attachLibraryList), AttachLibraryExcelVO.class, "附件库数据","附件库数据.xlsx");
    }
}