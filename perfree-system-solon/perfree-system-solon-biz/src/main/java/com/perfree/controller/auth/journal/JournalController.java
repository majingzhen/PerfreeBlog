package com.perfree.controller.auth.journal;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.article.vo.ArticleUpdateIsCommentReqVO;
import com.perfree.controller.auth.article.vo.ArticleUpdateIsTopReqVO;
import com.perfree.controller.auth.journal.vo.JournalAddReqVO;
import com.perfree.controller.auth.journal.vo.JournalPageReqVO;
import com.perfree.controller.auth.journal.vo.JournalRespVO;
import com.perfree.controller.auth.journal.vo.JournalUpdateReqVO;
import com.perfree.service.article.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Post;
import org.noear.solon.annotation.Put;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "动态相关接口")
@Mapping("api/auth/journal")
public class JournalController {

    @Inject
    private ArticleService articleService;

    @Post
    @Mapping("/createJournal")
    @ApiOperation(value = "发表动态")
    @PreAuthorize("@ss.hasPermission('admin:journal:create')")
    public CommonResult<JournalRespVO> createJournal(@Body @Valid JournalAddReqVO journalAddReqVO) {
        return CommonResult.success(articleService.createJournal(journalAddReqVO));
    }

    @Put
    @Mapping("/updateJournal")
    @ApiOperation(value = "修改动态")
    @PreAuthorize("@ss.hasPermission('admin:journal:update')")
    public CommonResult<JournalRespVO> updateJournal(@Body @Valid JournalUpdateReqVO updateReqVO) {
        return CommonResult.success(articleService.updateJournal(updateReqVO));
    }

    @Post
    @Mapping("/updateIsComment")
    @ApiOperation(value = "修改是否允许评论")
    @PreAuthorize("@ss.hasPermission('admin:journal:updateIsComment')")
    public CommonResult<Boolean> updateIsComment(@Body @Valid ArticleUpdateIsCommentReqVO articleUpdateIsCommentReqVO) {
        return CommonResult.success(articleService.updateIsComment(articleUpdateIsCommentReqVO));
    }

    @Post
    @Mapping("/updateIsTop")
    @ApiOperation(value = "修改是否置顶")
    @PreAuthorize("@ss.hasPermission('admin:journal:updateIsTop')")
    public CommonResult<Boolean> updateIsTop(@Body @Valid ArticleUpdateIsTopReqVO articleUpdateIsTopReqVO) {
        return CommonResult.success(articleService.updateIsTop(articleUpdateIsTopReqVO));
    }

    @Post
    @Mapping("/page")
    @ApiOperation(value = "动态分页列表")
    public CommonResult<PageResult<JournalRespVO>> page(@Body JournalPageReqVO pageVO) {
        return CommonResult.success(articleService.journalPage(pageVO));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取动态")
    public CommonResult<JournalRespVO> get(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.getJournalById(id));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除动态")
    @PreAuthorize("@ss.hasPermission('admin:journal:delete')")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.del(id));
    }

}
