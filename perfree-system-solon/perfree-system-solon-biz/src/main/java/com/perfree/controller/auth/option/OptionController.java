package com.perfree.controller.auth.option;


import com.perfree.commons.common.CommonResult;
import com.perfree.controller.auth.option.vo.OptionAddListReqVO;
import com.perfree.controller.auth.option.vo.OptionRespVO;
import com.perfree.convert.option.OptionConvert;
import com.perfree.demoModel.DemoMode;
import com.perfree.model.Option;
import com.perfree.service.option.OptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@Controller
@Api(tags = "系统配置相关接口")
@Mapping("api/auth/option")
public class OptionController {

    @Inject
    private OptionService optionService;

    @Post
    @Mapping("/saveOptionList")
    @ApiOperation(value = "保存配置项")
    @DemoMode
    @PreAuthorize("@ss.hasPermission('admin:option:saveOptionList')")
    public CommonResult<Boolean> saveOptionList(@Body @Valid OptionAddListReqVO optionAddListReqVO) {
        return success(optionService.saveOptionList(optionAddListReqVO));
    }

    @Get
    @Mapping("/getOptionByIdentification")
    @ApiOperation(value = "根据标识获取所有的配置项")
    public CommonResult<List<OptionRespVO>> getOptionByIdentification(@RequestParam(value = "identification") String identification) {
        List<Option> optionList = optionService.getOptionByIdentification(identification);
        return success(OptionConvert.INSTANCE.convertToRespVOList(optionList));
    }

    @Get
    @Mapping("/getCurrentThemeSettingValue")
    @ApiOperation(value = "获取当前启用主题的所有配置项值")
    public CommonResult<List<OptionRespVO>> getCurrentThemeSettingValue() {
        List<Option> optionList = optionService.getCurrentThemeSettingValue();
        return success(OptionConvert.INSTANCE.convertToRespVOList(optionList));
    }

    @Post
    @Mapping("/saveCurrentThemeSetting")
    @ApiOperation(value = "保存当前启用主题的设置项")
    @PreAuthorize("@ss.hasPermission('admin:theme:updateSetting')")
    public CommonResult<Boolean> saveCurrentThemeSetting(@Body @Valid OptionAddListReqVO optionAddListReqVO) {
        return success(optionService.saveCurrentThemeSetting(optionAddListReqVO));
    }


}