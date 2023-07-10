package com.sekomproject.sekom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SekomApplication {

    public static void main(String[] args) {
        SpringApplication.run(SekomApplication.class, args);
    }

}
