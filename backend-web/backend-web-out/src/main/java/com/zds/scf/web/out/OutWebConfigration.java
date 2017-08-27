package com.zds.scf.web.out;

import com.zds.scf.web.common.acl.CPACLFilter;
import com.zds.scf.web.out.fliter.OutFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 */
@Configuration
public class OutWebConfigration {

    @Bean(name = "out_loginFilter_reg")
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new CPACLFilter.Builder(new OutFilter()).loginUrl("needNo")
                .notNeedLoginUrls("/out/**")
                .urlPatterns("/out/*").supportUserType("manager").build();
        bean.setName("out_loginFilter_reg");
        return bean;
    }
}
