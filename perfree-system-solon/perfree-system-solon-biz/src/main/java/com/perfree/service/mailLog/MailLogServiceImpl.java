package com.perfree.service.mailLog;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.utils.SortingFieldUtils;
import com.perfree.controller.auth.mailLog.vo.*;
import com.perfree.convert.mailLog.MailLogConvert;
import com.perfree.mapper.MailLogMapper;
import com.perfree.model.MailLog;
import jakarta.annotation.Resource;
import org.apache.ibatis.solon.annotation.Db;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;
import org.noear.solon.annotation.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 邮件日志 ServiceImpl
 * @author Perfree
 **/
@Component
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements MailLogService {

    @Inject
    private MailLogMapper mailLogMapper;


    @Override
    public PageResult<MailLog> mailLogPage(MailLogPageReqVO pageVO) {
        return mailLogMapper.selectPage(pageVO);
    }

    @Override
    @Tran
    public MailLog add(MailLogAddReqVO mailLogAddReqVO) {
        MailLog mailLog = MailLogConvert.INSTANCE.convertAddReqVO(mailLogAddReqVO);
        mailLogMapper.insert(mailLog);
        return mailLog;
    }

    @Override
    @Tran
    public MailLog update(MailLogUpdateReqVO mailLogUpdateReqVO) {
        MailLog mailLog = MailLogConvert.INSTANCE.convertUpdateReqVO(mailLogUpdateReqVO);
        mailLogMapper.updateById(mailLog);
        return mailLog;
    }

    @Override
    public MailLog get(Integer id) {
        return mailLogMapper.selectById(id);
    }

    @Override
    @Tran
    public Boolean del(Integer id) {
        mailLogMapper.deleteById(id);
        return true;
    }

    @Override
    public List<MailLog> listAll() {
        return mailLogMapper.listAll();
    }

    @Override
    public List<MailLog> queryExportData(MailLogExportReqVO exportReqVO) {
        return mailLogMapper.queryExportData(exportReqVO);
    }

    @Override
    public void saveMailLog(MailLog mailLog) {
        mailLogMapper.insert(mailLog);
    }
}