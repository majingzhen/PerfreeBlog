package com.perfree.apiImpl.mailServer;

import com.perfree.convert.mailServer.MailServerConvert;
import com.perfree.model.MailServer;
import com.perfree.service.mailServer.MailServerService;
import com.perfree.system.api.mailServer.MailServerApi;
import com.perfree.system.api.mailServer.dto.MailServerDTO;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class MailServerApiImpl implements MailServerApi {

    @Inject
    private MailServerService mailServerService;

    @Override
    public MailServerDTO getById(Integer mailServerId) {
        MailServer byId = mailServerService.getById(mailServerId);
        return MailServerConvert.INSTANCE.convertToDTO(byId);
    }
}