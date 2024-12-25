package com.perfree.controller.auth.extra;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.extra.vo.ExtraAddReqVO;
import com.perfree.controller.auth.extra.vo.ExtraPageReqVO;
import com.perfree.controller.auth.extra.vo.ExtraRespVO;
import com.perfree.controller.auth.extra.vo.ExtraUpdateReqVO;
import com.perfree.convert.extra.ExtraConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.Extra;
import com.perfree.service.extra.ExtraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "附加数据相关接口")
@Mapping("api/auth/extra")
public class ExtraController {

    @Inject
    private ExtraService extraService;


    @Get
    @Mapping("/getByKey")
    @ApiOperation(value = "根据key获取附加数据")
    public CommonResult<ExtraRespVO> getByKey(@RequestParam(value = "extraKey") String extraKey) {
        return success(ExtraConvert.INSTANCE.convertRespVO(extraService.getByKey(extraKey)));
    }

    @Post
    @Mapping("/page")
    @ApiOperation(value = "附加数据分页列表")
    @PreAuthorize("@ss.hasPermission('admin:extra:query')")
    public CommonResult<PageResult<ExtraRespVO>> page(@Body ExtraPageReqVO pageVO) {
        PageResult<Extra> extraPageResult = extraService.extraPage(pageVO);
        return success(ExtraConvert.INSTANCE.convertPageResultVO(extraPageResult));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取附加数据")
    public CommonResult<ExtraRespVO> get(@RequestParam(value = "id") Integer id) {
        return success(ExtraConvert.INSTANCE.convertRespVO(extraService.get(id)));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加附加数据")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:extra:create')")
    public CommonResult<ExtraRespVO> add(@Body @Valid ExtraAddReqVO extraAddReqVO) {
        return success(ExtraConvert.INSTANCE.convertRespVO(extraService.add(extraAddReqVO)));
    }

    @Post
    @Mapping("/update")
    @ApiOperation(value = "更新附加数据")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:extra:update')")
    public CommonResult<ExtraRespVO> update(@Body @Valid ExtraUpdateReqVO extraUpdateReqVO) {
        return success(ExtraConvert.INSTANCE.convertRespVO(extraService.updateExtra(extraUpdateReqVO)));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "删除附加数据")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:extra:delete')")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return success(extraService.del(id));
    }
}
