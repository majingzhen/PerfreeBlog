package com.perfree.controller.auth.adminHome.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SysInfoRespVO {

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    /**
     * 版本
     */
    private String version;

    /**
     * 主题
     */
    private String theme;

    /**
     * 插件
     */
    private List<String> pluginList;
}
