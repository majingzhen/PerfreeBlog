package com.perfree.service.option;

import com.perfree.cache.OptionCacheService;
import com.perfree.commons.exception.ServiceException;
import com.perfree.controller.auth.option.vo.OptionAddReqVO;
import com.perfree.controller.auth.option.vo.OptionThemeAddReqVO;
import com.perfree.convert.option.OptionConvert;
import com.perfree.enums.ErrorCode;
import com.perfree.enums.OptionEnum;
import com.perfree.mapper.OptionMapper;
import com.perfree.model.Option;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements OptionService {

    @Resource
    private OptionMapper optionMapper;

    @Resource
    private OptionCacheService optionCacheService;

    @Override
    public List<Option> getAllOption() {
        return optionMapper.selectList();
    }

    @Override
    @Transactional
    public Boolean updateOptionByKey(String key, String value) {
        Option option = optionMapper.getByKey(key);
        if (null == option) {
            return false;
        }
        option.setValue(value);
        optionMapper.updateById(option);
        optionCacheService.putOption(option.getKey(), OptionConvert.INSTANCE.convertModelToDTO(option));
        return true;
    }

    @Override
    @Transactional
    public Boolean saveCurrentThemeSetting(OptionThemeAddReqVO optionThemeAddReqVO) {
        String webTheme = optionCacheService.getDefaultValue(OptionEnum.WEB_THEME.getKey(), "");
        if (StringUtils.isBlank(webTheme)) {
            throw new ServiceException(ErrorCode.GET_CURRENT_THEME_ERROR);
        }

        List<Option> optionList = new ArrayList<>();
        for (OptionAddReqVO optionAddReqVO : optionThemeAddReqVO.getOptions()) {
            Option option = OptionConvert.INSTANCE.convertByAddReqVO(optionAddReqVO);
            option.setTheme(webTheme);
            optionList.add(option);
        }
        optionMapper.delByTheme(webTheme);
        optionMapper.insertBatch(optionList);
        for (Option option : optionList) {
            optionCacheService.putOption(option.getKey(), OptionConvert.INSTANCE.convertModelToDTO(option));
        }
        return true;
    }

    @Override
    public List<Option> getCurrentThemeSettingValue() {
        String webTheme = optionCacheService.getDefaultValue(OptionEnum.WEB_THEME.getKey(), "");
        if (StringUtils.isBlank(webTheme)) {
            throw new ServiceException(ErrorCode.GET_CURRENT_THEME_ERROR);
        }
        return optionMapper.getCurrentThemeSettingValue(webTheme);
    }
}