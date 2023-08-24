package com.birdsnail.json.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.time.LocalDateTime;

public class JsonDemo {

    public static void main(String[] args) throws JsonProcessingException {
        Model model = new Model();
        model.setName("滑滑蛋");
        model.setDateTime(LocalDateTime.now());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println(objectMapper.writeValueAsString(model));

    }

    @Data
    static class Model{

        private String name;

//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime dateTime;

    }

}
