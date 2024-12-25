package com.perfree.controller.auth.attach;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.attach.vo.*;
import com.perfree.convert.attach.AttachConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.Attach;
import com.perfree.service.attach.AttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.noear.solon.annotation.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 附件管理
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/15 10:16
 **/
@Controller
@Api(tags = "附件相关接口")
@Mapping("api/auth/attach")
public class AttachController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachController.class);
    @Inject
    private AttachService attachService;

    @Post
    @Mapping("/upload")
    @ApiOperation(value = "附件上传")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attach:upload')")
    public CommonResult<AttachRespVO> upload(AttachUploadVO attachUploadVO) {
        Attach attach = attachService.create(attachUploadVO);
        return success(AttachConvert.INSTANCE.convertRespVO(attach));
    }

    @Post
    @Mapping("/page")
    @ApiOperation(value = "附件分页列表")
    @PreAuthorize("@ss.hasPermission('admin:attach:query')")
    public CommonResult<PageResult<AttachRespVO>> page(@Body AttachPageReqVO pageVO) {
        PageResult<Attach> rolePageResult = attachService.attachPage(pageVO);
        return success(AttachConvert.INSTANCE.convertPageResultVO(rolePageResult));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取附件")
    public CommonResult<AttachRespVO> get(@Param(value = "id") Integer id) {
        Attach byId = attachService.getById(id);
        return success(AttachConvert.INSTANCE.convertRespVO(byId));
    }

    @Put
    @Mapping("/update")
    @ApiOperation(value = "修改附件")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attach:update')")
    public CommonResult<Boolean> update(@Body AttachUpdateVO attachUpdateVO) {
        return success(attachService.updateAttach(attachUpdateVO));
    }


    @Delete
    @Mapping("/del")
    @ApiOperation(value = "删除附件")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:attach:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(attachService.del(id));
    }


    @Get
    @Mapping("/getAllAttachGroup")
    @ApiOperation(value = "获取所有附件分组")
    public CommonResult<List<AttachGroupRespVO>> getAllAttachGroup() {
        return success(attachService.getAllAttachGroup());
    }

    @Post
    @Mapping("/uploadAttachByUrl")
    @ApiOperation(value = "通过url下载并上传附件")
    @DemoMode
    public CommonResult<AttachByUrlRespVO> uploadAttachByUrl(@Valid @Body AttachUploadByUrlVO attachUploadByUrlVO) {
        Attach attach = attachService.uploadAttachByUrl(attachUploadByUrlVO.getUrl());
        AttachByUrlRespVO attachByUrlRespVO = AttachConvert.INSTANCE.convertByUrlRespVO(attach);
        attachByUrlRespVO.setOriginalURL(attachUploadByUrlVO.getUrl());
        return success(attachByUrlRespVO);
    }
}
