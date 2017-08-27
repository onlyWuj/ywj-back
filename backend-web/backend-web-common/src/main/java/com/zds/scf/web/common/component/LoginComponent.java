/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-11-12 17:25 创建
 */
package com.zds.scf.web.common.component;

import com.cp.boot.core.Apps;
import com.zds.common.lang.beans.Copier;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.biz.pbac.realm.CPAuthenticationToken;
import com.zds.scf.web.common.model.LoginModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

/**
 *
 */
@Service
public class LoginComponent {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDomainService userService;

    public User loginWithPassword(HttpServletRequest request, LoginModel loginModel) {
        User user = loginVerify(request, loginModel, LoginModel.PasswordLogin.class);
        AuthenticationToken token = new CPAuthenticationToken(loginModel.getUserCode(), loginModel.getPassword());
        login(request, user, token);
        return  user;
    }

    private User loginVerify(HttpServletRequest request, LoginModel loginModel, Class group) {
        loginModel.checkWithGourp(Default.class, group) ;
        //暂时不校验图形
       /* if (LoginModel.PasswordLogin.class.isAssignableFrom(group)) {
            if (loginModel.getVerifyCode() == null || ("".trim()).equals(loginModel.getVerifyCode())) {
                CPBusinessException.throwIt("图形验证码不能为空");
            } else {
                VerifyCodeUtil.verify(request, loginModel.getVerifyCode());
            }
        }*/
        User user = userService.loadByCode(loginModel.getUserCode());
        if (user == null) {
            CPBusinessException.throwIt("用户不存在");
        }
        if (!user.getAvailable()) {
            CPBusinessException.throwIt("用户被停用，请联系管理员");
        }
        return user;
    }


    private void login(HttpServletRequest request, User user, AuthenticationToken token) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (AuthenticationException e) {
            CPBusinessException.throwIt(e.getMessage());
        }
        CPContext.UserInfo userInfo = Copier.copy(user, CPContext.UserInfo.class);
        request.getSession().setAttribute("userInfo", userInfo);
    }
    public static boolean isSessionTimeout(HttpServletRequest request) {
        String sessionId = getSessionIdFormCookie(request);
        HttpSession session = request.getSession();
        if (sessionId != null && !session.getId().equalsIgnoreCase(sessionId)) {
            return true;
        }
        return false;
    }

    private static String getSessionIdFormCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(Apps.getAppSessionCookieName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

}
