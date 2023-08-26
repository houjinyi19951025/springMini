package com.mini.context;

import java.util.ArrayList;
import java.util.List;

public class SimpleApplicationEventPublisher implements  ApplicationEventPublisher{

    List<ApplicationListener> listeners = new ArrayList<>();
    @Override
    public void publishEvent(ApplicationEvent event) {

        this.listeners.forEach(listener->listener.onApplicationEvent(event));
    }

    @Override
    public void addApplicationLister(ApplicationListener applicationListener) {
        this.listeners.add(applicationListener);
    }
}
