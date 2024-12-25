package com.perfree.controller.common.category;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.category.vo.CategoryListReqVO;
import com.perfree.controller.auth.category.vo.CategoryPageReqVO;
import com.perfree.controller.auth.category.vo.CategoryRespVO;
import com.perfree.controller.auth.category.vo.CategoryTreeRespVO;
import com.perfree.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "分类相关接口")
@Mapping("api/category")
public class CategoryController {

    @Inject
    private CategoryService categoryService;

    @Post
    @Mapping("/page")
    @ApiOperation(value = "分类分页列表")
    public CommonResult< PageResult<CategoryRespVO>> page(@Body CategoryPageReqVO pageVO) {
        return success(categoryService.categoryPage(pageVO));
    }

    @Post
    @Mapping("/listTree")
    @ApiOperation(value = "分类树形结构列表")
    public CommonResult<List<CategoryTreeRespVO>> listTree(@Body CategoryListReqVO reqVO) {
        return success(categoryService.listTree(reqVO));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取分类")
    public CommonResult<CategoryRespVO> get(@Param(value = "id") Integer id) {
        return success(categoryService.getCategoryById(id));
    }

    @Get
    @Mapping("/getBySlug")
    @ApiOperation(value = "根据slug获取分类")
    public CommonResult<CategoryRespVO> getBySlug(@Param(value = "slug") String slug) {
        return success(categoryService.getBySlug(slug));
    }

    @Get
    @Mapping("/getHotCategory")
    @ApiOperation(value = "获取热门分类")
    public CommonResult<List<CategoryRespVO>> getHotCategory(@Param(value = "num") Integer num) {
        return success(categoryService.getHotCategory(num));
    }
}
