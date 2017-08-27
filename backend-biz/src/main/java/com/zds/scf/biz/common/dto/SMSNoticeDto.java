/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-31 23:19 创建
 */
package com.zds.scf.biz.common.dto;

import com.zds.scf.biz.common.dto.base.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 */
public class SMSNoticeDto extends BaseDto {


    @NotEmpty
    private String device;
    @NotEmpty
    private String templateid;
    @NotEmpty
    private String params;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
