package com.zds.scf.web.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by yy at 2017/2/8 18:10
 */
@ConfigurationProperties(prefix = CommonProperties.PREFIX)
public class CommonProperties {
    public static final String PREFIX = "zds.common";
    /*fdfs tracker 公网端口*/
    private String fdfsOutPort="8080";
    /*fdfs tracker 公网访问域名*/
    private String fdfsTrackerServerDomain;
    /*友阿短信服务器地址*/
    private String postManServerUrl;
    /*友阿短信服务器用户名*/
    private String postManUsername;
     /*友阿短信服务器密码*/
    private String postManPassword;
    /*友阿短信服务器 chanel*/
    private Integer channel;

    public String getFdfsOutPort() {
        return fdfsOutPort;
    }

    public void setFdfsOutPort(String fdfsOutPort) {
        this.fdfsOutPort = fdfsOutPort;
    }

    public String getPostManServerUrl() {
        return postManServerUrl;
    }

    public void setPostManServerUrl(String postManServerUrl) {
        this.postManServerUrl = postManServerUrl;
    }

    public String getFdfsTrackerServerDomain() {
        return fdfsTrackerServerDomain;
    }

    public void setFdfsTrackerServerDomain(String fdfsTrackerServerDomain) {
        this.fdfsTrackerServerDomain = fdfsTrackerServerDomain;
    }

    public String getPostManUsername() {
        return postManUsername;
    }

    public void setPostManUsername(String postManUsername) {
        this.postManUsername = postManUsername;
    }

    public String getPostManPassword() {
        return postManPassword;
    }

    public void setPostManPassword(String postManPassword) {
        this.postManPassword = postManPassword;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
