package com.bridsnail.spel.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {

    public void print(String s) {
        System.out.println("SimpleService.print: " + s);
    }

    @Override
    public String toString() {
        return "simpleService-yhd";
    }
}
