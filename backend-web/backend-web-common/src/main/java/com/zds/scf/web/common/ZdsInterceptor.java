/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-09-23 16:48 创建
 */
package com.zds.scf.web.common;

import com.cp.boot.core.configuration.LogAutoConfiguration;
import com.cp.boot.web.SpringHandlerInterceptor;
import com.zds.common.id.CodeGenerator;
import com.zds.scf.biz.common.CPContext;
import org.jboss.logging.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
@SpringHandlerInterceptor(excludePatterns = {"/css/**", "/js/**", "/images/**", "/services/**", "/resources/**",
        "/mgt/**"})
public class ZdsInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (null != request && null != handler) {
            String gid = CodeGenerator.newGID();
            CPContext context = CPContext.getContext();
            context.setGid(gid);
            MDC.put(LogAutoConfiguration.LogProperties.GID_KEY, gid);
            HttpSession httpSession = request.getSession(false);
            if (null != httpSession) {
                CPContext.UserInfo userInfo = (CPContext.UserInfo) httpSession.getAttribute("userInfo");
                if (userInfo != null) {
                    context.setUserInfo(userInfo);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.remove(LogAutoConfiguration.LogProperties.GID_KEY);
        CPContext.removeContext();
    }
}
