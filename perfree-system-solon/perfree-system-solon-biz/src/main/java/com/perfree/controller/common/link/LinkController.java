package com.perfree.controller.common.link;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.link.vo.LinkPageReqVO;
import com.perfree.controller.auth.link.vo.LinkRespVO;
import com.perfree.service.link.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.*;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "友链相关接口")
@Mapping("api/link")
public class LinkController {

    @Inject
    private LinkService linkService;


    @Post
    @Mapping("/page")
    @ApiOperation(value = "友链分页列表")
    public CommonResult<PageResult<LinkRespVO>> page(@Body LinkPageReqVO pageVO) {
        PageResult<LinkRespVO> linkPageResult = linkService.linkPage(pageVO);
        return success(linkPageResult);
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取友链")
    public CommonResult<LinkRespVO> get(@Param(value = "id") Integer id) {
        return success(linkService.getLinkById(id));
    }
}
