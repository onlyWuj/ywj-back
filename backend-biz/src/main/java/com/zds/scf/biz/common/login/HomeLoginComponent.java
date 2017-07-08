/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-11-12 17:25 创建
 */
package com.zds.scf.biz.common.login;
import com.cp.boot.core.Apps;
import com.zds.common.lang.beans.Copier;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.LoginDto;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.biz.pbac.realm.CPAuthenticationToken;
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

/**
 *
 */
@Service
public class HomeLoginComponent {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDomainService userService;

    public User login(HttpServletRequest httpServletRequest, LoginDto logindto) {
        User user = loginVerify(httpServletRequest, logindto);
        AuthenticationToken token = new CPAuthenticationToken(logindto.getUserName(), logindto.getPassword());
        login(httpServletRequest, user, token);
        return  user;
    }

    private User loginVerify(HttpServletRequest httpServletRequest, LoginDto loginDto) {
        if (loginDto.getVerifyCode() == null || ("".trim()).equals(loginDto.getVerifyCode())) {
            CPBusinessException.throwIt("图形验证码不能为空");
        } else {
            VerifyCodeUtil.verify(httpServletRequest, loginDto.getVerifyCode());
        }

        User user = userService.loadByCode(loginDto.getUserName());
        if (user == null) {
            CPBusinessException.throwIt("用户不存在");
        }

        return user;
    }


    private void login(HttpServletRequest httpServletRequest, User user, AuthenticationToken token) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (AuthenticationException e) {
            CPBusinessException.throwIt(e.getMessage());
        }
        CPContext.UserInfo userInfo = Copier.copy(user, CPContext.UserInfo.class);
        httpServletRequest.getSession().setAttribute("userInfo", userInfo);
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
