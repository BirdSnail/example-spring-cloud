package com.birdsnail.nacos.service;

import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class NacosEventListener implements ApplicationListener<RefreshEvent> {

    @Override
    public void onApplicationEvent(RefreshEvent event) {
        Object event1 = event.getEvent();
    }
}
