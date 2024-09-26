package com.birdsnail.nacos.service;

import com.purgeteam.dynamic.config.starter.event.ActionConfigEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NacosEventListener implements ApplicationListener<ActionConfigEvent> {



    @Override
    public void onApplicationEvent(ActionConfigEvent event) {
        Map<String, HashMap> map = event.getPropertyMap();
        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }
}
