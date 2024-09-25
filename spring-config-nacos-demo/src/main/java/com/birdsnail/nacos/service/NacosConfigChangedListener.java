package com.birdsnail.nacos.service;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static com.alibaba.nacos.api.common.Constants.DEFAULT_GROUP;

/**
 * Nacos配置变更监听
 */
@Configuration
public class NacosConfigChangedListener {


    @Autowired
    private NacosConfigManager configManager;

    @PostConstruct
    public void init() throws NacosException {
        Listener listener = new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("Nacos配置变更：value=" + configInfo);
            }
        };

        // for NacosConfigListenerRegisteredEvent(true)
        configManager.getConfigService().addListener("demo.yaml", DEFAULT_GROUP, listener);

    }


}
