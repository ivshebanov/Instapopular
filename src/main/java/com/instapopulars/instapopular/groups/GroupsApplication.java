package com.instapopulars.instapopular.groups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.instapopulars.instapopular")
public class GroupsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroupsApplication.class, args);
    }
}
