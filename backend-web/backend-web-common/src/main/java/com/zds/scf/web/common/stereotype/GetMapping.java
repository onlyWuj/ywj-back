/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-09-29 10:31 创建
 */
package com.zds.scf.web.common.stereotype;

/**
 *
 */

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Target(METHOD)
@Retention(RUNTIME)
@Documented
@RequestMapping(method = GET)
public @interface GetMapping {
    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] value() default {};
}
