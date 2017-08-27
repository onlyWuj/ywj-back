/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-26 16:35 创建
 */
package com.zds.scf.web.common.acl;

import com.google.common.collect.Lists;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.web.common.component.LoginComponent;
import org.apache.shiro.SecurityUtils;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 */
public class CPACLFilter extends OncePerRequestFilter {
    protected String loginUrl;
    protected String[] notNeedLoginUrls = null;
    protected AntPathMatcher antPathMatcher = new AntPathMatcher();
    protected String[] supportUserType;
    protected String homeUrl;

    protected CPACLFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException,
            IOException {
        String uri = getRequestURI(request);

        if (notNeedLogin(uri)) {
            filterChain.doFilter(request, response);
        } else {
            //超时判断
            if (checkSessionTimeout(request, response)) return;
            //类型判断
            if (checkUserType(request, response)) return;
            //认证判断
            if (!SecurityUtils.getSubject().isAuthenticated()) {
//                response.sendRedirect(response.encodeRedirectURL(loginUrl));
                response.setStatus(707);
                response.setCharacterEncoding("utf-8");
                response.getWriter().print("not logged");
                return;
            } else {
                filterChain.doFilter(request, response);
            }
//             filterChain.doFilter(request, response);
        }
    }

    protected boolean checkSessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (LoginComponent.isSessionTimeout(request)) {
            if(isWechatAccess(request)){
               response.sendRedirect(response.encodeRedirectURL(loginUrl));
                return true ;
            }
            //如果session超时，并且是ajax请求
            if(request.getRequestURI().endsWith(".json")){
                response.setStatus(707);
                response.setCharacterEncoding("utf-8");
                response.getWriter().print("session timeout");
            }
            return true;
        }
        return false;
    }

    protected boolean checkUserType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession(false);
        if (null != httpSession) {
            CPContext.UserInfo userInfo = (CPContext.UserInfo) httpSession.getAttribute("userInfo");
            if (userInfo != null) {
                boolean support = false;
                String[] supportTypes = getSupportUserType();
                //for(String type : supportTypes){
                 //   if(type.equalsIgnoreCase(userInfo.getType())){
                        support = true;
                //        break;
                //    }
                //}
                if (!support) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print("非法的访问，用户类型不匹配");
                    return true;
                }
            }
        }
        return false;
    }
    protected boolean isWechatAccess(HttpServletRequest request) {
        return request.getHeader("User-Agent").indexOf("MicroMessenger") != -1;
    }
    public void init() {
        Assert.hasText(loginUrl);
        if (notNeedLoginUrls == null) {
            notNeedLoginUrls = new String[1];
            notNeedLoginUrls[0] = loginUrl;
        } else {
            String[] tmp = new String[notNeedLoginUrls.length + 1];
            System.arraycopy(notNeedLoginUrls, 0, tmp, 0, notNeedLoginUrls.length);
            tmp[tmp.length - 1] = loginUrl;
            notNeedLoginUrls = tmp;
        }
    }

    protected boolean notNeedLogin(String uri) {
        for (int i = 0; i < notNeedLoginUrls.length; i++) {
            if (antPathMatcher.match(notNeedLoginUrls[i], uri)) {
                return true;
            }
        }
        return false;
    }

    protected String getRequestURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int idx = uri.indexOf(';');
        if (idx > -1) {
            uri = uri.substring(0, idx);
        }
        return uri;
    }
    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String[] getNotNeedLoginUrls() {
        return notNeedLoginUrls;
    }

    public String[] getSupportUserType() {
        return supportUserType;
    }

    public void setSupportUserType(String... supportUserType) {
        this.supportUserType = supportUserType;
    }

    public void setNotNeedLoginUrls(String... notNeedLoginUrls) {
        this.notNeedLoginUrls = notNeedLoginUrls;
    }

    public static class Builder {
        private String loginUrl;
        private String homeUrl;
        private String[] notNeedLoginUrls = null;
        private String[] urlPatterns = null;
        protected String[] supportUserType;

        private CPACLFilter filter = null;

         public Builder() {
             this.filter = new CPACLFilter();
        }
         public Builder (CPACLFilter filter) {
             this.filter = filter;
        }

        /**
         * 设置登录页面url
         */
        public Builder loginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
            return this;
        }
          /**
         * 设置登录页面url
         */
        public Builder homeUrl(String homeUrl) {
            this.homeUrl = homeUrl;
            return this;
        }
        /**
         * 设置不需要登录的url，支持ant表达式
         */
        public Builder notNeedLoginUrls(String... notNeedLoginUrls) {
            this.notNeedLoginUrls = notNeedLoginUrls;
            return this;
        }
        /**
         * 允许何种类型的用户访问
         */
        public Builder supportUserType(String... supportUserType) {
            this.supportUserType = supportUserType;
            return this;
        }

        /**
         * 设置此filter生效的路径
         */
        public Builder urlPatterns(String... urlPattern) {
            this.urlPatterns = urlPattern;
            return this;
        }

        public FilterRegistrationBean build() {
            Assert.notEmpty(urlPatterns);
            Assert.notNull(supportUserType);

            CPACLFilter filter = this.filter;

            filter.setLoginUrl(loginUrl);
            filter.setHomeUrl(homeUrl);
            filter.setNotNeedLoginUrls(notNeedLoginUrls);
            filter.setSupportUserType(supportUserType);
            filter.init();
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(filter);
            filterRegistrationBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
            filterRegistrationBean.setUrlPatterns(Lists.newArrayList(urlPatterns));
            return filterRegistrationBean;
        }
    }
}
