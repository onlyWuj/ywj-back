package com.zds.scf.biz.common.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class SMSVerifyDto {

    @NotEmpty
    private String phone;

/*    private String verifyCode;*/

    private int effective;

    public SMSVerifyDto(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

/*
    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
*/

    public int getEffective() {
        return effective;
    }

    public void setEffective(int effective) {
        this.effective = effective;
    }
}

