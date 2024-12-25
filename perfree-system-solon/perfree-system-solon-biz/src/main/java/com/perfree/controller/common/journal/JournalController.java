package com.perfree.controller.common.journal;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.journal.vo.JournalPageReqVO;
import com.perfree.controller.auth.journal.vo.JournalRespVO;
import com.perfree.service.article.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.noear.solon.annotation.*;

import org.springframework.web.bind.annotation.RequestParam;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "动态相关接口")
@Mapping("api/journal")
public class JournalController {

    @Inject
    private ArticleService articleService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "动态分页列表")
    public CommonResult<PageResult<JournalRespVO>> page(@Body JournalPageReqVO pageVO) {
        return success(articleService.journalPage(pageVO));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "根据id获取动态")
    public CommonResult<JournalRespVO> get(@Param(value = "id") Integer id) {
        return CommonResult.success(articleService.getJournalById(id));
    }

}
