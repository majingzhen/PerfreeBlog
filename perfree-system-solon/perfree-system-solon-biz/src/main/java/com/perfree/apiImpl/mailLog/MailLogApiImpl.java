package com.perfree.apiImpl.mailLog;

import com.perfree.convert.mailLog.MailLogConvert;
import com.perfree.model.MailLog;
import com.perfree.service.mailLog.MailLogService;
import com.perfree.system.api.mailLog.MailLogApi;
import com.perfree.system.api.mailLog.dto.MailLogDTO;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class MailLogApiImpl implements MailLogApi {

    @Inject
    private MailLogService mailLogService;

    @Override
    public void saveMailLog(MailLogDTO mailLogDTO) {
        MailLog mailLog = MailLogConvert.INSTANCE.convertByDTO(mailLogDTO);
        mailLogService.saveMailLog(mailLog);
    }
}
