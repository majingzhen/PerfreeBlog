package com.perfree.controller.auth.attachLibraryItems;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.attachLibraryItems.vo.*;
import com.perfree.convert.attachLibraryItems.AttachLibraryItemsConvert;
import com.perfree.model.AttachLibraryItems;
import com.perfree.service.attachLibraryItems.AttachLibraryItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Mapping;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.servlet.http.HttpServletResponse;
import com.perfree.commons.excel.ExcelUtils;
import org.noear.solon.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 附件库数据 controller
* @author Perfree
**/
@Controller
@Api(tags = "附件库数据相关接口")
@Mapping("api/auth/attachLibraryItems")
public class AttachLibraryItemsController {

    @Inject
    private AttachLibraryItemsService attachLibraryItemsService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "附件库数据分页列表")
    public CommonResult<PageResult<AttachLibraryItemsRespVO>> page(@Body AttachLibraryItemsPageReqVO pageVO) {
        PageResult<AttachLibraryItemsRespVO> attachLibraryItemsPageResult = attachLibraryItemsService.attachLibraryItemsPage(pageVO);
        return success(attachLibraryItemsPageResult);
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:create')")
    public CommonResult<AttachLibraryItemsRespVO> add(@Body @Valid AttachLibraryItemsAddReqVO attachLibraryItemsAddReqVO) {
        return success(AttachLibraryItemsConvert.INSTANCE.convertRespVO(attachLibraryItemsService.add(attachLibraryItemsAddReqVO)));
    }

    @Post
    @Mapping("/batchAdd")
    @ApiOperation(value = "批量添加附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:batchAdd')")
    public CommonResult<List<AttachLibraryItemsRespVO>> batchAdd(@Body @Valid AttachLibraryItemsBatchAddReqVO attachLibraryItemsAddReqVO) {
        return success(AttachLibraryItemsConvert.INSTANCE.convertListRespVO(attachLibraryItemsService.batchAdd(attachLibraryItemsAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:update')")
    public CommonResult<AttachLibraryItemsRespVO> update(@Body @Valid AttachLibraryItemsUpdateReqVO attachLibraryItemsUpdateReqVO) {
        return success(AttachLibraryItemsConvert.INSTANCE.convertRespVO(attachLibraryItemsService.update(attachLibraryItemsUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取附件库数据")
    public CommonResult<AttachLibraryItemsRespVO> get(@Param(value = "id") Integer id) {
        return success(attachLibraryItemsService.get(id));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(attachLibraryItemsService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有附件库数据")
    public CommonResult<List<AttachLibraryItemsRespVO>> listAll() {
        return success(AttachLibraryItemsConvert.INSTANCE.convertListRespVO(attachLibraryItemsService.listAll()));
    }

    @Post
    @Mapping("/export")
    @ApiOperation(value = "导出附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:export')")
    public void export(@Body AttachLibraryItemsExportReqVO exportReqVO, HttpServletResponse response) {
        List<AttachLibraryItems> attachLibraryItemsList = attachLibraryItemsService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, AttachLibraryItemsConvert.INSTANCE.convertToExcelVOList(attachLibraryItemsList), AttachLibraryItemsExcelVO.class, "附件库数据数据","附件库数据数据.xlsx");
    }
}