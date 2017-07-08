
package com.zds.scf.biz.common.config;

import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.udc.UDC;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 */
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        BaseEntity.inited();
        UDC.initUdcService();
    }
}
