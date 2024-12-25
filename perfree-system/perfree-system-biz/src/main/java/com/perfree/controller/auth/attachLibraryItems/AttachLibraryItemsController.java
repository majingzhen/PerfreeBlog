package com.perfree.controller.auth.attachLibraryItems;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.attachLibraryItems.vo.*;
import com.perfree.convert.attachLibraryItems.AttachLibraryItemsConvert;
import com.perfree.model.AttachLibraryItems;
import com.perfree.service.attachLibraryItems.AttachLibraryItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import com.perfree.commons.excel.ExcelUtils;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 附件库数据 controller
* @author Perfree
**/
@Controller
@Tag(name = "附件库数据相关接口")
@Mapping("api/auth/attachLibraryItems")
public class AttachLibraryItemsController {

    @Resource
    private AttachLibraryItemsService attachLibraryItemsService;

    @PostMapping("/page")
    @Operation(summary = "附件库数据分页列表")
    public CommonResult<PageResult<AttachLibraryItemsRespVO>> page(@Body AttachLibraryItemsPageReqVO pageVO) {
        PageResult<AttachLibraryItemsRespVO> attachLibraryItemsPageResult = attachLibraryItemsService.attachLibraryItemsPage(pageVO);
        return success(attachLibraryItemsPageResult);
    }

    @PostMapping("/add")
    @Operation(summary = "添加附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:create')")
    public CommonResult<AttachLibraryItemsRespVO> add(@Body @Valid AttachLibraryItemsAddReqVO attachLibraryItemsAddReqVO) {
        return success(AttachLibraryItemsConvert.INSTANCE.convertRespVO(attachLibraryItemsService.add(attachLibraryItemsAddReqVO)));
    }

    @PostMapping("/batchAdd")
    @Operation(summary = "批量添加附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:batchAdd')")
    public CommonResult<List<AttachLibraryItemsRespVO>> batchAdd(@Body @Valid AttachLibraryItemsBatchAddReqVO attachLibraryItemsAddReqVO) {
        return success(AttachLibraryItemsConvert.INSTANCE.convertListRespVO(attachLibraryItemsService.batchAdd(attachLibraryItemsAddReqVO)));
    }

    @PostMapping("/update")
    @Operation(summary = "更新附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:update')")
    public CommonResult<AttachLibraryItemsRespVO> update(@Body @Valid AttachLibraryItemsUpdateReqVO attachLibraryItemsUpdateReqVO) {
        return success(AttachLibraryItemsConvert.INSTANCE.convertRespVO(attachLibraryItemsService.update(attachLibraryItemsUpdateReqVO)));
    }

    @GetMapping("/get")
    @Operation(summary = "根据id获取附件库数据")
    public CommonResult<AttachLibraryItemsRespVO> get(@Param(value = "id") Integer id) {
        return success(attachLibraryItemsService.get(id));
    }

    @DeleteMapping("/del")
    @Operation(summary = "根据id删除附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(attachLibraryItemsService.del(id));
    }

    @GetMapping("/listAll")
    @Operation(summary = "获取所有附件库数据")
    public CommonResult<List<AttachLibraryItemsRespVO>> listAll() {
        return success(AttachLibraryItemsConvert.INSTANCE.convertListRespVO(attachLibraryItemsService.listAll()));
    }

    @PostMapping("/export")
    @Operation(summary = "导出附件库数据")
    @PreAuthorize("@ss.hasPermission('admin:attachLibraryItems:export')")
    public void export(@Body AttachLibraryItemsExportReqVO exportReqVO, HttpServletResponse response) {
        List<AttachLibraryItems> attachLibraryItemsList = attachLibraryItemsService.queryExportData(exportReqVO);
        ExcelUtils.renderExcel(response, AttachLibraryItemsConvert.INSTANCE.convertToExcelVOList(attachLibraryItemsList), AttachLibraryItemsExcelVO.class, "附件库数据数据","附件库数据数据.xlsx");
    }
}