package com.birdsnail.springbootdemo.service;

import com.birdsnail.springbootdemo.annotation.YhdJob;
import org.springframework.stereotype.Service;

@Service
public class DemoService {


    @YhdJob
    public void job1() {

    }

    @YhdJob
    public void job2() {

    }

}
