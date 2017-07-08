/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-12-13 18:54 创建
 */
package com.zds.scf.web.common.search;

import com.zds.scf.biz.common.search.SpecificationsBuilder;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 */
public class SpecificationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType() == Specification.class;
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
									NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SpecificationsBuilder builder = new SpecificationsBuilder();
        builder.parse(webRequest.getParameter(parameter.getParameterName()));
        Specifications exp = builder.build();
		return exp;
	}
}
