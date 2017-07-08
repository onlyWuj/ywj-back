package com.zds.scf.web.common.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yy at 2016/11/3 14:21
 */
public class SmsVerifyCodeModel extends RequestModel {
    private  String userName;
    @NotEmpty
    private  String phone;

    private  String verifyCode;

    private String smsVerifyCode;

    private  Long merchantId;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getSmsVerifyCode() {
        return smsVerifyCode;
    }

    public void setSmsVerifyCode(String smsVerifyCode) {
        this.smsVerifyCode = smsVerifyCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
