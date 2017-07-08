package com.zds.scf.biz.common.dto;

/**
 * Created by yy at 2017/2/14 10:10
 */
public class SmsRequestDto {
    private  String phone;
    private  String text;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
