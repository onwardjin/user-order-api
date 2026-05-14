package com.example.userorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserOrderApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserOrderApiApplication.class, args);
    }
}