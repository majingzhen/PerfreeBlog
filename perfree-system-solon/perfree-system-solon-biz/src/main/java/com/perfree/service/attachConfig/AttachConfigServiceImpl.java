package com.perfree.service.attachConfig;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.cache.AttachConfigCacheService;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.constant.SystemConstants;
import com.perfree.controller.auth.attachConfig.vo.AttachConfigCreateVO;
import com.perfree.controller.auth.attachConfig.vo.AttachConfigPageReqVO;
import com.perfree.controller.auth.attachConfig.vo.AttachConfigUpdateMasterVO;
import com.perfree.controller.auth.attachConfig.vo.AttachConfigUpdateVO;
import com.perfree.convert.attachConfig.AttachConfigConvert;
import com.perfree.file.handle.local.FileLocalConfig;
import com.perfree.handler.CustomResourceHandler;
import com.perfree.mapper.AttachConfigMapper;
import com.perfree.model.AttachConfig;
import com.perfree.system.api.attachConfig.dto.AttachConfigCacheDTO;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Component
public class AttachConfigServiceImpl extends ServiceImpl<AttachConfigMapper, AttachConfig> implements AttachConfigService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachConfigServiceImpl.class);

    @Inject
    private AttachConfigMapper attachConfigMapper;

    @Inject
    private AttachConfigCacheService attachConfigCacheService;

    @Override
    public List<AttachConfig> getAll() {
        return attachConfigMapper.getAll();
    }

    @Override
    @Tran
    public AttachConfig add(AttachConfigCreateVO attachConfigCreateVO) {
        AttachConfig attachConfig = AttachConfigConvert.INSTANCE.convertCreateVO(attachConfigCreateVO);
        attachConfigMapper.insert(attachConfig);
        attachConfigCacheService.putAttachConfig(attachConfig.getId(), AttachConfigConvert.INSTANCE.convertCacheDTO(attachConfig));
        initLocalResourcesPatterns();
        return attachConfig;
    }

    @Override
    @Tran
    public Boolean updateAttachConfig(AttachConfigUpdateVO attachConfigUpdateVO) {
        AttachConfig attachConfig = AttachConfigConvert.INSTANCE.convertUpdateVO(attachConfigUpdateVO);
        attachConfigMapper.updateById(attachConfig);
        attachConfigCacheService.putAttachConfig(attachConfig.getId(), AttachConfigConvert.INSTANCE.convertCacheDTO(attachConfig));
        initLocalResourcesPatterns();
        return true;
    }

    @Override
    @Tran
    public Boolean del(Integer id) {
        attachConfigMapper.deleteById(id);
        attachConfigCacheService.removeAttachConfig(id);
        initLocalResourcesPatterns();
        return true;
    }

    @Override
    public PageResult<AttachConfig> attachConfigPage(AttachConfigPageReqVO pageVO) {
        return attachConfigMapper.attachConfigPage(pageVO);
    }

    @Override
    public Boolean updateMaster(AttachConfigUpdateMasterVO attachConfigUpdateMasterVO) {
        attachConfigMapper.clearMaster();
        attachConfigMapper.updateMaster(attachConfigUpdateMasterVO.getId());

        AttachConfigCacheDTO masterAttachConfig = attachConfigCacheService.getMasterAttachConfig();
        if (null != masterAttachConfig) {
            masterAttachConfig.setMaster(false);
            attachConfigCacheService.putAttachConfig(masterAttachConfig.getId(), masterAttachConfig);
        }

        AttachConfigCacheDTO attachConfig = attachConfigCacheService.getAttachConfig(attachConfigUpdateMasterVO.getId());
        attachConfig.setMaster(true);
        attachConfigCacheService.putAttachConfig(attachConfig.getId(), attachConfig);
        return true;
    }

    @Override
public void initLocalResourcesPatterns() {
    List<AttachConfig> attachConfigs = attachConfigMapper.getAllLocalConfig();
    List<String> locationStrings = new ArrayList<>();
    for (AttachConfig attachConfig : attachConfigs) {
        FileLocalConfig fileLocalConfig = JSONUtil.toBean(attachConfig.getConfig(), FileLocalConfig.class);
        fileLocalConfig.setBasePath(fileLocalConfig.getBasePath().replaceAll("\\\\", SystemConstants.FILE_SEPARATOR));
        if (!fileLocalConfig.getBasePath().endsWith(SystemConstants.FILE_SEPARATOR)) {
            fileLocalConfig.setBasePath(fileLocalConfig.getBasePath() + SystemConstants.FILE_SEPARATOR);
        }
        locationStrings.add("file:" + fileLocalConfig.getBasePath());
    }
    // 使用 Solon 的资源处理机制
    for (String location : locationStrings) {
        //TODO 自定义资源管理器 假设你有一个自定义的资源处理器
        // Assuming Solon is not recognized, we will use an alternative approach to register the custom resource handler
        CustomResourceHandler customResourceHandler = new CustomResourceHandler();
        //customResourceHandler.handle(new CustomContext(Solon.context(),location));
    }
}

    @Override
    public void initAttachConfigCache() {
        List<AttachConfig> all = attachConfigMapper.getAll();
        List<AttachConfigCacheDTO> attachConfigCacheDTOS = AttachConfigConvert.INSTANCE.convertCacheListDTO(all);
        for (AttachConfigCacheDTO attachConfig : attachConfigCacheDTOS) {
            attachConfigCacheService.putAttachConfig(attachConfig.getId(), attachConfig);
        }
    }
}