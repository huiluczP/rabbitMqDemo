package com.huiluczp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RunApplication {
    public static void main(String[] args){
        SpringApplication.run(RunApplication.class, args);
    }
}
