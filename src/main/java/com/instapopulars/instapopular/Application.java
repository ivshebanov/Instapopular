package com.instapopulars.instapopular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class Application {

    /**
     * http://localhost:8080/
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}