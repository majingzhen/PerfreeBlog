package com.perfree.controller.auth.page;


import com.perfree.commons.common.CommonResult;
import com.perfree.controller.auth.article.vo.*;
import com.perfree.convert.article.ArticleConvert;
import com.perfree.model.Article;
import com.perfree.service.article.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Api(tags = "页面相关接口")
@Mapping("api/auth/page")
public class PageController {
    @Inject
    private ArticleService articleService;

    @Post
    @Mapping("/createArticle")
    @ApiOperation(value = "发表页面")
    @PreAuthorize("@ss.hasPermission('admin:page:create')")
    public CommonResult<ArticleRespVO> createArticle(@Body @Valid ArticleAddReqVO articleAddReqVO) {
        Article article = articleService.createArticle(articleAddReqVO);
        return CommonResult.success(ArticleConvert.INSTANCE.convertRespVO(article));
    }

    @Put
    @Mapping("/updateArticle")
    @ApiOperation(value = "修改页面")
    @PreAuthorize("@ss.hasPermission('admin:page:update')")
    public CommonResult<ArticleRespVO> updateArticle(@Body @Valid ArticleUpdateReqVO articleUpdateReqVO) {
        Article article = articleService.updateArticle(articleUpdateReqVO);
        return CommonResult.success(ArticleConvert.INSTANCE.convertRespVO(article));
    }


    @Post
    @Mapping("/updateIsComment")
    @ApiOperation(value = "修改是否允许评论")
    @PreAuthorize("@ss.hasPermission('admin:page:updateIsComment')")
    public CommonResult<Boolean> updateIsComment(@Body @Valid ArticleUpdateIsCommentReqVO articleUpdateIsCommentReqVO) {
        return CommonResult.success(articleService.updateIsComment(articleUpdateIsCommentReqVO));
    }

    @Post
    @Mapping("/updateIsTop")
    @ApiOperation(value = "修改是否置顶")
    @PreAuthorize("@ss.hasPermission('admin:page:updateIsTop')")
    public CommonResult<Boolean> updateIsTop(@Body @Valid ArticleUpdateIsTopReqVO articleUpdateIsTopReqVO) {
        return CommonResult.success(articleService.updateIsTop(articleUpdateIsTopReqVO));
    }

    @Post
    @Mapping("/updateStatus")
    @ApiOperation(value = "修改状态")
    @PreAuthorize("@ss.hasPermission('admin:page:updateStatus')")
    public CommonResult<Boolean> updateStatus(@Body @Valid ArticleUpdateStatusReqVO articleUpdateStatusReqVO) {
        return CommonResult.success(articleService.updateStatus(articleUpdateStatusReqVO));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除页面")
    @PreAuthorize("@ss.hasPermission('admin:page:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return CommonResult.success(articleService.del(id));
    }


}