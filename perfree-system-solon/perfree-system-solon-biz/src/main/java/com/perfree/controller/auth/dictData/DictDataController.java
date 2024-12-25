package com.perfree.controller.auth.dictData;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.dictData.vo.*;
import com.perfree.convert.dictData.DictDataConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.DictData;
import com.perfree.service.dictData.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 数据字典值 controller
* @author Perfree
**/
@Controller
@Api(tags = "数据字典值相关接口")
@Mapping("api/auth/dictData")
public class DictDataController {

    @Inject
    private DictDataService dictDataService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "数据字典值分页列表")
    @PreAuthorize("@ss.hasPermission('admin:dictData:query')")
    public CommonResult<PageResult<DictDataRespVO>> page(@Body DictDataPageReqVO pageVO) {
        PageResult<DictData> dictDataPageResult = dictDataService.dictDataPage(pageVO);
        return success(DictDataConvert.INSTANCE.convertPageResultVO(dictDataPageResult));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加数据字典值")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:dictData:create')")
    public CommonResult<DictDataRespVO> add(@Body @Valid DictDataAddReqVO dictDataAddReqVO) {
        return success(DictDataConvert.INSTANCE.convertRespVO(dictDataService.add(dictDataAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新数据字典值")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:dictData:update')")
    public CommonResult<DictDataRespVO> update(@Body @Valid DictDataUpdateReqVO dictDataUpdateReqVO) {
        return success(DictDataConvert.INSTANCE.convertRespVO(dictDataService.update(dictDataUpdateReqVO)));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取数据字典值")
    public CommonResult<DictDataRespVO> get(@Param(value = "id") Integer id) {
        return success(DictDataConvert.INSTANCE.convertRespVO(dictDataService.get(id)));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除数据字典值")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:dictData:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(dictDataService.del(id));
    }

    @Get
    @Mapping("/listAll")
    @ApiOperation(value = "获取所有数据字典值")
    public CommonResult<List<DictDataRespVO>> listAll() {
        return success(DictDataConvert.INSTANCE.convertListRespVO(dictDataService.listAll()));
    }
}