package com.example.cooperationproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.cooperationproject.mapper")
public class CooperationProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooperationProjectApplication.class, args);
    }
}
