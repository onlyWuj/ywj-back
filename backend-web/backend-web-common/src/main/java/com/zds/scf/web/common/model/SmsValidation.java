/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-11-02 11:29 创建
 */
package com.zds.scf.web.common.model;

import com.zds.scf.biz.common.CPBusinessException;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *
 */
public class SmsValidation implements Serializable {
    private String phone;
    private int retry;
    private String verifyCode;

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static void create(HttpServletRequest httpServletRequest, String phone, String smsValidateCode) {
        SmsValidation smsValidation = new SmsValidation();
        smsValidation.setPhone(phone);
        smsValidation.setVerifyCode(smsValidateCode);
        smsValidation.setRetry(0);
        httpServletRequest.getSession().setAttribute("smsValidation", smsValidation);
    }

    public static void validate(HttpServletRequest httpServletRequest, String verifyCode) {
        SmsValidation smsValidation = getSmsValidation(httpServletRequest);
        if (smsValidation == null) {
            CPBusinessException.throwIt("短信验证码失效");
        }
        smsValidation.setRetry(smsValidation.getRetry() + 1);
        if (smsValidation.getRetry() > 3) {
            httpServletRequest.getSession().setAttribute("smsValidation", smsValidation);
            CPBusinessException.throwIt("短信验证码匹配次数超过限制，请重新生成短信验证码");
        }
        if (smsValidation.getVerifyCode().equalsIgnoreCase(verifyCode)) {
            httpServletRequest.getSession().setAttribute("smsValidationSuccess", true);
        } else {
            CPBusinessException.throwIt("短信验证码错误");
        }
    }

    public static void checkSmsValidateSuccess(HttpServletRequest httpServletRequest) {
        Boolean smsValidationSuccess = (Boolean) httpServletRequest.getSession().getAttribute("smsValidationSuccess");
        if (smsValidationSuccess == null || !smsValidationSuccess) {
            CPBusinessException.throwIt("非法操作");
        }
    }

    public static SmsValidation getSmsValidation(HttpServletRequest httpServletRequest) {
        return (SmsValidation) httpServletRequest.getSession().getAttribute("smsValidation");
    }
}
