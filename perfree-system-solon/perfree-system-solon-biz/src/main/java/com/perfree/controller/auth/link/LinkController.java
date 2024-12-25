package com.perfree.controller.auth.link;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.link.vo.LinkAddReqVO;
import com.perfree.controller.auth.link.vo.LinkPageReqVO;
import com.perfree.controller.auth.link.vo.LinkRespVO;
import com.perfree.controller.auth.link.vo.LinkUpdateReqVO;
import com.perfree.convert.link.LinkConvert;
import com.perfree.model.Link;
import com.perfree.service.link.LinkService;
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
@Api(tags = "友链相关接口")
@Mapping("api/auth/link")
public class LinkController {

    @Inject
    private LinkService linkService;


    @Post
    @Mapping("/page")
    @ApiOperation(value = "友链分页列表")
    public CommonResult<PageResult<LinkRespVO>> page(@Body LinkPageReqVO pageVO) {
        PageResult<LinkRespVO> linkPageResult = linkService.linkPage(pageVO);
        return CommonResult.success(linkPageResult);
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加友链")
    @PreAuthorize("@ss.hasPermission('admin:link:create')")
    public CommonResult<LinkRespVO> add(@Body @Valid LinkAddReqVO linkAddReqVO) {
        Link link = linkService.addLink(linkAddReqVO);
        return CommonResult.success(LinkConvert.INSTANCE.convertRespVO(link));
    }

    @Put
    @Mapping("/update")
    @ApiOperation(value = "更新友链")
    @PreAuthorize("@ss.hasPermission('admin:link:update')")
    public CommonResult<LinkRespVO> update(@Body @Valid LinkUpdateReqVO linkUpdateReqVO) {
        Link link = linkService.updateLink(linkUpdateReqVO);
        return CommonResult.success(LinkConvert.INSTANCE.convertRespVO(link));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取友链")
    public CommonResult<LinkRespVO> get(@Param(value = "id") Integer id) {
        return CommonResult.success(linkService.getLinkById(id));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "删除友链")
    @PreAuthorize("@ss.hasPermission('admin:link:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(linkService.del(id));
    }

}
