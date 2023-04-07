package com.birdsnail.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Value("${yhd.name}")
    private String name;

    public String getName() {
        return name;
    }

}
