
package com.zds.scf.biz.common.config;

import com.zds.scf.biz.common.dto.CPRequest;
import com.zds.scf.biz.common.dto.CPResponse;
import com.cp.boot.appservice.AppService;
import com.cp.boot.core.Apps;
import com.zds.common.lang.exception.SystemException;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 */
public class CPAppServiceBeanCheckerPostProcessor implements BeanPostProcessor, PriorityOrdered {
    private static final String msg = "@AppService标注的类所有的公共非静态方法都会被增强(除非在方法上标注@AppService(enable = false))，方法需要满足如下条件：" +
            "1.只有一个入参，并且为CPRequest及其子类，" +
            "2.返回类型必须是CPResponse及其子类，此方法不符合规范：";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = getTargetClass(bean);
        if (clazz.getName().startsWith(Apps.getBasePackage())) {
            if (clazz.isAnnotationPresent(AppService.class)) {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    if (Modifier.isPublic(declaredMethod.getModifiers())
                            && !Modifier.isStatic(declaredMethod.getModifiers())) {
                        AppService appService = declaredMethod.getAnnotation(AppService.class);
                        if (appService != null && !appService.enable()) {
                            continue;
                        }
                        if (!CPResponse.class.isAssignableFrom(declaredMethod.getReturnType()) || declaredMethod.getParameterCount() != 1 || !CPRequest.class.isAssignableFrom(declaredMethod.getParameterTypes()[0])) {
                            throw new SystemException(msg + declaredMethod);
                        }
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private static Class<?> getTargetClass(Object bean) {
        Class targetClass;
        for (targetClass = AopUtils.getTargetClass(bean); isCglibProxyClass(targetClass); targetClass = targetClass
                .getSuperclass()) {
        }
        return targetClass;
    }

    private static boolean isCglibProxyClass(Class clazz) {
        return clazz != null && clazz.getName().contains("$$");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
