package com.zds.scf.web;

import com.zds.scf.web.common.acl.CPACLFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 */
@Configuration
public class BackEndWebConfigration {

    @Bean(name = "backend_loginFilter_reg")
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new CPACLFilter.Builder().loginUrl("needNo")
                .notNeedLoginUrls("/backend/**")
                .urlPatterns("/backend/*").supportUserType("manager").build();
        bean.setName("out_loginFilter");
        return bean;
    }
}
