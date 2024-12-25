package com.demo.service.pluginDemo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.utils.SortingFieldUtils;
import com.demo.controller.auth.pluginDemo.vo.*;
import com.demo.convert.pluginDemo.PluginDemoConvert;
import com.demo.mapper.PluginDemoMapper;
import com.demo.model.PluginDemo;
import jakarta.annotation.Resource;
import org.apache.ibatis.solon.annotation.Db;
import org.noear.solon.annotation.Component;
import org.noear.solon.data.annotation.Tran;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 测试 ServiceImpl
 * @author Perfree
 **/
@Component
public class PluginDemoServiceImpl extends ServiceImpl<PluginDemoMapper, PluginDemo> implements PluginDemoService {

    @Db
    private PluginDemoMapper pluginDemoMapper;


    @Override
    public PageResult<PluginDemo> pluginDemoPage(PluginDemoPageReqVO pageVO) {
        return pluginDemoMapper.selectPage(pageVO);
    }

    @Override
    @Tran
    public PluginDemo add(PluginDemoAddReqVO pluginDemoAddReqVO) {
        PluginDemo pluginDemo = PluginDemoConvert.INSTANCE.convertAddReqVO(pluginDemoAddReqVO);
        pluginDemoMapper.insert(pluginDemo);
        return pluginDemo;
    }

    @Override
    @Tran
    public PluginDemo update(PluginDemoUpdateReqVO pluginDemoUpdateReqVO) {
        PluginDemo pluginDemo = PluginDemoConvert.INSTANCE.convertUpdateReqVO(pluginDemoUpdateReqVO);
        pluginDemoMapper.updateById(pluginDemo);
        return pluginDemo;
    }

    @Override
    public PluginDemo get(Integer id) {
        return pluginDemoMapper.selectById(id);
    }

    @Override
    @Tran
    public Boolean del(Integer id) {
        pluginDemoMapper.deleteById(id);
        return true;
    }

    @Override
    public List<PluginDemo> listAll() {
        return pluginDemoMapper.listAll();
    }

    @Override
    public List<PluginDemo> queryExportData(PluginDemoExportReqVO exportReqVO) {
        return pluginDemoMapper.queryExportData(exportReqVO);
    }
}
