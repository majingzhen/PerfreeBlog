package com.perfree.controller.auth.codegen;


import com.perfree.demoModel.DemoMode;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.codegen.vo.*;
import com.perfree.controller.auth.codegen.vo.table.CodegenTableListReqVO;
import com.perfree.controller.auth.codegen.vo.table.CodegenTablePageReqVO;
import com.perfree.controller.auth.codegen.vo.table.CodegenTableRespVO;
import com.perfree.convert.codegen.CodegenConvert;
import com.perfree.model.CodegenTable;
import com.perfree.service.codegen.CodegenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 代码生成
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/15 10:16
 **/
@Controller
@Api(tags = "代码生成相关接口")
@Mapping("api/auth/codegen")
public class CodegenController {

    @Inject
    private CodegenService codegenService;

    @Post
    @Mapping("/getTableList")
    @ApiOperation(value = "获取数据库所有的表")
    public CommonResult<List<TableInfo>> getTableList(@Body CodegenTableListReqVO codegenTableListReqVO) {
        return success(codegenService.getTableList(codegenTableListReqVO));
    }

    @Post
    @Mapping("/create-list")
    @ApiOperation(value = "基于数据库的表结构，创建代码生成器的表和字段定义")
    public CommonResult<String> createCodegenList(@Valid @Body CodegenCreateListReqVO reqVO) {
        codegenService.createCodegenList(reqVO);
        return success("操作成功");
    }

    @Post
    @Mapping("/codegenTablePage")
    @ApiOperation(value = "代码生成数据库表分页列表")
    public CommonResult<PageResult<CodegenTableRespVO>> codegenTablePage(@Body CodegenTablePageReqVO pageVO) {
        PageResult<CodegenTable> codegenTablePage = codegenService.codegenTablePage(pageVO);
        return success(CodegenConvert.INSTANCE.convertPageResultVO(codegenTablePage));
    }

    @Get
    @Mapping("/getCodegenInfoByTableId")
    @ApiOperation(value = "根据表id获取代码生成信息")
    public CommonResult<CodegenInfoRespVO> getCodegenInfoByTableId(@Param(value = "tableId") Integer tableId) {
        return success(codegenService.getCodegenInfoByTableId(tableId));
    }

    @Post
    @Mapping("/saveConfig")
    @ApiOperation(value = "保存代码生成配置")
    public CommonResult<Boolean> saveConfig(@Body CodegenInfoReqVO codegenInfoReqVO) {
        return success(codegenService.saveConfig(codegenInfoReqVO));
    }

    @Get
    @Mapping("/getCodeFileList")
    @ApiOperation(value = "获取生成文件列表")
    public CommonResult<List<CodegenFileListRespVO>> getCodeFileList(@Param(value = "tableId") Integer tableId) {
        return success(codegenService.getCodeFileList(tableId));
    }

    @Post
    @Mapping("/getCodeFileContent")
    @ApiOperation(value = "获取代码文件内容")
    public CommonResult<String> getCodeFileContent(@Body CodeFileContentReqVO codeFileContentReqVO) {
        return success(codegenService.getCodeFileContent(codeFileContentReqVO));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "删除生成的数据")
    @DemoMode
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(codegenService.del(id));
    }

    @Get
    @Mapping("/download")
    @ApiOperation(value = "下载")
    public void download(@Param(value = "id") Integer id, HttpServletResponse response) {
        codegenService.download(id, response);
    }


}