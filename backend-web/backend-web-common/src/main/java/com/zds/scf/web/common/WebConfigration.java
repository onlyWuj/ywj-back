package com.zds.scf.web.common;

import com.zds.scf.web.common.search.SpecificationHandlerMethodArgumentResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


/**
 *
 */
@Configuration
@EnableConfigurationProperties({ CommonProperties.class,WeChatProperties.class })
public class WebConfigration extends WebMvcConfigurerAdapter {

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
        argumentResolvers.add(new SpecificationHandlerMethodArgumentResolver());
    }
}
