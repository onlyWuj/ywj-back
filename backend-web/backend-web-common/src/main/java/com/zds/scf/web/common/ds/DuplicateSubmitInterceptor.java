/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-12-07 11:26 创建
 */
package com.zds.scf.web.common.ds;

import com.zds.scf.web.common.AvoidResubmit;
import com.zds.scf.web.common.CPConstants;
import com.cp.boot.web.SpringHandlerInterceptor;
import com.zds.common.log.LoggerFactory;
import com.zds.common.web.DuplicateSubmitException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 *
 */
@SpringHandlerInterceptor(excludePatterns = {"/css/**", "/js/**", "/images/**", "/services/**", "/resources/**",
        "/mgt/**"})
public class DuplicateSubmitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(DuplicateSubmitInterceptor.class);
    @Autowired
    private RedisTemplate<String, String> dsTokenSet;

    public static String TOKEN_KEY = "dSToken";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AvoidResubmit avoidResubmit = method.getAnnotation(AvoidResubmit.class);
            if (avoidResubmit != null) {
                String tokenIn = request.getParameter(TOKEN_KEY);
                logger.info("重复提交token:{}", tokenIn);
                String sessionId = request.getSession().getId();
                String key = CPConstants.DUPLICATE_SUBMIT_TOKEN_SET + sessionId;
                BoundSetOperations<String, String> setOperations = dsTokenSet.boundSetOps(key);
                if (setOperations.isMember(tokenIn)) {
                    setOperations.remove(tokenIn);
                    return true;
                } else {
                    DuplicateSubmitException dse = new DuplicateSubmitException("重复提交请求");
                    dse.setReferer(request.getHeader("Referer"));
                    dse.setRequestUri(request.getRequestURI());
                    throw dse;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
