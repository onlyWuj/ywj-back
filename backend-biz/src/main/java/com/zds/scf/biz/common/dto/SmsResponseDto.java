package com.zds.scf.biz.common.dto;

/**
 * Created by yy at 2017/2/13 14:49
 */
public class SmsResponseDto  {

    private  Boolean success; //是否发送成功
    private  String text; //短信发送类容(包括验证码)
//    private  String smsValidateCode;//验证码
    private  String errorMsg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public String getSmsValidateCode() {
//        return smsValidateCode;
//    }
//
//    public void setSmsValidateCode(String smsValidateCode) {
//        this.smsValidateCode = smsValidateCode;
//    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
