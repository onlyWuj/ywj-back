/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-31 16:46 创建
 */
package com.zds.scf.biz.pbac.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 */
public class CPAuthenticationToken extends UsernamePasswordToken {
    private boolean noPasswordLogin;

    public CPAuthenticationToken(String username, String password) {
        super(username, password);
    }

    public CPAuthenticationToken(String username, boolean noPasswordLogin) {
        super(username, (String) null);
        this.noPasswordLogin =noPasswordLogin;
    }


    public boolean isNoPasswordLogin() {
        return noPasswordLogin;
    }

    public void setNoPasswordLogin(boolean noPasswordLogin) {
        this.noPasswordLogin = noPasswordLogin;
    }
}
