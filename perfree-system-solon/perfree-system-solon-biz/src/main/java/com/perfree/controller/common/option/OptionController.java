package com.perfree.controller.common.option;


import com.perfree.cache.OptionCacheService;
import com.perfree.commons.common.CommonResult;
import com.perfree.controller.auth.option.vo.OptionRespVO;
import com.perfree.convert.option.OptionConvert;
import com.perfree.system.api.option.dto.OptionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(tags = "系统配置接口")
@Mapping("api/option")
public class OptionController {

    @Inject
    private OptionCacheService optionCacheService;

    @Get
    @Mapping("/getOptionByKeysAndIdentification")
    @ApiOperation(value = "根据key和标识获取配置项")
    public CommonResult<List<OptionRespVO>> getOptionByNoAuth(@Param("keys") String[] keys, @Param("identification") String identification){
        List<OptionDTO> optionDTOList = new ArrayList<>();
        for (String key : keys) {
            OptionDTO openOption = optionCacheService.getOption(key, identification);
            if (openOption == null) {
                openOption = new OptionDTO();
            }
            optionDTOList.add(openOption);
        }
        return CommonResult.success(OptionConvert.INSTANCE.convertCacheDTO2RespListVO(optionDTOList));
    }
}
