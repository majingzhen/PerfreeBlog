package com.perfree.controller.auth.mailLog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
* @description 邮件日志 导出ReqVO
* @author Perfree
**/
@Schema(description = "邮件日志导出ReqVO")
@Data
public class MailLogExportReqVO {

    @Schema(description = "模板编号")
    private String mailTemplateCode;

    @Schema(description = "发送时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] sendDate;

    @Schema(description = "接收邮箱")
    private String receiveMail;

    @Schema(description = "邮件标题")
    private String mailTitle;

    @Schema(description = "发送状态")
    private Integer sendStatus;

    @Schema(description = "发件邮箱")
    private String sendMail;
}