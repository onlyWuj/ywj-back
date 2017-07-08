
package com.zds.scf.biz.common.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.zds.scf.biz.common.convert.DateConverter;
import com.zds.scf.biz.common.event.EventBus;
import com.zds.scf.biz.common.event.EventHandler;
import com.zds.scf.biz.common.udc.StringToUDCConverter;
import com.zds.scf.biz.common.udc.UDC;
import com.zds.scf.biz.common.udc.UDCDeserializer;
import com.cp.boot.core.Apps;
import com.google.common.base.Splitter;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;

/**
 *
 */
@Configuration
public class CommonConfigration {
    @Bean
    public static BeanDefinitionRegistryPostProcessor cpValidatorBeanDefinitionRegistryPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
                scanner.addIncludeFilter(new AssignableTypeFilter(EventHandler.class));
                scanner.scan(Splitter.on(",").splitToList(Apps.getBasePackage()).toArray(new String[0]));
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            }
        };
    }

    @Bean
    public static BeanPostProcessor cpAppServiceBeanCheckerPostProcessor() {
        return new CPAppServiceBeanCheckerPostProcessor();
    }

    @Bean
    public ApplicationReadyListener applicationReadyListener() {
        return new ApplicationReadyListener();
    }

   /* @Bean
    public EventBus messageBus(ContextWrappedExecutorService asyncEventExecutorService) {
        Feature.AsynchronousHandlerInvocation asynchronousHandlerInvocation = new Feature.AsynchronousHandlerInvocation();
        asynchronousHandlerInvocation.setExecutor(asyncEventExecutorService);
        EventBus bus = new EventBus(new BusConfiguration()
                .addFeature(Feature.SyncPubSub.Default())
                .addFeature(asynchronousHandlerInvocation)
                .addFeature(Feature.AsynchronousMessageDispatch.Default())
                .addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger())
                .setProperty(IBusConfiguration.Properties.BusId, "global bus"));
        return bus;
    }

    @Bean
    public ContextWrappedExecutorService asyncEventExecutorService() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setThreadNamePrefix("async-event-");
        threadPoolTaskExecutor.initialize();
        ContextWrappedExecutorService contextWrappedExecutorService = new ContextWrappedExecutorService(threadPoolTaskExecutor.getThreadPoolExecutor());
        return contextWrappedExecutorService;
    }*/

    @Bean
    public StringToUDCConverter stringToUDCConverter() {
        return new StringToUDCConverter();
    }
    @Bean
    public DateConverter dateConverter() {
        return new DateConverter();
    }

    @PostConstruct
    public void init() {
        ParserConfig.getGlobalInstance().getDerializers().put(UDC.class, new UDCDeserializer());
    }
}
