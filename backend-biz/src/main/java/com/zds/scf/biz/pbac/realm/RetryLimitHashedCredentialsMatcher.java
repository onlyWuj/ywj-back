/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-13 21:01 创建
 */
package com.zds.scf.biz.pbac.realm;

import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.biz.pbac.ex.CPAuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    @Autowired
    private UserDomainService userService;
    @Autowired
    private PasswordRetryCache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CPAuthenticationToken authenticationToken = (CPAuthenticationToken) token;

        String userCode = (String) authenticationToken.getPrincipal();
        //retry count + 1
        Integer retryCount = passwordRetryCache.getRetryCount(userCode);
        retryCount++;
        if (retryCount > 5) {
            //if retry count > 5 throw
            userService.lock(userCode);
            throw new CPAuthenticationException(userCode + "用户登录失败次数超过限制，帐号被锁定");
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //clear retry count
            passwordRetryCache.clearRetryCount(userCode);
        } else {
            passwordRetryCache.setRetryCount(userCode, retryCount);
        }
        return matches;
    }

}
