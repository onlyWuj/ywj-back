/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-13 21:50 创建
 */
package com.zds.scf.biz.pbac.ex;

import org.apache.shiro.authc.AuthenticationException;

/**
 *
 */
public class CPAuthenticationException extends AuthenticationException {
    public CPAuthenticationException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
