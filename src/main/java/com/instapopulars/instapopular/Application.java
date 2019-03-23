package com.instapopulars.instapopular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application {

    /**
     * http://localhost:8080/
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.getProperties().list(System.out);

        System.out.println(System.getProperty("os.name"));
    }
}