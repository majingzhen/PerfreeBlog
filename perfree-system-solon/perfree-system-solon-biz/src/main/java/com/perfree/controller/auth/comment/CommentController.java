package com.perfree.controller.auth.comment;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.comment.vo.CommentChildPageReqVO;
import com.perfree.controller.auth.comment.vo.CommentPageReqVO;
import com.perfree.controller.auth.comment.vo.CommentRespVO;
import com.perfree.controller.auth.comment.vo.CommentUpdateStatusReqVO;
import com.perfree.controller.auth.tag.vo.TagUpdateReqVO;
import com.perfree.controller.common.comment.vo.CommentPageByTopPidReqVO;
import com.perfree.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "评论相关接口")
@Mapping("api/auth/comment")
public class CommentController {

    @Inject
    private CommentService commentService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "评论分页列表")
    public CommonResult<PageResult<CommentRespVO>> page(@Body CommentPageReqVO commentPageReqVO) {
        PageResult<CommentRespVO> commentPageResult = commentService.commentPage(commentPageReqVO);
        return success(commentPageResult);
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取评论信息")
    public CommonResult<CommentRespVO> get(@Param(value = "id") Integer id) {
        return CommonResult.success(commentService.queryById(id));
    }

    @Post
    @Mapping("/queryChildCommentPage")
    @ApiOperation(value = "获取子评论分页列表")
    public CommonResult<PageResult<CommentRespVO>> queryChildCommentPage(@Body CommentChildPageReqVO pageReqVO) {
        return CommonResult.success(commentService.queryChildCommentPage(pageReqVO));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "根据id删除评论")
    @PreAuthorize("@ss.hasPermission('admin:comment:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return CommonResult.success(commentService.del(id));
    }

    @Post
    @Mapping("/updateStatus")
    @ApiOperation(value = "修改评论状态")
    @PreAuthorize("@ss.hasPermission('admin:comment:audit')")
    public CommonResult<Boolean> updateStatus(@Body @Valid CommentUpdateStatusReqVO commentUpdateStatusReqVO) {
        return CommonResult.success(commentService.updateStatus(commentUpdateStatusReqVO));
    }

}
