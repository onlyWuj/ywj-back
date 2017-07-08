/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-31 23:19 创建
 */
package com.zds.scf.biz.common.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class LoginDto extends BaseDto {


    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String systemType;
    @NotEmpty
    private String verifyCode;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}
