/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-31 23:19 创建
 */
package com.zds.scf.web.common.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class LoginModel extends RequestModel {
    public static interface PasswordLogin {

    }

    @NotEmpty
    private String userCode;

    @NotEmpty(groups = PasswordLogin.class)
    private String password;

    //    @NotNull(message = "图形验证码不能为空",groups = PasswordLogin.class)
    private String verifyCode;


    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
