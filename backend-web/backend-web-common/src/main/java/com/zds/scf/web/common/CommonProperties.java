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


    public String getFdfsOutPort() {
        return fdfsOutPort;
    }

    public void setFdfsOutPort(String fdfsOutPort) {
        this.fdfsOutPort = fdfsOutPort;
    }

    public String getFdfsTrackerServerDomain() {
        return fdfsTrackerServerDomain;
    }

    public void setFdfsTrackerServerDomain(String fdfsTrackerServerDomain) {
        this.fdfsTrackerServerDomain = fdfsTrackerServerDomain;
    }

}
