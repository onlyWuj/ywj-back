/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-13 19:39 创建
 */
package com.zds.scf.biz.pbac;

import com.zds.scf.biz.pbac.realm.CPRealm;
import com.zds.scf.biz.pbac.realm.RetryLimitHashedCredentialsMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class PBACConfigration {
    /**
     * 下面两个参数不能修改
     */
    public static final String ALGORITHM_NAME = "SHA-256";
    public static final int HASH_ITERATIONS = 2;
    public static final String PASWD_RETRY_CACHE = "passwordRetryCache";


    @Bean
    public CPRealm shiroRealm(RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher) {
        CPRealm cpRealm = new CPRealm();
        cpRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);
        return cpRealm;
    }

    @Bean
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(ALGORITHM_NAME);
        hashedCredentialsMatcher.setHashIterations(HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }
}
