package com.perfree.controller.auth.category;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.category.vo.*;
import com.perfree.convert.category.CategoryConvert;
import com.perfree.model.Category;
import com.perfree.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.noear.solon.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "分类相关接口")
@Mapping("api/auth/category")
public class CategoryController {

    @Inject
    private CategoryService categoryService;

    @Post
    @Mapping("/pageList")
    @ApiOperation(value = "分类页面列表")
    public CommonResult<List<CategoryRespVO>> pageList(@Body CategoryListReqVO reqVO) {
        return success(categoryService.categoryPageList(reqVO));
    }

    @Post
    @Mapping("/listTree")
    @ApiOperation(value = "分类树形结构列表")
    public CommonResult<List<CategoryTreeRespVO>> listTree(@Body CategoryListReqVO reqVO) {
        return success(categoryService.listTree(reqVO));
    }

    @Post
    @Mapping("/add")
    @ApiOperation(value = "添加分类")
    @PreAuthorize("@ss.hasPermission('admin:category:create')")
    public CommonResult<CategoryRespVO> add(@Body @Valid CategoryAddReqVO categoryAddReqVO) {
        Category category = categoryService.addCategory(categoryAddReqVO);
        return success(CategoryConvert.INSTANCE.convertRespVO(category));
    }

    @Put
    @Mapping("/update")
    @ApiOperation(value = "更新分类")
    @PreAuthorize("@ss.hasPermission('admin:category:update')")
    public CommonResult<CategoryRespVO> update(@Body @Valid CategoryUpdateReqVO categoryUpdateReqVO) {
        Category category = categoryService.updateCategory(categoryUpdateReqVO);
        return success(CategoryConvert.INSTANCE.convertRespVO(category));
    }

    @Get
    @Mapping("/get")
    @ApiOperation(value = "获取分类")
    public CommonResult<CategoryRespVO> get(@Param(value = "id") Integer id) {
        return success(categoryService.getCategoryById(id));
    }

    @Delete
    @Mapping("/del")
    @ApiOperation(value = "删除分类")
    @PreAuthorize("@ss.hasPermission('admin:category:delete')")
    public CommonResult<Boolean> del(@Param(value = "id") Integer id) {
        return success(categoryService.del(id));
    }
}
