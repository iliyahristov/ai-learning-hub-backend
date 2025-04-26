package com.ih.ailh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AiLearningHubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiLearningHubBackendApplication.class, args);
    }

}