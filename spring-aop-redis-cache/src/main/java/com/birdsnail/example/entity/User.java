package com.birdsnail.example.entity;

import lombok.Data;

@Data
public class User {

    public String name;
    public int age = 2;

    private Car car;
}