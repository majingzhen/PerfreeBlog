package com.perfree.controller.common.tag;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.tag.vo.TagPageReqVO;
import com.perfree.controller.auth.tag.vo.TagRespVO;
import com.perfree.convert.tag.TagConvert;
import com.perfree.service.tag.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "标签相关接口")
@Mapping("api/tag")
public class TagController {

    @Inject
    private TagService tagService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "标签分页列表")
    public CommonResult<PageResult<TagRespVO>> page(@Body TagPageReqVO pageVO) {
        PageResult<TagRespVO> tagPageResult = tagService.tagPage(pageVO);
        return success(tagPageResult);
    }

    @Get
    @Mapping("/getAllTag")
    @ApiOperation(value = "获取所有标签")
    public CommonResult<List<TagRespVO>> getAllTag() {
        List<com.perfree.model.Tag> list = tagService.list();
        return success(TagConvert.INSTANCE.convertRespVOList(list));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取标签信息")
    public CommonResult<TagRespVO> add(@Param(value = "id") Integer id) {
        return CommonResult.success(tagService.getTagById(id));
    }

    @Get
    @Mapping("/getBySlug")
    @ApiOperation(value = "根据slug获取标签信息")
    public CommonResult<TagRespVO> getBySlug(@Param(value = "slug") String slug) {
        return CommonResult.success(tagService.getBySlug(slug));
    }

    @Get
    @Mapping("/getHotTag")
    @ApiOperation(value = "获取热门标签")
    public CommonResult<List<TagRespVO>> getHotTag(@Param(value = "num") Integer num) {
        return CommonResult.success(tagService.getHotTag(num));
    }
}
