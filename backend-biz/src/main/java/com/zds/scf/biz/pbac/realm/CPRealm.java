/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-13 14:42 创建
 */
package com.zds.scf.biz.pbac.realm;

import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.entity.Role;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.biz.pbac.ex.CPAuthenticationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 *   登陆验证方法调用顺序: doGetAuthenticationInfo-->assertCredentialsMatch-->RetryLimitHashedCredentialsMatcher.doCredentialsMatch
 */
public class CPRealm extends AuthorizingRealm {

    @Autowired
    private UserDomainService userService;

    @Autowired
    private UserDomainService homeUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userCode = (String) principals.getPrimaryPrincipal();
        CPContext.UserInfo userInfo = (CPContext.UserInfo) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        Assert.notNull(userInfo);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = userService.loadByCode(userCode).getRoles();
        for(Role role : roles){
            authorizationInfo.addRole(String.valueOf(role.getId()));
            for(Resource resource : role.getResources()){
                authorizationInfo.addStringPermission(resource.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws CPAuthenticationException {
        /*CPAuthenticationToken authenticationToken = (CPAuthenticationToken) token;
        String username = (String) authenticationToken.getPrincipal();
        Long merchantId = authenticationToken.getMerchantId();
        SeUser user = userService.findByUserNameAndMerchantId(username, merchantId);
        if (user == null) {
            throw new CPAuthenticationException("用户名或密码错误");
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new CPAuthenticationException("用户被锁定，请联系管理员解锁");
        }
        authenticationToken.setUsername(user.getUsername()); //用户名是否存在验证完毕,将用户输入的用户名 重设为数据库中的用户名 ，因为用户可能会输入用户名或者手机号登陆 modify by 2017.2.8
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;*/

        CPAuthenticationToken authenticationToken = (CPAuthenticationToken) token;
        String username = (String) authenticationToken.getPrincipal();
        User user = homeUserService.loadByCode(username);
        if (user == null) {
            throw new CPAuthenticationException("用户名或密码错误");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getCode(), //用户名
                user.getPassWord(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CPAuthenticationToken authenticationToken = (CPAuthenticationToken) token;
        if (!authenticationToken.isNoPasswordLogin()) {
            CredentialsMatcher cm = getCredentialsMatcher();
            if (!cm.doCredentialsMatch(token, info)) {
                throw new CPAuthenticationException("用户名或密码错误");
            }
        }
    }

    @Override
    public boolean isCachingEnabled() {
        return false;
    }
}
