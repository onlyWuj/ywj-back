
package com.zds.scf.biz.common.event;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 *
 */
public class EventBus<T> extends MBassador<T> {

    public EventBus() {
    }

    public EventBus(IPublicationErrorHandler errorHandler) {
        super(errorHandler);
    }

    public EventBus(IBusConfiguration configuration) {
        super(configuration);
    }

    /**
     * 仅当当前事务提交成功后才发布消息
     */
    public void publishAfterTransactionCommitted(T message) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_COMMITTED) {
                    EventBus.this.publish(message);
                }
            }
        });
    }
}