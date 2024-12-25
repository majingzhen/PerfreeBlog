package com.perfree.controller.common.dictData;


import com.perfree.cache.DictDataCacheService;
import com.perfree.commons.common.CommonResult;
import com.perfree.controller.auth.dictData.vo.DictDataRespVO;
import com.perfree.convert.dictData.DictDataConvert;
import com.perfree.service.dictData.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Get;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
* @description 数据字典值 controller
* @author Perfree
**/
@Controller
@Api(tags = "数据字典值相关接口")
    @Mapping("api/dictData")
public class DictDataController {

    @Inject
    private DictDataCacheService dictDataCacheService;

    @Get
    @Mapping("/listAllCache")
    @ApiOperation(value = "获取所有数据字典值(缓存)")
    public CommonResult<List<DictDataRespVO>> listAllCache() {
        return success(DictDataConvert.INSTANCE.convertDTOListToRespVOList(dictDataCacheService.getAllDictData()));
    }

}