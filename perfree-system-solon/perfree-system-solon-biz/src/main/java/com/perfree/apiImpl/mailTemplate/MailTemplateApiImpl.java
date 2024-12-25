package com.perfree.apiImpl.mailTemplate;

import com.perfree.convert.mailTemplate.MailTemplateConvert;
import com.perfree.model.MailTemplate;
import com.perfree.service.mailTemplate.MailTemplateService;
import com.perfree.system.api.mailTemplate.MailTemplateApi;
import com.perfree.system.api.mailTemplate.dto.MailTemplateDTO;
import jakarta.annotation.Resource;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class MailTemplateApiImpl implements MailTemplateApi {

    @Inject
    private MailTemplateService mailTemplateService;


    @Override
    public MailTemplateDTO getById(Integer templateId) {
        MailTemplate byId = mailTemplateService.getById(templateId);
        return MailTemplateConvert.INSTANCE.convertToDTO(byId);
    }

    @Override
    public MailTemplateDTO getByCode(String templateCode) {
        MailTemplate byCode = mailTemplateService.getByCode(templateCode);
        return MailTemplateConvert.INSTANCE.convertToDTO(byCode);
    }
}
