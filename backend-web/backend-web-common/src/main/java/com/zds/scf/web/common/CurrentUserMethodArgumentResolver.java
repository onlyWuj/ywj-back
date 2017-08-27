/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-14 17:09 创建
 */
package com.zds.scf.web.common;

import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserDomainService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return userService.loadByCode(subject.getPrincipal().toString());
        } else {
            return null;
        }
    }
}
