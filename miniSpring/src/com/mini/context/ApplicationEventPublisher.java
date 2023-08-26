package com.mini.context;

/**
 * @author iyb-houjinyi
 */
public interface ApplicationEventPublisher {
    /**
     * fetch data by event
     *
     * @param event rule id
     */
    void publishEvent(ApplicationEvent event);

    void addApplicationLister(ApplicationListener applicationListener);
}
